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
@Table(name = "EMP_ADDRESS_INFO")
public class EmpAddressInfo {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "EMP_CODE")
    private String empCode;

    @Column(name = "ADDRESS_TYPE")
    private String addressType;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "STATE")
    private String state;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "PIN_CODE")
    private Number pinCode;

    @Column(name = "ADDRESS_LINE1")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE2")
    private String addressLine2;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED_ON", nullable = false)
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON", nullable = false)
    private Timestamp updatedOn;

}
