package com.example.spending.domain.service;

import com.example.spending.domain.exception.ResourceNotFoundException;
import com.example.spending.domain.model.CostCenter;
import com.example.spending.domain.model.User;
import com.example.spending.domain.repository.CostCenterRepository;
import com.example.spending.dto.cost_center.CostCenterRequestDto;
import com.example.spending.dto.cost_center.CostCenterResponseDto;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CostCenterService
    implements ICRUDService<CostCenterRequestDto, CostCenterResponseDto> {

  private CostCenterRepository costCenterRepository;

  private ModelMapper mapper;

  @Override
  public CostCenterResponseDto create(CostCenterRequestDto dto) {
    return getResponseDto(dto, null);
  }

  private CostCenterResponseDto getResponseDto(CostCenterRequestDto dto, Long id) {
    CostCenter costCenter = mapper.map(dto, CostCenter.class);
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    costCenter.setUser(user);
    costCenter.setId(id);

    CostCenter savedCostCenter = costCenterRepository.save(costCenter);

    return mapper.map(savedCostCenter, CostCenterResponseDto.class);
  }

  @Override
  public List<CostCenterResponseDto> getAll() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<CostCenter> costCenters = costCenterRepository.findByUser(user);

    return costCenters.stream()
        .map(costCenter -> mapper.map(costCenter, CostCenterResponseDto.class))
        .toList();
  }

  @Override
  public CostCenterResponseDto getById(Long id) {
    Optional<CostCenter> optionalCostCenter = costCenterRepository.findById(id);
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (optionalCostCenter.isEmpty()
        || !Objects.equals(optionalCostCenter.get().getUser().getId(), user.getId())) {
      throw new ResourceNotFoundException("Unable to find cost center with id: " + id);
    }

    return mapper.map(optionalCostCenter.get(), CostCenterResponseDto.class);
  }

  @Override
  public CostCenterResponseDto update(Long id, CostCenterRequestDto dto) {
    getById(id);

    return getResponseDto(dto, id);
  }

  @Override
  public void delete(Long id) {
    getById(id);

    costCenterRepository.deleteById(id);
  }
}
