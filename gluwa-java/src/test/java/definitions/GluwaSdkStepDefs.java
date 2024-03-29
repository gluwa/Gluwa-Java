package definitions;

import com.gluwa.sdk.*;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import support.TestContext;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class GluwaSdkStepDefs {
    private int actualStatusCode;
    private String actualBadResponseMessage;
    private JSONObject responseContents;


    TransactionTests txTest = new TransactionTests();

    GluwaResponse result;

    TestContext response = new TestContext();

    @When("I post transaction via Gluwa SDK using parameters {} {} {} {} {}")
    public void iPostTransaction(Object unsupportedCurrency, String amount, String targetAddress, String fee, String signature) {
        try {
            result = txTest.postTransactionTest(unsupportedCurrency, amount, targetAddress, fee, signature);
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(response.extractValidationMessageFromPath(e),
                    e.getStatusCode());
            System.out.println(response.extractValidationMessageFromPath(e));
        }
    }


    @Then("I validate response that transaction is created")
    public void iValidateResponseThatTransactionIsCreated() {
        assertThat(result.getCode()).isEqualTo(202);
        assertThat(result.getReason()).isEqualTo("Accepted");
    }


    @When("I get payment QR code via Gluwa SDK using parameters {} {} {} {} {}")
    public void iGetPaymentQRCode(Object currency, String amount, int expiry, String fee, String basicAuth) {
        try {
            result = txTest.getPaymentQRCodeTest_Pos(currency, amount, expiry, fee, basicAuth);
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(response.extractValidationMessageFromPath(e),
                    e.getStatusCode());
            System.out.println(response.extractValidationMessageFromPath(e));
        }
    }

    // TODO depricate this method
    @Then("I validate get response")
    public void iValidateResponse() {
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getReason()).isEqualTo("OK");
    }

    @When("I get list of transactions with {} status for {}")
    public void iGetListOfTransactionsFor(String status, Currency currency) {
        result = TransactionTests.getListTransactionHistoryTest(0,0,status, "", currency);
    }

    @Then("I validate request response {} and {}")
    public void iValidateBadRequestResponse(int code, String message) {
        System.out.println(response.getResponseMessage());
        assertThat(response.getResponseCode()).isEqualTo(code);
        assertThat(response.getResponseMessage()).contains(message);
    }


    @When("I get list of transactions with {} for unsupported currency {}")
    public void iGetListOfTransactions(String status, Object currency) {
        try {
            result = TransactionTests.getListTransactionHistoryTest(0,0,status, "", currency);
        } catch (GluwaSDKNetworkException e) {
            actualStatusCode = e.getStatusCode();
            actualBadResponseMessage = e.extractBadRequestMessage().trim();
        }
    }


    @When("I get address via Gluwa SDK for {}")
    public void iGetAddress(Currency currency) {
        result = txTest.getAddressTest(currency);
    }

    @When("I get list of transactions using request parameters {} {} {} {} {}")
    public void iGetListOfTransactionsNegative(int limit, int offset, String status, String signature, Object Currency) {
        try {
            result = TransactionTests.getListTransactionHistoryTest(limit, offset, status, signature, Currency);
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(response.extractValidationMessageFromPath(e),
                    e.getStatusCode());
        }
    }

    @When("I get fee for transaction of {} for currency {}")
    public void iGetFeeForCurrencyTest(String amount, Object currency) {
        try {
            result = txTest.getFeeTest_test(currency, amount);
            System.out.println(result.getBody());
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(response.extractValidationMessageFromPath(e),
                    e.getStatusCode());
        }
    }

    @When("I get transaction using parameters {} {} {}")
    public void iGetTransactionByHash(String txnHash, Object currency, String signature) {
        try {
            result = txTest.getListTransactionDetail_test(txnHash, currency, signature);
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(response.extractValidationMessageFromPath(e),
                    e.getStatusCode());
        }

    }

    @When("I get payment QR code Payload via Gluwa SDK using parameters {} {} {} {} {}")
    public void iGetPaymentQRCodePayload(Object currency, String amount, int expiry, String fee, String basicAuth) {
        try {
            result = txTest.getPaymentQRCodeWithPayloadTest_Pos(currency, amount, expiry, fee, basicAuth);
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(response.extractValidationMessageFromPath(e),
                                                                  e.getStatusCode());
            System.out.println("Message: " + response.extractValidationMessageFromPath(e) + " Status Code: " + e.getStatusCode());
        }
    }

    @When("I get address via Gluwa SDK with invalid {}")
    public void iGetAddress(Object invalidCurrency) {
        try {
            result = txTest.getAddressTest(invalidCurrency);
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(response.extractValidationMessageFromPath(e),
                                                                  e.getStatusCode());
        }
    }
}