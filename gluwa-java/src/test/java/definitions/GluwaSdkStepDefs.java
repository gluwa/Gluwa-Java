package definitions;

import com.gluwa.sdk.Currency;
import com.gluwa.sdk.GluwaResponse;
import com.gluwa.sdk.GluwaSDKException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.gluwa.sdk.TransactionTests;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.*;

public class GluwaSdkStepDefs {

    TransactionTests txTest = new TransactionTests();
    GluwaResponse result;
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> map = new HashMap<>();

    @When("I post transaction via Gluwa SDK for \"([^\"]*)\"$")
    public void iPostTransactionViaGluwaSDKForCurrency(Currency currency) {
        result = txTest.postTransactionTest(currency);
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
        result = TransactionTests.getListTransactionHistoryTest(currency);
        System.out.println("=====================================");
        System.out.println("Status code: " + result.getCode());
        System.out.println("List of Transactions: " + result.getMapList());
    }

//    @When("I get a \"([^\"]*)\"$ transaction by \"hash\"")
//    public void iGetTransactionByHash(Currency currency, String hash){
//        result = txTest.getListTransactionDetail_test(currency, hash);
//        System.out.println("=====================================");
//        System.out.println("Status code: " + result.getCode());
//        System.out.println("Transaction details: " + result.getMapList());
//    }

    @When("I get a transaction by hash for \"([^\"]*)\"$")
    public void iGetATransactionBy(Currency currency) {
        result = txTest.getListTransactionDetail_test(currency);
        System.out.println("=====================================");
        System.out.println("Status code: " + result.getCode());
        System.out.println("Transaction details: " + result.getMapList());
    }

    @When("I post transaction via Gluwa SDK using invalid currency as \"([^\"]*)\"$")
    public void iPostTransactionViaGluwaSDKUsingInvalidCurrencyAs(Currency invalidCurrency) {
        try {
            result = txTest.postTransactionTest(invalidCurrency);
            System.out.println("=====================================");
            System.out.println("Status code: " + result.getCode());
            System.out.println("REASON: " + result.getReason());
            System.out.println("RESPONSE BODY: " + result.getBody());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Then("I validate bad request response")
    public void iValidateBadRequestResponse() {
        assertThat(map.get("Code")).isEqualTo("BadRequest");
        assertThat(map.get("Message")).isEqualTo("Unsupported currency GCRE.");
        //assertThat(result.getReason()).isEqualTo("OK");
    }

    @When("I get list of transactions for invalid currency \"([^\"]*)\"$")
    public void iGetListOfTransactionsForInvalidCurrency(Currency invalidCurrency) {
        try {
            result = txTest.postTransactionTest(invalidCurrency);
            System.out.println("=====================================");
            System.out.println("Status code: " + result.getCode());
            System.out.println("REASON: " + result.getReason());
            System.out.println("RESPONSE BODY: " + result.getBody());
        } catch (Exception e) {
            try {
                map = objectMapper.readValue(e.getMessage(), Map.class);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @When("I get address via Gluwa SDK for \"([^\"]*)\"$")
    public void iGetAddressViaGluwaSDK(Currency currency) {
        result = txTest.getAddressTest(currency);
        System.out.println("=====================================");
        System.out.println("Status code: " + result.getCode());
        System.out.println("REASON: " + result.getReason());
        System.out.println("RESPONSE BODY: " + result.getBody());
    }
}