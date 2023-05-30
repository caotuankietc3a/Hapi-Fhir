package com.hl7v2.hapiexamples.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Type;

@Entity(name = "Observation")
@Table(name = "Observation")
@Data
@NoArgsConstructor
public class CustomObservation {

  public CustomObservation(Long id, Coding code, DateTimeType effectiveDateTime,
                           ObservationStatus status, Reference subject,
                           Reference device, Quantity valueQuantity,
                           Type value) {
    this.id = id;
    this.code = code;
    this.effectiveDateTime = effectiveDateTime;
    this.status = status;
    this.subject = subject;
    this.device = device;
    this.valueQuantity = valueQuantity;
    this.value = value;
  }

  @Id Long id;

  @Column(name = "code", nullable = false) private Coding code;

  @Column(name = "effectiveDateTime", nullable = false)
  private DateTimeType effectiveDateTime;

  @Column(name = "status", nullable = false) private ObservationStatus status;

  @Column(name = "subject", nullable = false) private Reference subject;

  @Column(name = "device", nullable = false) private Reference device;

  @Column(name = "valueQuantity", nullable = true)
  private Quantity valueQuantity;

  @Column(name = "value", nullable = true) private Type value;

  @Override
  public String toString() {
    String formattedDateTime = effectiveDateTime.getValueAsString();
    formattedDateTime = formattedDateTime.replace("T", " ").replace("Z", "");

    StringBuilder sb = new StringBuilder();
    sb.append("{");

    sb.append("\"resourceType\": \"Observation\",");

    sb.append("\"code\": ")
        .append("{")
        .append("\"system\": \"")
        .append(code.getSystem())
        .append("\",")
        .append("\"code\": \"")
        .append(code.getCode())
        .append("\",")
        .append("\"display\": \"")
        .append(code.getDisplay())
        .append("\"")
        .append("},");

    if (valueQuantity != null) {
      sb.append("\"valueQuantity\": ")
          .append("{")
          .append("\"value\": ")
          .append(valueQuantity.getValue().longValue())
          .append(",")
          .append("\"unit\": \"")
          .append(valueQuantity.getUnit())
          .append("\"},");
    }

    if (value != null) {
      sb.append("\"valueString\": \"").append(value.toString()).append("\",");
    }

    sb.append("\"effectiveDateTime\": \"")
        .append(formattedDateTime)
        .append("\",");

    sb.append("\"status\": \"").append(status.toCode()).append("\",");

    sb.append("\"subject\": ")
        .append("{")
        .append("\"reference\": \"")
        .append(subject.getReference().toString())
        .append("\"},");

    sb.append("\"device\": ")
        .append("{")
        .append("\"reference\": \"")
        .append(device.getReference().toString())
        .append("\"}");
    sb.append("}");

    return sb.toString();
  }
}
