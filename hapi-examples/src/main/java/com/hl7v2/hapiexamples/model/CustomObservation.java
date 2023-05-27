package com.hl7v2.hapiexamples.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Type;

@Entity(name = "Observation")
@Table(name = "Observation")
public class CustomObservation {

  public CustomObservation(Long id, Coding code, DateTimeType effectiveDateTime,
                           ObservationStatus status, Reference subject,
                           Reference device, String valueQuantity, Type value) {
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

  @Column(name = "valueQuantity", nullable = true) private String valueQuantity;

  @Column(name = "value", nullable = true) private Type value;

  public Coding getCode() { return code; }

  public void setCode(Coding code) { this.code = code; }

  public DateTimeType getEffectiveDateTime() { return effectiveDateTime; }

  public void setEffectiveDateTime(DateTimeType effectiveDateTime) {
    this.effectiveDateTime = effectiveDateTime;
  }

  public ObservationStatus getStatus() { return status; }

  public void setStatus(ObservationStatus status) { this.status = status; }

  public Reference getSubject() { return subject; }

  public void setSubject(Reference subject) { this.subject = subject; }

  public Reference getDevice() { return device; }

  public void setDevice(Reference device) { this.device = device; }

  public String getValueQuantity() { return valueQuantity; }

  public void setValueQuantity(String valueQuantity) {
    this.valueQuantity = valueQuantity;
  }

  public Type getValue() { return value; }

  public void setValue(Type value) { this.value = value; }

  public Long getId() { return id; }

  public void setId(Long id) { this.id = id; }

  // @Override
  // public String getId() {
  //   return super.getId();
  // }

  // @Override
  // public Resource setId(String value) {
  //   return super.setId(value);
  // }
}
