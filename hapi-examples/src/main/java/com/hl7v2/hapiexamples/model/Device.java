package com.hl7v2.hapiexamples.model;
import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Device")
@Data
@NoArgsConstructor
public class Device {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  @Column(name = "Device_id", length = 100,
          columnDefinition = "VARCHAR(100) DEFAULT 'abcd'")
  private String deviceId;

  @Column(name = "Device_name", length = 100,
          columnDefinition = "VARCHAR(100) DEFAULT 'Unknown Name'")
  private String deviceName;

  @Column(name = "Status_id") private Integer statusId;

  @Column(name = "Measure_id") private Integer measureId;

  @Column(name = "Ventilation_settings_id")
  private Integer ventilationSettingsId;

  @Column(name = "Alarm_settings_id") private Integer alarmSettingsId;

  @Column(name = "Created_at", nullable = false,
          columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp createdAt;
}
