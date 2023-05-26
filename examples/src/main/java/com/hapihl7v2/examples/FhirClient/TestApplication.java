package com.hapihl7v2.examples.FhirClient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TestApplication {
  // @Autowired private static IGenericClient fhirClient;
  // @Autowired private static FhirContext ctx;
  static public final ScheduledExecutorService scheduler =
      Executors.newScheduledThreadPool(1);

  static public final FhirContext ctx = FhirClientConfiguration.fhirContext();
  static public final IGenericClient fhirClient =
      FhirClientConfiguration.fhirClient(ctx);

  @Scheduled(fixedRate = 1000)
  public static void run() throws Exception {

    // Runnable runnable = () -> testApp();
    // while (true) {
    // scheduler.schedule(runnable, 1, TimeUnit.SECONDS);
    testApp();

    // Thread.sleep(2500);
    // }
    // FhirContext ctx = FhirContext.forR4();
  }

  public static void testApp() {
    int random = (int)(Math.random() * FhirService.displayValues.length);
    Observation observation = null;
    switch (random) {
    case 0: {
      observation = FhirService.createCPAPPressureLevelObservation();
      break;
    }
    case 1: {
      observation = FhirService.createCPAPUsageDurationObservation();
      break;
    }
    case 2: {
      observation = FhirService.createMaskPressureLeakageObservation();
      break;
    }
    case 3: {
      observation = FhirService.createRespiratoryRateObservation();
      break;
    }
    case 4: {
      observation = FhirService.createOxygenSaturationObservation();
      break;
    }
    case 5: {
      observation = FhirService.createApneaHypopneaIndexObservation();
      break;
    }
    case 6: {
      observation = FhirService.createMaskFitAssessmentObservation();
      break;
    }
    case 7: {
      observation = FhirService.createEventDetectionObservation();
      break;
    }
    case 8: {
      observation = FhirService.createComplianceDataObservation();
      break;
    }

    default:
      System.err.println("Random was not correct!");
      break;
    }

    // Patient patient = FhirService.createPatient();
    // Device device = FhirService.createDevice();
    printFHIRResource(ctx, observation);

    MethodOutcome outcome = fhirClient.create()
                                .resource(observation)
                                .prettyPrint()
                                .encodedJson()
                                .execute();

    IIdType id = outcome.getId();
    System.out.println("Got ID: " + id.getValue());
    // printFHIRResource(ctx, patient);
    // printFHIRResource(ctx, device);
  }
  public static void printFHIRResource(FhirContext ctx,
                                       IBaseResource document) {
    System.out.println(
        "Document Resource: " +
        ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(
            document));
  }
}
