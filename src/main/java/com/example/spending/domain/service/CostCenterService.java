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

/**
 * This class is responsible for providing CRUD operations for the Cost Center entity.
 */
@Service
@AllArgsConstructor
public class CostCenterService
    implements ICRUDService<CostCenterRequestDto, CostCenterResponseDto> {

  private CostCenterRepository costCenterRepository;

  private ModelMapper mapper;

  /**
   * Creates a CostCenterResponseDto based on the given CostCenterRequestDto.
   *
   * @param dto The CostCenterRequestDto used to create the CostCenterResponseDto.
   * @return The created CostCenterResponseDto.
   */
  @Override
  public CostCenterResponseDto create(CostCenterRequestDto dto) {
    return getResponseDto(dto, null);
  }

  /**
   * Creates a CostCenterResponseDto based on the given CostCenterRequestDto and ID.
   *
   * @param dto The CostCenterRequestDto used to create the CostCenterResponseDto.
   * @param id  The ID of the CostCenter.
   * @return The created CostCenterResponseDto.
   */
  private CostCenterResponseDto getResponseDto(CostCenterRequestDto dto, Long id) {
    CostCenter costCenter = mapper.map(dto, CostCenter.class);
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    costCenter.setUser(user);
    costCenter.setId(id);

    CostCenter savedCostCenter = costCenterRepository.save(costCenter);

    return mapper.map(savedCostCenter, CostCenterResponseDto.class);
  }

  /**
   * Retrieves all cost centers.
   *
   * @return The list of cost centers.
   */
  @Override
  public List<CostCenterResponseDto> getAll() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<CostCenter> costCenters = costCenterRepository.findByUser(user);

    return costCenters.stream()
        .map(costCenter -> mapper.map(costCenter, CostCenterResponseDto.class))
        .toList();
  }

  /**
   * Retrieves the CostCenterResponseDto based on the given ID.
   *
   * @param id The ID of the CostCenter.
   * @return The retrieved CostCenterResponseDto.
   * @throws ResourceNotFoundException if the CostCenter is not found or if the user associated with
   *                                   the CostCenter doesn't match the authenticated user.
   */
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

  /**
   * Updates a CostCenter with the given ID using the provided CostCenterRequestDto.
   *
   * @param id  The ID of the CostCenter to update.
   * @param dto The CostCenterRequestDto containing the updated information.
   * @return The updated CostCenterResponseDto.
   */
  @Override
  public CostCenterResponseDto update(Long id, CostCenterRequestDto dto) {
    getById(id);

    return getResponseDto(dto, id);
  }

  /**
   * Deletes a CostCenter record based on the given ID.
   *
   * @param id The ID of the CostCenter to delete.
   */
  @Override
  public void delete(Long id) {
    getById(id);

    costCenterRepository.deleteById(id);
  }
}
