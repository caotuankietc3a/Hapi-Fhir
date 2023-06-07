package com.hl7v2.hapiexamples.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "AlarmsSettings")
@Data
@NoArgsConstructor
@ToString
public class AlarmsSettings {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Alarm_settings_id")
  private Integer alarmSettingsId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
    @JoinColumn(name = "device_id"), @JoinColumn(name = "device_name")
  })
  private Device device;

  @Column(name = "Ppeak") private Double ppeak;

  @Column(name = "VTiMin") private Double vtiMin;

  @Column(name = "VTiMax") private Double vtiMax;

  @Column(name = "VTeMin") private Double vteMin;

  @Column(name = "VTeMax") private Double vteMax;

  @Column(name = "MViMin") private Double mviMin;

  @Column(name = "MViMax") private Double mviMax;

  @Column(name = "MVeMin") private Double mveMin;

  @Column(name = "MVeMax") private Double mveMax;

  @Column(name = "RR_min") private Double rrMin;

  @Column(name = "RR_max") private Double rrMax;

  @Column(name = "Fio2Min") private Double fio2Min;

  @Column(name = "Fio2Max") private Double fio2Max;

  @Column(name = "Etco2Min") private Double etco2Min;

  @Column(name = "Etco2Max") private Double etco2Max;

  @Column(name = "Fico2Max") private Double fico2Max;

  @Column(name = "Pmin") private Double pmin;

  @Column(name = "PplatMax") private Double pplatMax;

  @Column(name = "FreqCTMin") private Double freqCTMin;

  @Column(name = "FreqCTMax") private Double freqCTMax;

  @Column(name = "Created_at", nullable = false) private Timestamp createdAt;
}
