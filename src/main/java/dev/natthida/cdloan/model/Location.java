package dev.natthida.cdloan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String branch; // Represents the branch where CDs are stored

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("location") // ป้องกันปัญหาการวนซ้ำ
    private List<CD> cds;

    public Location() {}

    public Location(String branch) {
        this.branch = branch;
    }

    // Getters and Setters
    public Long getId() { 
        return id; 
    }

    public String getBranch() { 
        return branch; 
    }

    public void setBranch(String branch) { 
        this.branch = branch; 
    }

    public List<CD> getCds() { 
        return cds; 
    }

    public void setCds(List<CD> cds) { 
        this.cds = cds; 
    }

    @Override
    public String toString() {
        return "Location{id=" + id + ", branch='" + branch + "'}";
    }
}
