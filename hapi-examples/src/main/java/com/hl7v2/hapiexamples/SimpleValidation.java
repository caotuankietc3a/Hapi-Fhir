package com.hl7v2.hapiexamples;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.IValidatorModule;
import ca.uhn.fhir.validation.SchemaBaseValidator;
import ca.uhn.fhir.validation.ValidationResult;
import org.hl7.fhir.r4.model.Patient;

public class SimpleValidation {
  public static boolean validateResource(Patient p) {
    FhirContext ctx = FhirContext.forR4();

    FhirValidator val = ctx.newValidator();

    IValidatorModule module1 = new SchemaBaseValidator(ctx);
    IValidatorModule module2 = new SchemaBaseValidator(ctx);
    val.registerValidatorModule(module1);
    val.registerValidatorModule(module2);

    ValidationResult result = val.validateWithResult(p);
    if (result.isSuccessful()) {

      System.out.println("Validation passed");
      return true;
    }
    System.out.println("Validation failed");
    return false;
  }
}
