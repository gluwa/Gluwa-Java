package definitions;

import com.gluwa.sdk.GluwaResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import support.Configuration;
import support.TransactionTests;

public class GluwaSdkStepDefs {

    TransactionTests txTest = new TransactionTests();

    @When("I post transaction via Gluwa SDK")
    public void iPostTransactionViaGluwaSDK() {
        try {
            GluwaResponse result = txTest.getPaymentQRCodeTest_Pos();
            System.out.println(result);;
        } catch (NullPointerException e) {
            System.out.println("Cannot invoke \"org.web3j.crypto.ECKeyPair.getPublicKey()\" because \"keyPair\" is null");
        }


    }

    @Then("I validate response that transaction is created")
    public void iValidateResponseThatTransactionIsCreated() {

    }
}
