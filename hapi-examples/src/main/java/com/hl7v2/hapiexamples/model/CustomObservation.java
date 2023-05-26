package com.hl7v2.hapiexamples.model;

import ca.uhn.fhir.model.api.annotation.ResourceDef;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hl7.fhir.r4.model.Observation;

@ResourceDef(name = "Observation")
@Entity(name = "Observation")
@Table(name = "Observation")
public class CustomObservation extends Observation {
  @Id public String id;
}
