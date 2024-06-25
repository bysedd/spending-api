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

/**
 * The TitleService class implements the ICRUDService interface, providing CRUD operations for
 * working with titles.
 */
@Service
@AllArgsConstructor
public class TitleService implements ICRUDService<TitleRequestDto, TitleResponseDto> {

  private TitleRepository titleRepository;

  private ModelMapper mapper;

  /**
   * Retrieves the TitleResponseDto for the given TitleRequestDto and ID.
   *
   * @param dto The TitleRequestDto object.
   * @param id  The ID of the title.
   * @return The TitleResponseDto object.
   */
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

  /**
   * Creates a new title based on the provided TitleRequestDto.
   *
   * @param dto The TitleRequestDto object containing the information for the new title.
   * @return The TitleResponseDto object representing the created title.
   * @throws ResourceBadRequestException if the provided data is invalid.
   */
  @Override
  public TitleResponseDto create(TitleRequestDto dto) {
    validateTitle(dto);
    return getResponseDto(dto, null);
  }

  /**
   * Retrieves a list of all TitleResponseDto objects.
   *
   * @return A list of TitleResponseDto objects.
   */
  @Override
  public List<TitleResponseDto> getAll() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Title> titles = titleRepository.findByUser(user);

    return titles.stream().map(title -> mapper.map(title, TitleResponseDto.class)).toList();
  }

  /**
   * Retrieves the TitleResponseDto for the given ID.
   *
   * @param id The ID of the title.
   * @return The TitleResponseDto object.
   * @throws ResourceNotFoundException if the title with the given ID does not exist.
   */
  @Override
  public TitleResponseDto getById(Long id) {
    Optional<Title> optionalTitle = titleRepository.findById(id);
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (optionalTitle.isEmpty() || !optionalTitle.get().getUser().getId().equals(user.getId())) {
      throw new ResourceNotFoundException("Unable to find title with id: " + id);
    }

    return mapper.map(optionalTitle.get(), TitleResponseDto.class);
  }

  /**
   * Updates a title with the given ID based on the provided TitleRequestDto.
   *
   * @param id  The ID of the title.
   * @param dto The TitleRequestDto object containing the updated information.
   * @return The updated TitleResponseDto object.
   */
  @Override
  public TitleResponseDto update(Long id, TitleRequestDto dto) {
    getById(id);
    validateTitle(dto);
    return getResponseDto(dto, id);
  }

  /**
   * Deletes a title with the given ID.
   *
   * @param id The ID of the title to delete.
   */
  @Override
  public void delete(Long id) {
    getById(id);
    titleRepository.deleteById(id);
  }

  /**
   * Retrieves a list of TitleResponseDto objects based on the due date period.
   *
   * @param firstPeriod The start of the period in the format 'YYYY-MM-DD HH24:MI:SS'.
   * @param finalPeriod The end of the period in the format 'YYYY-MM-DD HH24:MI:SS'.
   * @return A list of TitleResponseDto objects.
   */
  public List<TitleResponseDto> getByDueDate(String firstPeriod, String finalPeriod) {
    List<Title> titles = titleRepository.getCashFlowByDueDate(firstPeriod, finalPeriod);

    return titles.stream()
        .map(title -> mapper.map(title, TitleResponseDto.class))
        .collect(Collectors.toList());
  }

  /**
   * Validates the properties of a TitleRequestDto object.
   *
   * @param dto The TitleRequestDto object to validate.
   * @throws ResourceBadRequestException If any of the properties in the TitleRequestDto object are
   *                                     invalid.
   */
  private void validateTitle(TitleRequestDto dto) {
    if (dto.getType() == null) {
      throw new ResourceBadRequestException("type is required");
    } else if (dto.getDescription() == null || dto.getDescription().isBlank()) {
      throw new ResourceBadRequestException("description is required");
    } else if (dto.getValue() <= 0) {
      throw new ResourceBadRequestException("value is required");
    } else if (dto.getDueDate() == null) {
      throw new ResourceBadRequestException("dueDate is required");
    }

    Date now = new Date();
    if (dto.getDueDate().before(now)) {
      throw new ResourceBadRequestException("dueDate must be greater than the current date");
    }
  }
}
