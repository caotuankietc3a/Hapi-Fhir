package com.hl7v2.hapiexamples.provider;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatientResourceProvider implements IResourceProvider {
  private static final Logger logger =
      LoggerFactory.getLogger(PatientResourceProvider.class);
  private Map<Integer, Patient> myPatients = new HashMap<Integer, Patient>();

  public PatientResourceProvider() {
    // Patient pat1 = new Patient();
    // pat1.setId("1");
    // pat1.addIdentifier().setSystem("http://acme.com/MRNs").setValue("7000135");
    // pat1.addName().setFamily("Simpson").addGiven("Homer").addGiven("J");
    // myPatients.put("1", pat1);

    // Patient pat2 = new Patient();
    // pat2.setId("2");
    // pat2.addIdentifier().setSystem("http://acme.com/MRNs").setValue("7111135");
    // pat2.addName().setFamily("Limpson").addGiven("Tomer").addGiven("K");
    // myPatients.put("2", pat2);

    // Patient p = new Patient();
    // p.addName().setFamily("Smith").addGiven("John").addGiven("Q");
    // p.addIdentifier().setSystem("urn:foo:identifiers").setValue("7111025");
    // if (SimpleValidation.validateResource(p)) {
    //   myPatients.put("3", pat2);
    // }

    // p.addTelecom()
    //     .setSystem(ContactPointUse.ContactPointSystem.PHONE)
    //     .setValue("416 123-4567");
  }

  @Override
  public Class<? extends IBaseResource> getResourceType() {
    return Patient.class;
  }

  @Read()
  public Patient read(@IdParam IdType theId) {
    Patient retVal = myPatients.get(Integer.parseInt(theId.getIdPart()));
    if (retVal == null) {
      throw new ResourceNotFoundException(theId);
    }
    return retVal;
  }

  @Create
  public MethodOutcome create(@ResourceParam Patient patient) {
    // if (patient.getIdentifierFirstRep().isEmpty()) {
    //   throw new UnprocessableEntityException(Msg.code(636) +
    //                                          "No identifier supplied");
    // }

    // CPAP Pressure Level at 2023-05-25 18:00:00
    // Value: 9 cmH2O
    // logger.info(CPAP Pressure Level at);

    Integer id = myPatients.size() + 1;
    System.out.println("myPatients size: " + id);

    myPatients.put(myPatients.size() + 1, patient);
    System.out.println("myPatients size: " + myPatients.size());

    MethodOutcome retVal = new MethodOutcome();
    retVal.setId(new IdType("Patient", id.toString(), "1"));

    OperationOutcome outcome = new OperationOutcome();
    outcome.addIssue().setDiagnostics("One minor issue detected");
    retVal.setOperationOutcome(outcome);

    return retVal;
  }
}
