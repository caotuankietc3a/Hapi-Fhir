package com.hl7v2.hapiexamples.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Status")
@Data
@NoArgsConstructor
public class Status {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Status_id")
  private Integer statusId;

  @Column(name = "PatientType") private Integer patientType;

  @Column(name = "VentilatorMode") private Integer ventilatorMode;

  @Column(name = "O2_100") private Integer o2_100;

  @Column(name = "ExpiPause") private Integer expiPause;

  @Column(name = "ExpiFlowSensor") private Integer expiFlowSensor;

  @Column(name = "VentilatorState") private Integer ventilatorState;

  @Column(name = "InspiPause") private Integer inspiPause;

  @Column(name = "NIV") private Integer niv;

  @Column(name = "CO2Sensor") private Integer co2Sensor;

  @Column(name = "Created_at", nullable = false,
          columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp createdAt;
}
