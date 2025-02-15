package dev.natthida.cdloan.model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class CD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;  // เพิ่ม artist เพื่อเก็บข้อมูลของศิลปิน

    // สถานะการยืม
    private Boolean borrowed = false;
    // ชื่อผู้ยืม
    private String borrower;

    // สาขาที่เก็บ CD (ใช้เมื่อซื้อหรือเมื่อคืน)
    @ManyToOne
    @JsonIgnoreProperties("cds")
    private Location location;

    // สาขาที่ยืมไป (สำหรับบันทึกข้อมูลการยืม)
    @ManyToOne
    @JsonIgnoreProperties("cds")
    private Location borrowedLocation;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    public CD() {}

    // คอนสตรัคเตอร์เดิมที่รับ title และ location
    public CD(String title, Location location) {
        this.title = title;
        this.location = location;
        this.borrowed = false;
        this.borrower = null;
        this.borrowedLocation = null;
        this.borrowDate = null;
        this.returnDate = null;
    }

    // คอนสตรัคเตอร์ใหม่ที่รับ title, artist, location
    public CD(String title, String artist, Location location) {
        this.title = title;
        this.artist = artist; // กำหนด artist
        this.location = location;
        this.borrowed = false;
        this.borrower = null;
        this.borrowedLocation = null;
        this.borrowDate = null;
        this.returnDate = null;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getBorrowedLocation() {
        return borrowedLocation;
    }

    public void setBorrowedLocation(Location borrowedLocation) {
        this.borrowedLocation = borrowedLocation;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
