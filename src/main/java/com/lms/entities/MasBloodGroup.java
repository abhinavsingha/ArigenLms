package com.lms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "mas_blood_group")
public class MasBloodGroup {
    @Id
    @Column(name = "blood_group_id", nullable = false)
    private Long id;

    @Column(name = "blood_group_code", length = 8)
    private String bloodGroupCode;

    @Column(name = "blood_group_name", length = 30)
    private String bloodGroupName;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "last_chg_by", length = 12)
    private String lastChgBy;

    @Column(name = "last_chg_date")
    private Instant lastChgDate;

    @Column(name = "last_chg_time", length = 10)
    private String lastChgTime;

    @Column(name = "hic_code", length = 25)
    private String hicCode;

}
