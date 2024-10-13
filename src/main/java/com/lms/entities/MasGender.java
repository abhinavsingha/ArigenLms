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
@Table(name = "mas_gender")
public class MasGender {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "gender_code", nullable = false, length = 1)
    private String genderCode;

    @Column(name = "gender_name", nullable = false, length = Integer.MAX_VALUE)
    private String genderName;

    @Column(name = "last_chg_dt")
    private Instant lastChgDt;

    @Column(name = "status", nullable = false, length = 1)
    private String status;

}
