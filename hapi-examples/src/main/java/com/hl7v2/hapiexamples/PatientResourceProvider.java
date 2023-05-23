package com.hl7v2.hapiexamples;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

public class PatientResourceProvider implements IResourceProvider {
  private Map<String, Patient> myPatients = new HashMap<String, Patient>();

  public PatientResourceProvider() {
    Patient pat1 = new Patient();
    pat1.setId("1");
    pat1.addIdentifier().setSystem("http://acme.com/MRNs").setValue("7000135");
    pat1.addName().setFamily("Simpson").addGiven("Homer").addGiven("J");
    myPatients.put("1", pat1);

    Patient pat2 = new Patient();
    pat2.setId("1");
    pat2.addIdentifier().setSystem("http://acme.com/MRNs").setValue("7111135");
    pat2.addName().setFamily("Limpson").addGiven("Tomer").addGiven("K");
    myPatients.put("2", pat2);

    Patient p = new Patient();
    p.addName().setFamily("Smith").addGiven("John").addGiven("Q");
    p.addIdentifier().setSystem("urn:foo:identifiers").setValue("7111025");
    if (SimpleValidation.validateResource(p)) {
      myPatients.put("3", pat2);
    }
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
    Patient retVal = myPatients.get(theId.getIdPart());
    if (retVal == null) {
      throw new ResourceNotFoundException(theId);
    }
    return retVal;
  }
}
