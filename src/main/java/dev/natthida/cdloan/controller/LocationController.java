package dev.natthida.cdloan.controller;

import org.springframework.web.bind.annotation.*;
import dev.natthida.cdloan.model.Location;
import dev.natthida.cdloan.repository.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // ดึงข้อมูลสาขาทั้งหมด
    @GetMapping
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // เพิ่มข้อมูลสาขา
    @PostMapping
    public Location addLocation(@RequestBody Location location) {
        return locationRepository.save(location);
    }

    // ลบสาขา
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteLocation(@PathVariable Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "ลบสำเร็จ");
            return ResponseEntity.ok(response); // ส่ง JSON {"message": "ลบสำเร็จ"}
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "ไม่พบสาขาที่ต้องการลบ"));
        }
    }
}
