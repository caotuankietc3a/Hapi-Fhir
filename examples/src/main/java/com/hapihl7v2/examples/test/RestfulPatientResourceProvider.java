package com.hapihl7v2.examples.test;

import ca.uhn.fhir.i18n.Msg;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import java.util.concurrent.ConcurrentHashMap;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

public class RestfulPatientResourceProvider implements IResourceProvider {
  private static Long counter = 1L;

  private static final ConcurrentHashMap<String, Patient> patients =
      new ConcurrentHashMap<>();

  static {
    patients.put(String.valueOf(counter), createPatient("Van Houte"));
    patients.put(String.valueOf(counter), createPatient("Agnew"));
    for (int i = 0; i < 20; i++) {
      patients.put(String.valueOf(counter),
                   createPatient("Random Patient " + counter));
    }
  }

  public RestfulPatientResourceProvider() {}

  @Read
  public Patient find(@IdParam final IdType theId) {
    System.out.println("TEST" + theId.getId().toString());

    if (patients.containsKey(theId.getIdPart())) {
      return patients.get(theId.getIdPart());
    } else {
      throw new ResourceNotFoundException(Msg.code(2005) + theId);
    }
  }

  @Create
  public MethodOutcome createPatient(@ResourceParam Patient patient) {

    patient.setId(createId(counter, 1L));
    patients.put(String.valueOf(counter), patient);

    return new MethodOutcome(patient.getIdElement());
  }

  @Override
  public Class<Patient> getResourceType() {
    return Patient.class;
  }

  private static IdType createId(final Long id, final Long theVersionId) {
    return new IdType("Patient", "" + id, "" + theVersionId);
  }

  private static Patient createPatient(final String name) {
    final Patient patient = new Patient();
    patient.getName().add(new HumanName().setFamily(name));
    patient.setId(createId(counter, 1L));
    counter++;
    return patient;
  }
}
