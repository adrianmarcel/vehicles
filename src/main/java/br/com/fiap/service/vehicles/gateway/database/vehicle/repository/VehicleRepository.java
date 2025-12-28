package br.com.fiap.service.vehicles.gateway.database.vehicle.repository;

import br.com.fiap.service.vehicles.gateway.database.BaseRepository;
import br.com.fiap.service.vehicles.gateway.database.vehicle.model.VehicleEntity;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends BaseRepository<VehicleEntity, UUID> {}
