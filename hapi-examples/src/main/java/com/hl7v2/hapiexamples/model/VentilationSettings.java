package com.hl7v2.hapiexamples.model;
import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "VentilationSettings")
@Data
@NoArgsConstructor
@ToString
public class VentilationSettings {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Ventilation_settings_id")
  private Integer ventilationSettingsId;

  @Column(name = "VT") private Double vt;

  @Column(name = "RR") private Double rr;

  @Column(name = "RR_mini") private Double rrMini;

  @Column(name = "RR_VSIMV") private Double rrVsimv;

  @Column(name = "PI") private Double pi;

  @Column(name = "PS") private Double ps;

  @Column(name = "PEEP") private Double peep;

  @Column(name = "I_E") private Double iE;

  @Column(name = "TI_Ttot") private Double tiTtot;

  @Column(name = "Timax") private Double timax;

  @Column(name = "TrigI") private Double trigI;

  @Column(name = "TrigE") private Double trigE;

  @Column(name = "Pressure_slope") private Double pressureSlope;

  @Column(name = "PI_sigh") private Double piSigh;

  @Column(name = "Sigh_period") private Double sighPeriod;

  @Column(name = "Vt_target") private Double vtTarget;

  @Column(name = "FiO2") private Double fiO2;

  @Column(name = "Flow_shape") private Double flowShape;

  @Column(name = "Tplat") private Double tplat;

  @Column(name = "Vtapnea") private Double vtapnea;

  @Column(name = "F_apnea") private Double fApnea;

  @Column(name = "T_apnea") private Double tApnea;

  @Column(name = "Tins") private Double tins;

  @Column(name = "Pimax") private Double pimax;

  @Column(name = "F_ent") private Double fEnt;

  @Column(name = "PS_PVACI") private Double psPvaci;

  @Column(name = "FiO2_CPV") private Double fiO2Cpv;

  @Column(name = "RR_CPV") private Double rrCpv;

  @Column(name = "PI_CPV") private Double piCpv;

  @Column(name = "Thigh_CPV") private Double thighCpv;

  @Column(name = "Heigh") private Double heigh;

  @Column(name = "Gender") private Double gender;

  @Column(name = "Coeff") private Double coeff;

  @Column(name = "O2_low_pressure") private Double o2LowPressure;

  @Column(name = "Peak_flow") private Double peakFlow;

  @Column(name = "Created_at", nullable = false,
          columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp createdAt;
}
