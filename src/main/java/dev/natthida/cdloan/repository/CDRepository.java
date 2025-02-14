package dev.natthida.cdloan.repository;

import dev.natthida.cdloan.model.CD;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CDRepository extends JpaRepository<CD, Long> {
    // ✅ หาเฉพาะ CD ที่ยังไม่ได้ถูกยืม
    List<CD> findByBorrowedFalse();
}
