package dev.natthida.cdloan.controller;

import org.springframework.web.bind.annotation.*;

import dev.natthida.cdloan.model.Storage;
import dev.natthida.cdloan.repository.StorageRepository;

import java.util.List;

@RestController
@RequestMapping("/storages")
public class StorageController {
    private final StorageRepository storageRepository;

    public StorageController(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @GetMapping
    public List<Storage> getAllStorages() {
        return storageRepository.findAll();
    }

    @PostMapping
    public Storage addStorage(@RequestBody Storage storage) {
        return storageRepository.save(storage);
    }

    @DeleteMapping("/{id}")
    public void deleteStorage(@PathVariable Long id) {
        storageRepository.deleteById(id);
    }
}
