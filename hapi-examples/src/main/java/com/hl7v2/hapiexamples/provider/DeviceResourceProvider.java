package com.hl7v2.hapiexamples.provider;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.hl7v2.hapiexamples.dao.ObservationDAO;
import com.hl7v2.hapiexamples.model.CustomObservation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
// import org.springframework.stereotype.Component;

// @Component
public class DeviceResourceProvider implements IResourceProvider {
  private static final FhirContext fhirContext = FhirContext.forR4();

  private static final Logger logger =
      LoggerFactory.getLogger(DeviceResourceProvider.class);
  private Map<Integer, Device> devices = new HashMap<Integer, Device>();

  public DeviceResourceProvider() {
    // TODO document why this constructor is empty
  }

  @Override
  public Class<? extends IBaseResource> getResourceType() {
    return Device.class;
  }

  @Create
  public MethodOutcome create(@ResourceParam final Device device) {

    Integer id = devices.size() + 1;

    String json =
        fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(
            device);
    logger.info(json);

    devices.put(id, device);

    MethodOutcome retVal = new MethodOutcome();
    retVal.setId(new IdType("Device", id.toString(), "1"));

    OperationOutcome outcome = new OperationOutcome();
    outcome.addIssue().setDiagnostics("One minor issue detected");
    retVal.setOperationOutcome(outcome);

    return retVal;
  }
}
