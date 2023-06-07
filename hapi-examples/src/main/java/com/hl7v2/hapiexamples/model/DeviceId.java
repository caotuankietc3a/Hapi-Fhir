package com.hl7v2.hapiexamples.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceId implements Serializable {
  private Integer deviceId;
  private String deviceName;
}
