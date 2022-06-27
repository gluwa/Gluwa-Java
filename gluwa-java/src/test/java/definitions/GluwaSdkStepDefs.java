package definitions;

import com.gluwa.sdk.Currency;
import com.gluwa.sdk.GluwaResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.gluwa.sdk.TransactionTests;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.*;

public class GluwaSdkStepDefs {

    TransactionTests txTest = new TransactionTests();
    GluwaResponse result;

    @When("I post transaction via Gluwa SDK for \"([^\"]*)\"$")
    public void iPostTransactionViaGluwaSDKForCurrency(Currency currency) {
        result = txTest.PostTransactionTest_Pos(currency);
        System.out.println("=====================================");
        System.out.println("Status code: " + result.getCode());
    }

    @Then("I validate response that transaction is created")
    public void iValidateResponseThatTransactionIsCreated() {
        assertThat(result.getCode()).isEqualTo(202);
        assertThat(result.getReason()).isEqualTo("Accepted");
    }

    @When("I get payment QR code via Gluwa SDK")
    public void iGetPaymentQRCodeViaGluwaSDK() {
        result = txTest.getPaymentQRCodeTest_Pos();
        System.out.println("=====================================");
        System.out.println("Status code: " + result.getCode());
    }

    @Then("I validate get response")
    public void iValidateResponse() {
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getReason()).isEqualTo("OK");
    }

    @When("I get list of transactions for \"([^\"]*)\"$")
    public void iGetListOfTransactionsFor(Currency currency) {
        result = txTest.getListTransactionHistoryTest_Pos(currency);
        System.out.println("=====================================");
        System.out.println("Status code: " + result.getCode());
        System.out.println("List of Transactions: " + result.getMapList());
    }

}