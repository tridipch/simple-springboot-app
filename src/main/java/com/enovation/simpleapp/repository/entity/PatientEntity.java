package com.enovation.simpleapp.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * Entity class for the table patient in the database.
 */
@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_id_seq")
    @SequenceGenerator(name = "patient_id_seq", sequenceName = "patient_id_seq", initialValue = 10000, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID externalReference;

    private String name;

    private String address;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    private LocalDateTime updated;

    @PreUpdate
    public void preUpdate() {
        updated = LocalDateTime.now(ZoneOffset.UTC);
    }

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now(ZoneOffset.UTC);
    }
}
