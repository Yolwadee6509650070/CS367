package dev.natthida.cdloan.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;

    @OneToMany(mappedBy = "storage")
    private List<CD> cds;

    public Storage() {}

    public Storage(String location) {
        this.location = location;
    }

    // Getters and Setters
    public Long getId() { 
        return id; 
    }

    public String getLocation() { 
        return location; 
    }

    public void setLocation(String location) { 
        this.location = location; 
    }

    public List<CD> getCds() { 
        return cds; 
    }

    public void setCds(List<CD> cds) { 
        this.cds = cds; 
    }
}
