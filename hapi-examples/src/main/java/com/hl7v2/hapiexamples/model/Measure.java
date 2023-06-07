package com.hl7v2.hapiexamples.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Measure")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Measure {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Measure_id")
  private Integer measureId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
    @JoinColumn(name = "device_id"), @JoinColumn(name = "device_name")
  })
  private Device device;

  @Column(name = "Ppeak") private Double ppeak;

  @Column(name = "VTe") private Double vte;

  @Column(name = "RR") private Double rr;

  @Column(name = "MVe") private Double mve;

  @Column(name = "FiO2") private Double fiO2;

  @Column(name = "Pmean") private Double pmean;

  @Column(name = "Pplat") private Double pplat;

  @Column(name = "PEEP") private Double peep;

  @Column(name = "VTi") private Double vti;

  @Column(name = "MVi") private Double mvi;

  @Column(name = "TI_Ttot") private Double tiTtot;

  @Column(name = "FinCO2") private Double finCO2;

  @Column(name = "etCO2") private Double etCO2;

  @Column(name = "Created_at", nullable = false) private Timestamp createdAt;
}
