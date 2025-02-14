package dev.natthida.cdloan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CD {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Boolean borrowed = false;
    private String borrower;

    @ManyToOne  // ความสัมพันธ์กับ Storage (หลาย CD สามารถอยู่ใน Storage เดียวกัน)
    private Storage storage;

    public CD() {}

    // Constructor ที่รับ title และ Storage
    public CD(String title, Storage storage) {
        this.title = title;
        this.borrowed = false;
        this.borrower = null;
        this.storage = storage;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
