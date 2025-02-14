package dev.natthida.cdloan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.natthida.cdloan.model.Storage;

public interface StorageRepository extends JpaRepository<Storage, Long> {
}

