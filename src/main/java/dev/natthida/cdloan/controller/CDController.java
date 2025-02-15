package dev.natthida.cdloan.controller;

import dev.natthida.cdloan.model.CD;
import dev.natthida.cdloan.model.Location;
import dev.natthida.cdloan.repository.CDRepository;
import dev.natthida.cdloan.repository.LocationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cds")
public class CDController {
    private final CDRepository repository;
    private final LocationRepository locationRepository;

    public CDController(CDRepository repository, LocationRepository locationRepository) {
        this.repository = repository;
        this.locationRepository = locationRepository;
    }

    // ✅ ดู CD ทั้งหมด
    @GetMapping
    public List<CD> findAll() {
        return repository.findAll();
    }

    // ✅ ดู CD ตาม ID
    @GetMapping("/{id}")
    public ResponseEntity<CD> findOne(@PathVariable Long id) {
        Optional<CD> cd = repository.findById(id);
        return cd.map(ResponseEntity::ok)
                 .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ✅ เพิ่ม CD ใหม่ (ซื้อ CD มา) พร้อมระบุสาขาที่เก็บ
    @PostMapping
    public ResponseEntity<?> addCD(@RequestBody CD cd, @RequestParam Long locationId) {
        Optional<Location> locationOpt = locationRepository.findById(locationId);
        if (!locationOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("ไม่พบสาขาที่ระบุ");
        }
        cd.setLocation(locationOpt.get());
        cd.setBorrowed(false); // เริ่มต้นว่า CD ยังไม่ถูกยืม
        repository.save(cd);
        return ResponseEntity.status(HttpStatus.CREATED).body(cd);
    }

    // ✅ ยืม CD โดยใช้ JSON Body
    @PutMapping("/{id}/borrow")
    public ResponseEntity<?> borrowCD(@PathVariable Long id, @RequestBody BorrowRequest request) {
        Optional<CD> cdOpt = repository.findById(id);
        if (!cdOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบ CD");
        }
        CD cd = cdOpt.get();
        if (cd.getBorrowed()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CD นี้ถูกยืมอยู่แล้ว!");
        }

        Optional<Location> borrowLocationOpt = locationRepository.findById(request.getBorrowedLocationId());
        if (!borrowLocationOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ไม่พบสาขาที่ระบุสำหรับการยืม");
        }

        cd.setBorrowed(true);
        cd.setBorrower(request.getBorrower());
        cd.setBorrowedLocation(borrowLocationOpt.get());
        cd.setBorrowDate(LocalDate.now());
        repository.save(cd);
        return ResponseEntity.ok(cd);
    }

    // ✅ คืน CD โดยใช้ JSON Body
    @PutMapping("/{id}/return")
    public ResponseEntity<?> returnCD(@PathVariable Long id, @RequestBody ReturnRequest request) {
        Optional<CD> cdOpt = repository.findById(id);
        if (!cdOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบ CD");
        }
        CD cd = cdOpt.get();
        if (!cd.getBorrowed()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CD นี้ยังไม่ได้ถูกยืม!");
        }

        Optional<Location> returnLocationOpt = locationRepository.findById(request.getReturnLocationId());
        if (!returnLocationOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ไม่พบสาขาที่ระบุสำหรับการคืน");
        }

        cd.setBorrowed(false);
        cd.setBorrower(null);
        cd.setLocation(returnLocationOpt.get()); // อัพเดทสาขาที่เก็บใหม่เมื่อรับคืน
        cd.setBorrowedLocation(null); // เคลียร์ข้อมูลสาขาที่ยืมไป
        cd.setReturnDate(LocalDate.now());
        repository.save(cd);
        return ResponseEntity.ok(cd);
    }

    // ✅ ลบ CD (ป้องกันการลบ CD ที่ถูกยืมอยู่)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCD(@PathVariable Long id) {
        Optional<CD> cdOpt = repository.findById(id);
        if (!cdOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบ CD");
        }
        CD cd = cdOpt.get();
        if (cd.getBorrowed()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ไม่สามารถลบ CD ที่กำลังถูกยืมอยู่!");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ API ดูเฉพาะ CD ที่ยังไม่ถูกยืม
    @GetMapping("/available")
    public List<CD> findAvailableCDs() {
        return repository.findByBorrowedFalse();
    }

    // ✅ DTO สำหรับการยืม CD
    public static class BorrowRequest {
        private String borrower;
        private Long borrowedLocationId;

        public String getBorrower() {
            return borrower;
        }

        public void setBorrower(String borrower) {
            this.borrower = borrower;
        }

        public Long getBorrowedLocationId() {
            return borrowedLocationId;
        }

        public void setBorrowedLocationId(Long borrowedLocationId) {
            this.borrowedLocationId = borrowedLocationId;
        }
    }

    // ✅ DTO สำหรับการคืน CD
    public static class ReturnRequest {
        private Long returnLocationId;

        public Long getReturnLocationId() {
            return returnLocationId;
        }

        public void setReturnLocationId(Long returnLocationId) {
            this.returnLocationId = returnLocationId;
        }
    }
}
