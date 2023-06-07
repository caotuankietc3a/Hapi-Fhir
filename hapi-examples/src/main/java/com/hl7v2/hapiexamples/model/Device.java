package com.hl7v2.hapiexamples.model;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Device")
@Data
@NoArgsConstructor
@IdClass(DeviceId.class)
@ToString
public class Device {
  @Id @Column(name = "Device_id") private Integer deviceId;

  @Id @Column(name = "Device_name") private String deviceName;

  // @Column(name = "Status_id") private Integer statusId;

  // @Column(name = "Measure_id") private Integer measureId;

  // @Column(name = "Ventilation_settings_id")
  // private Integer ventilationSettingsId;

  // @Column(name = "Alarm_settings_id") private Integer alarmSettingsId;

  @Column(name = "Created_at", nullable = false) private Timestamp createdAt;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
  private List<Measure> measures = new ArrayList<Measure>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
  private List<Status> status = new ArrayList<Status>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
  private List<VentilationSettings> ventilationSettings =
      new ArrayList<VentilationSettings>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
  private List<AlarmsSettings> alarmsSettings = new ArrayList<AlarmsSettings>();
}
