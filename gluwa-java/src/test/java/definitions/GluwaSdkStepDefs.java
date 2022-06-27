package definitions;

import com.gluwa.sdk.GluwaResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.gluwa.sdk.TransactionTests;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.*;

public class GluwaSdkStepDefs {

    TransactionTests txTest = new TransactionTests();
    GluwaResponse result;

    @When("I post transaction via Gluwa SDK")
    public void iPostTransactionViaGluwaSDK() {
        result = txTest.getPaymentQRCodeTest_Pos();
        System.out.println("Status code: " + result.getCode());
    }

    @Then("I validate response that transaction is created")
    public void iValidateResponseThatTransactionIsCreated() {
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getReason()).isEqualTo("OK");
    }
}
