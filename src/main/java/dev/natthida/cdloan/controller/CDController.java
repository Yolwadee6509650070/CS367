package dev.natthida.cdloan.controller;
import dev.natthida.cdloan.model.CD;
import dev.natthida.cdloan.repository.CDRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cds")
public class CDController {
    private final CDRepository repository;

    public CDController(CDRepository repository) {
        this.repository = repository;
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

    // ✅ เพิ่ม CD ใหม่
    @PostMapping
    public ResponseEntity<CD> addCD(@RequestBody CD cd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(cd));
    }

    // ✅ ยืม CD (ป้องกันการยืมซ้ำ)
    @PutMapping("/{id}/borrow")
    public ResponseEntity<?> borrowCD(@PathVariable Long id, @RequestParam String borrower) {
        return repository.findById(id).map(cd -> {
            if (cd.getBorrowed()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("CD นี้ถูกยืมอยู่แล้ว!");
            }
            cd.setBorrowed(true);
            cd.setBorrower(borrower);
            repository.save(cd);
            return ResponseEntity.ok(cd);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("CD ไม่พบ"));
    }

    // ✅ คืน CD (ป้องกันการคืนซ้ำ)
    @PutMapping("/{id}/return")
    public ResponseEntity<?> returnCD(@PathVariable Long id) {
        return repository.findById(id).map(cd -> {
            if (!cd.getBorrowed()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("CD นี้ยังไม่ได้ถูกยืม!");
            }
            cd.setBorrowed(false);
            cd.setBorrower(null);
            repository.save(cd);
            return ResponseEntity.ok(cd);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("CD ไม่พบ"));
    }

    // ✅ ลบ CD (ป้องกันการลบ CD ที่ถูกยืมอยู่)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCD(@PathVariable Long id) {
        return repository.findById(id).map(cd -> {
            if (cd.getBorrowed()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("ไม่สามารถลบ CD ที่กำลังถูกยืมอยู่!");
            }
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("CD ไม่พบ"));
    }

    // ✅ API ใหม่: ดูเฉพาะ CD ที่ยังไม่ถูกยืม
    @GetMapping("/available")
    public List<CD> findAvailableCDs() {
        return repository.findByBorrowedFalse();
    }
}

