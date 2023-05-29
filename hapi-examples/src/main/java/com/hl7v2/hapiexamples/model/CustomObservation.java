package com.hl7v2.hapiexamples.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Type;

@Entity(name = "Observation")
@Table(name = "Observation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomObservation {

    @Id
    Long id;

    @Column(name = "code", nullable = false)
    private Coding code;

    @Column(name = "effectiveDateTime", nullable = false)
    private DateTimeType effectiveDateTime;

    @Column(name = "status", nullable = false)
    private ObservationStatus status;

    @Column(name = "subject", nullable = false)
    private Reference subject;

    @Column(name = "device", nullable = false)
    private Reference device;

    @Column(name = "valueQuantity", nullable = true)
    private String valueQuantity;

    @Column(name = "value", nullable = true)
    private Type value;
}
