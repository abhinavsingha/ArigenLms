package com.payroll.erp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "MAS_ROLE")
public class MasRole {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ROLE_CODE")
    private String roleCode;

    @Column(name = "ROLE_DESC")
    private String roleDesc;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED_ON")
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON", nullable = false)
    private Timestamp updatedOn;

}
