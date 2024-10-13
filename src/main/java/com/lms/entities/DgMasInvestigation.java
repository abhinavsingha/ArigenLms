package com.lms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "dg_mas_investigation")
public class DgMasInvestigation {
    @Id
    @Column(name = "investigation_id", nullable = false)
    private Long id;

    @Size(max = 200)
    @Column(name = "investigation_name", length = 200)
    private String investigationName;

    @Size(max = 1)
    @Column(name = "status", length = 1)
    private String status;

    @Size(max = 1)
    @Column(name = "confidential", length = 1)
    private String confidential;

    @Size(max = 1)
    @Column(name = "appear_in_discharge_summary", length = 1)
    private String appearInDischargeSummary;

    @Size(max = 1)
    @Column(name = "investigation_type", length = 1)
    private String investigationType;

    @Size(max = 1)
    @Column(name = "multiple_results", length = 1)
    private String multipleResults;

    @Size(max = 10)
    @Column(name = "quantity", length = 10)
    private String quantity;

    @Size(max = 20)
    @Column(name = "normal_value", length = 20)
    private String normalValue;

    @Size(max = 12)
    @Column(name = "last_chg_by", length = 12)
    private String lastChgBy;

    @Column(name = "last_chg_date")
    private Instant lastChgDate;

    @Size(max = 10)
    @Column(name = "last_chg_time", length = 10)
    private String lastChgTime;

    @Size(max = 1)
    @Column(name = "appointment_required", length = 1)
    private String appointmentRequired;

    @Size(max = 10)
    @Column(name = "max_normal_value", length = 10)
    private String maxNormalValue;

    @Size(max = 10)
    @Column(name = "min_normal_value", length = 10)
    private String minNormalValue;

    @Column(name = "test_order_no")
    private Integer testOrderNo;

    @Size(max = 1)
    @Column(name = "numeric_or_string", length = 1)
    private String numericOrString;

    @Size(max = 25)
    @Column(name = "hic_code", length = 25)
    private String hicCode;

    @Column(name = "charge_code_id")
    private Integer chargeCodeId;

    @Column(name = "sample_id")
    private Integer sampleId;

    @Column(name = "equipment_id")
    private Integer equipmentId;

    @Size(max = 1)
    @Column(name = "blood_reaction_test", length = 1)
    private String bloodReactionTest;

    @Size(max = 1)
    @Column(name = "blood_bank_screen_test", length = 1)
    private String bloodBankScreenTest;

    @Size(max = 1500)
    @Column(name = "instructions", length = 1500)
    private String instructions;

}
