package br.com.fiap.service.vehicles.gateway.database.vehicle.specification;

import br.com.fiap.service.vehicles.core.usecase.model.VehicleFilterRequest;
import br.com.fiap.service.vehicles.gateway.database.vehicle.model.VehicleEntity;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public final class VehicleSpecification {

  private VehicleSpecification() {}

  public static Specification<VehicleEntity> build(VehicleFilterRequest filterRequest) {
    return (((root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (Objects.nonNull(filterRequest.getId())) {
        predicates.add(criteriaBuilder.equal(root.get("id"), filterRequest.getId()));
      }

      if (Objects.nonNull(filterRequest.getBrand())) {
        predicates.add(criteriaBuilder.equal(root.get("brand"), filterRequest.getBrand()));
      }

      if (Objects.nonNull(filterRequest.getModel())) {
        predicates.add(criteriaBuilder.equal(root.get("model"), filterRequest.getModel()));
      }

      if (Objects.nonNull(filterRequest.getColor())) {
        predicates.add(criteriaBuilder.equal(root.get("color"), filterRequest.getColor()));
      }

      if (Objects.nonNull(filterRequest.getStatus())) {
        predicates.add(criteriaBuilder.equal(root.get("status"), filterRequest.getStatus()));
      }

      if (Objects.nonNull(filterRequest.getMinYear())) {
        predicates.add(
            criteriaBuilder.greaterThanOrEqualTo(root.get("year"), filterRequest.getMinYear()));
      }

      if (Objects.nonNull(filterRequest.getMaxYear())) {
        predicates.add(
            criteriaBuilder.lessThanOrEqualTo(root.get("year"), filterRequest.getMaxYear()));
      }

      if (Objects.nonNull(filterRequest.getMinPrice())) {
        predicates.add(
            criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filterRequest.getMinPrice()));
      }

      if (Objects.nonNull(filterRequest.getMaxPrice())) {
        predicates.add(
            criteriaBuilder.lessThanOrEqualTo(root.get("price"), filterRequest.getMaxPrice()));
      }

      return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }));
  }
}
