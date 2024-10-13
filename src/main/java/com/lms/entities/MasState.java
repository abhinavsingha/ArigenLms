package com.lms.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "mas_state")
public class MasState {
    @Id
    @Column(name = "state_id", nullable = false)
    private Long id;

    @Column(name = "state_code", length = 8)
    private String stateCode;

    @Column(name = "state_name", length = 30)
    private String stateName;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private MasCountry country;

}
