package com.hl7v2.hapiexamples.provider;

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
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
// import org.springframework.stereotype.Component;

// @Component
public class ObservationResourceProvider implements IResourceProvider {

  public static final String[] displayValues = {
      "CPAP Pressure Level",   "CPAP Usage Duration",
      "Mask Pressure Leakage", "Respiratory Rate",
      "Oxygen Saturation",     "Apnea-Hypopnea Index (AHI)",
      "Mask Fit Assessment",   "Event Detection",
      "Compliance Data"};

  private static final Logger logger =
      LoggerFactory.getLogger(ObservationResourceProvider.class);
  private Map<Integer, Observation> observations =
      new HashMap<Integer, Observation>();

  public ObservationResourceProvider() {}

  @Override
  public Class<? extends IBaseResource> getResourceType() {
    return Observation.class;
  }

  @Create
  public MethodOutcome create(@ResourceParam final Observation observation) {

    Integer id = observations.size() + 1;

    final List<Coding> codingList = observation.getCode().getCoding();
    DateTimeType effectiveDateTime = observation.getEffectiveDateTimeType();
    String formattedDateTime = effectiveDateTime.getValueAsString();
    formattedDateTime = formattedDateTime.replace("T", " ").replace("Z", "");

    for (Coding coding : codingList) {
      final String display = coding.getDisplay();
      boolean found = false;
      if (display.equals(displayValues[0]) ||
          display.equals(displayValues[1]) ||
          display.equals(displayValues[2]) ||
          display.equals(displayValues[3]) ||
          display.equals(displayValues[4]) ||
          display.equals(displayValues[5])) {
        logger.info("{} at {} Value: {} {}", display, formattedDateTime,
                    observation.getValueQuantity().getValue(),
                    observation.getValueQuantity().getUnit());
        found = true;

      } else {
        logger.info("{} at {} Value: {}", display, formattedDateTime,
                    observation.getValue().toString());
        found = true;
      }
      if (found) {
        try (ClassPathXmlApplicationContext context =
                 new ClassPathXmlApplicationContext("spring.xml")) {
          ObservationDAO observationDAO = context.getBean(ObservationDAO.class);

          CustomObservation customObservation = new CustomObservation(
              id.longValue(), coding, effectiveDateTime,
              observation.getStatus(), observation.getSubject(),
              observation.getDevice(),
              observation.getValueQuantity().toString(),
              observation.getValue());

          observationDAO.save(customObservation);
          break;
        } catch (Exception e) {
          System.err.println(e.getMessage());
        };
      }
    }

    observations.put(id, observation);

    MethodOutcome retVal = new MethodOutcome();
    retVal.setId(new IdType("Observation", id.toString(), "1"));

    OperationOutcome outcome = new OperationOutcome();
    outcome.addIssue().setDiagnostics("One minor issue detected");
    retVal.setOperationOutcome(outcome);

    return retVal;
  }
}
