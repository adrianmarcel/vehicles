package br.com.fiap.service.vehicles.core.config.bean;

import br.com.fiap.service.vehicles.core.domain.shared.exception.VehicleExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackages = "br.com.fiap",
    excludeFilters =
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            value = VehicleExceptionHandler.class))
public class ComponentScanConfiguration {}
