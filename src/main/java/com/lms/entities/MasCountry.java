package com.lms.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "mas_country")
public class MasCountry {
    @Id
    @Column(name = "country_id", nullable = false)
    private Long id;

    @Column(name = "country_code", length = 8)
    private String countryCode;

    @Column(name = "country_name", length = 30)
    private String countryName;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "last_chg_by", length = 12)
    private String lastChgBy;

    @Column(name = "last_chg_date")
    private Instant lastChgDate;

    @Column(name = "last_chg_time", length = 10)
    private String lastChgTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private MasCurrency currency;

}
