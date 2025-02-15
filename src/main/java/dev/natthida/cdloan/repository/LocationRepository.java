package dev.natthida.cdloan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.natthida.cdloan.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}

