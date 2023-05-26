package com.hapihl7v2.examples.FhirClient;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hl7.fhir.r4.model.Address.AddressUse;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StringType;
import org.springframework.stereotype.Service;

// @Service
public class FhirService {
  private static final String[] CPAPPressureLevelUnits = {"cmH2O", "mmHg", "Pa",
                                                          "kPa"};
  private static final String[] CPAPUsageDurationUnits = {"seconds", "minutes"};

  private static final String[] MaskFitAssessmentValues = {"Good", "Fair",
                                                           "Poor"};

  private static final String[] ComplianceDataValues = {
      "Mask leaks", "Skin irritation", "Conjunctivitis", "Dry throat",
      "Claustrophobia"};

  private static final String[] EventDetectionValues = {
      "Snoring", "Mask Dislodgment", "Significant Leaks"};

  public static final String[] displayValues = {
      "CPAP Pressure Level",   "CPAP Usage Duration",
      "Mask Pressure Leakage", "Respiratory Rate",
      "Oxygen Saturation",     "Apnea-Hypopnea Index (AHI)",
      "Mask Fit Assessment",   "Event Detection",
      "Compliance Data"};

  // @Autowired private IGenericClient fhirClient;
  private static Observation createObservation(String code,
                                               String displayType) {

    Observation observation = new Observation();
    observation.getCode()
        .addCoding()
        .setSystem("http://loinc.org")
        .setCode(code)
        .setDisplay(displayType);

    observation.setEffective(new DateTimeType(new Date()));
    observation.setStatus(Observation.ObservationStatus.FINAL);
    observation.getSubject().setReference("Patient/example-patient");
    observation.getDevice().setReference("Device/example-device");

    // return fhirClient.create().resource(observation).execute().getId();
    return observation;
  }

  public static Observation createCPAPPressureLevelObservation() {
    double randomValue = Math.random() * 20;
    DecimalFormat decimalFormat = new DecimalFormat("##.##");
    String formattedValue = decimalFormat.format(randomValue);

    Observation observation = createObservation("15074-8", displayValues[0]);

    observation.getValueQuantity()
        .setValue(Double.parseDouble(formattedValue))
        .setUnit(CPAPPressureLevelUnits[(int)(Math.random() *
                                              CPAPPressureLevelUnits.length)]);
    return observation;
  }

  public static Observation createCPAPUsageDurationObservation() {

    int randomUnit = (int)(Math.random() * CPAPUsageDurationUnits.length);
    int randomValue = (int)(Math.random() * (randomUnit == 0 ? 100000 : 100));

    Observation observation = createObservation("XYZ1234", displayValues[1]);

    observation.getValueQuantity()
        .setValue(randomValue)
        .setUnit(CPAPUsageDurationUnits[randomUnit]);
    return observation;
  }

  public static Observation createMaskPressureLeakageObservation() {
    double randomValue = Math.random() * 100;
    DecimalFormat decimalFormat = new DecimalFormat("##.##");
    String formattedValue = decimalFormat.format(randomValue);

    Observation observation = createObservation("ABC5678", displayValues[2]);
    observation.getValueQuantity()
        .setValue(Double.parseDouble(formattedValue))
        .setUnit("percents");
    return observation;
  }

  public static Observation createRespiratoryRateObservation() {
    int randomValue = (int)(Math.random() * 150);

    Observation observation = createObservation("DEF9012", displayValues[3]);
    observation.getValueQuantity()
        .setValue(randomValue)
        .setUnit("breaths/minute");
    return observation;
  }

  public static Observation createOxygenSaturationObservation() {

    double randomValue = Math.random() * 100;
    DecimalFormat decimalFormat = new DecimalFormat("##.##");
    String formattedValue = decimalFormat.format(randomValue);

    Observation observation = createObservation("GHI3456", displayValues[4]);
    observation.getValueQuantity()
        .setValue(Double.parseDouble(formattedValue))
        .setUnit("percents");
    return observation;
  }

  public static Observation createApneaHypopneaIndexObservation() {
    int randomValue = (int)(Math.random() * 30);
    Observation observation = createObservation("JKL7890", displayValues[5]);
    observation.getValueQuantity().setValue(randomValue).setUnit("events/hour");
    return observation;
  }

  public static Observation createMaskFitAssessmentObservation() {
    int randomValue = (int)(Math.random() * MaskFitAssessmentValues.length);
    Observation observation = createObservation("MNO2345", displayValues[6]);
    observation.setValue(new StringType(MaskFitAssessmentValues[randomValue]));
    return observation;
  }

  public static Observation createEventDetectionObservation() {
    int randomValue = (int)(Math.random() * EventDetectionValues.length);
    Observation observation = createObservation("PQR6789", displayValues[7]);
    observation.setValue(new StringType(EventDetectionValues[randomValue]));
    return observation;
  }

  public static Observation createComplianceDataObservation() {
    int randomValue = (int)(Math.random() * ComplianceDataValues.length);
    Observation observation = createObservation("STU0123", displayValues[8]);
    observation.setValue(new StringType(ComplianceDataValues[randomValue]));
    return observation;
  }

  public static Device createDevice() {
    Device device = new Device();
    device.setId("example-device");
    device.addIdentifier()
        .setSystem("http://goodcare.org/devices/id")
        .setValue("345675");

    return device;
  }

  public static Patient createPatient() {
    // Create a Patient resource
    Patient patient = new Patient();
    patient.setId("example-patient");
    patient.addName()
        .setUse(org.hl7.fhir.r4.model.HumanName.NameUse.OFFICIAL)
        .addGiven("Cao Tuan")
        .setFamily("Kiet");

    try {
      String date_string = "08-04-2002";
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      patient.setGender(AdministrativeGender.MALE);
      patient.setBirthDate(formatter.parse(date_string));

    } catch (ParseException e) {
      System.err.println(e.getMessage());
    }

    patient.addTelecom()
        .setSystem(ContactPointSystem.PHONE)
        .setUse(ContactPointUse.HOME)
        .setValue("0963985652");

    patient.addTelecom()
        .setSystem(ContactPointSystem.EMAIL)
        .setUse(ContactPointUse.HOME)
        .setValue("caotuankietc3a@gmail.com");

    patient.addAddress()
        .setUse(AddressUse.HOME)
        .setCountry("VN")
        .setCity("HCM")
        .addLine("5 Chan Hung p6 Q.Tan Binh");

    return patient;
  }
}
