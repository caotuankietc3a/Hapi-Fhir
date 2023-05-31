package com.hl7v2.hapiexamples.provider;

import static net.logstash.logback.argument.StructuredArguments.entries;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import java.util.HashMap;
import java.util.Map;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.Device.DevicePropertyComponent;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Property;
import org.hl7.fhir.r4.model.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(
        device);
    Map<String, Map<String, Map<String, Object>>> logKeyValue = new HashMap<>();
    Map<String, Object> logKeyValue2 = new HashMap<>();

    for (DevicePropertyComponent prop : device.getProperty()) {
      String type = prop.getType().getCoding().get(0).getCode();
      Map<String, Map<String, Object>> mapOut = new HashMap<>();
      for (Quantity var : prop.getValueQuantity()) {
        Map<String, Object> mapIn = new HashMap<>();
        long value = var.getValue().longValue();
        String code = var.getCode();
        String unit = var.getUnit();
        mapIn.put("value", value);
        // mapIn.put("name", code);
        mapIn.put("unit", unit);
        mapOut.put(code, mapIn);
      }
      logKeyValue.put(type, mapOut);
    }

    System.out.println(device.getId());
    System.out.println(device.getIdBase());
    System.out.println(device.getDistinctIdentifier());

    String[] identifier = device.getDistinctIdentifier().split("-");
    logKeyValue2.put("device_name", identifier[0]);
    logKeyValue2.put("device_id", identifier[1]);

    logger.info("Log for Device", entries(logKeyValue2), entries(logKeyValue));

    devices.put(id, device);

    MethodOutcome retVal = new MethodOutcome();
    retVal.setId(new IdType("Device", id.toString(), "1"));
    retVal.setId(new IdType("Device", id.toString(), "1"));

    OperationOutcome outcome = new OperationOutcome();
    outcome.addIssue().setDiagnostics("One minor issue detected");
    retVal.setOperationOutcome(outcome);

    return retVal;
  }
}
