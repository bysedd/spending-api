package com.example.spending.domain.service;

import com.example.spending.domain.exception.ResourceBadRequestException;
import com.example.spending.domain.exception.ResourceNotFoundException;
import com.example.spending.domain.model.Title;
import com.example.spending.domain.model.User;
import com.example.spending.domain.repository.TitleRepository;
import com.example.spending.dto.title.TitleRequestDto;
import com.example.spending.dto.title.TitleResponseDto;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TitleService implements ICRUDService<TitleRequestDto, TitleResponseDto> {

  private TitleRepository titleRepository;

  private ModelMapper mapper;

  private TitleResponseDto getResponseDto(TitleRequestDto dto, Long id) {
    Title title = mapper.map(dto, Title.class);
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    title.setUser(user);
    title.setId(id);
    if (id == null) {
      title.setRegisterDate(new Date());
    }
    Title savedTitle = titleRepository.save(title);

    return mapper.map(savedTitle, TitleResponseDto.class);
  }

  @Override
  public TitleResponseDto create(TitleRequestDto dto) {
    validateTitle(dto);
    return getResponseDto(dto, null);
  }

  @Override
  public List<TitleResponseDto> getAll() {
    List<Title> titles = titleRepository.findAll();

    return titles.stream()
        .map(title -> mapper.map(title, TitleResponseDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public TitleResponseDto getById(Long id) {
    Optional<Title> optionalTitle = titleRepository.findById(id);

    if (optionalTitle.isEmpty()) {
      throw new ResourceNotFoundException("Unable to find title with id: " + id);
    }

    return mapper.map(optionalTitle.get(), TitleResponseDto.class);
  }

  @Override
  public TitleResponseDto update(Long id, TitleRequestDto dto) {
    getById(id);
    validateTitle(dto);
    return getResponseDto(dto, id);
  }

  @Override
  public void delete(Long id) {
    getById(id);
    titleRepository.deleteById(id);
  }

  private void validateTitle(TitleRequestDto dto) {
    if (dto.getType() == null) {
      throw new ResourceBadRequestException("Type is required");
    } else if (dto.getDescription() == null || dto.getDescription().isBlank()) {
      throw new ResourceBadRequestException("Description is required");
    } else if (dto.getValue() <= 0) {
      throw new ResourceBadRequestException("Value is required");
    } else if (dto.getDueDate() == null) {
      throw new ResourceBadRequestException("Due date is required");
    }

    Date now = new Date();
    if (dto.getDueDate().before(now)) {
      throw new ResourceBadRequestException("Due date must be greater than the current date");
    }
  }
}
