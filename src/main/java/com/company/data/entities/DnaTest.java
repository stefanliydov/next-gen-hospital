package com.company.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dna_tests")

public class DnaTest extends BaseEntity {
    private String dna;

    private double result;

    @CreationTimestamp
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String symptoms;

}
