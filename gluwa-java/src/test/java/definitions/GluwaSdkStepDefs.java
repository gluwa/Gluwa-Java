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


    private String getResponseMessage(JSONObject responseContents, GluwaSDKNetworkException ex) {
        try {
            JSONArray innerErrors = responseContents.getJSONArray("InnerErrors");
            actualBadResponseMessage = innerErrors.getJSONObject(0).getString("Message");
        } catch (Exception e) {
            System.out.println("No Inner Errors");
            actualBadResponseMessage = ex.extractBadRequestMessage();
        }
        return actualBadResponseMessage;
    }


    @When("I post transaction via Gluwa SDK for {}")
    public void iPostTransactionViaGluwaSDKForCurrency(Currency currency) {
        result = txTest.postTransactionTest(currency);
    }


    @Then("I validate response that transaction is created")
    public void iValidateResponseThatTransactionIsCreated() {
        assertThat(result.getCode()).isEqualTo(202);
        assertThat(result.getReason()).isEqualTo("Accepted");
    }


    @When("I get payment QR code via Gluwa SDK for currency {}")
    public void iGetPaymentQRCodeViaGluwaSDK(Currency currency) {
        try {
            result = txTest.getPaymentQRCodeTest_Pos(currency);
        } catch (GluwaSDKNetworkException e) {
            actualStatusCode = e.getStatusCode();
            actualBadResponseMessage = e.extractBadRequestMessage();
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
        result = TransactionTests.getListTransactionHistoryTest(0,0,status, currency);
    }


    @When("I post transaction via Gluwa SDK using unsupported currency for {}")
    public void iPostTransactionViaGluwaSDKUsingInvalidCurrencyAs(Currency unsupportedCurrency) {
        try {
            result = txTest.postTransactionTest(unsupportedCurrency);
        } catch (GluwaSDKNetworkException e) {
            actualStatusCode = e.getStatusCode();
            actualBadResponseMessage = e.extractBadRequestMessage();
        }
    }


    @Then("I validate request response {} and {}")
    public void iValidateBadRequestResponse(int code, String message) {
        System.out.println(response.getResponseMessage());
        assertThat(response.getResponseCode()).isEqualTo(code);
        assertThat(response.getResponseMessage()).isEqualTo(message);
    }


    @When("I get list of transactions with {} for unsupported currency {}")
    public void iGetListOfTransactionsForInvalidCurrency(String status, Object currency) {
        try {
            result = TransactionTests.getListTransactionHistoryTest(0,0,status, currency);
        } catch (GluwaSDKNetworkException e) {
            actualStatusCode = e.getStatusCode();
            actualBadResponseMessage = e.extractBadRequestMessage().trim();
        }
    }


    @When("I get address via Gluwa SDK for {}")
    public void iGetAddressViaGluwaSDK(Currency currency) {
        result = txTest.getAddressTest(currency);
    }


    @When("I get payment QR code with Payload via Gluwa SDK for {}")
    public void iGetPaymentQRCodeWithPayloadViaGluwaSDK(Currency currency) {
        result = txTest.getPaymentQRCodeWithPayloadTest_Pos(currency);
    }


    @When("I get fee for transaction of {} for currency {}")
    public void iGetFeeForCurrencyTest(String amount, Object currency) {
        try {
            result = txTest.getFeeTest_test(currency, amount);
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(getResponseMessage(e.getResponseContents(),e),
                                               e.getStatusCode());
        }
    }

    @When("I get payment QR code via Gluwa SDK for invalid currency {}")
    public void iGetPaymentQRCodeViaGluwaSDKForUnsupportedCurrencyCurrency(Object currency) {
        try {
            result = txTest.getPaymentQRCodeTest_Pos(currency);
        } catch (GluwaSDKNetworkException e) {
            actualStatusCode = e.getStatusCode();
            actualBadResponseMessage = e.extractBadRequestMessage();
        }
    }

    @When("I post transaction via Gluwa SDK using invalid currency for {}")
    public void iPostTransactionViaGluwaSDKUsingInvalidCurrencyForCurrency(Object invalidCurrency) {
        try {
            result = txTest.postTransactionTest(invalidCurrency);
        } catch (GluwaSDKNetworkException e) {
            actualStatusCode = e.getStatusCode();
            System.out.println(e.extractBadRequestMessage());
            actualBadResponseMessage = e.extractBadRequestMessage();
        }
    }

    @When("I get list of transactions using request parameters {} {} {} {}")
    public void iGetListOfTransactionsNegative(int limit,
                                               int offset,
                                               String status,
                                               Object invalidCurrency)
    {
        try {
            result = TransactionTests.getListTransactionHistoryTest(limit,
                                                                    offset,
                                                                    status,
                                                                    invalidCurrency);
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(getResponseMessage(e.getResponseContents(),e),
                                                e.getStatusCode());
        }
    }

    @When("I get transaction by {} for {}")
    public void iGetTransactionByHashForUnsupportedCurrency(String txnHash, Object currency) {
        try {
            result = txTest.getListTransactionDetail_test(txnHash, currency);
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(getResponseMessage(e.getResponseContents(),e),
                                               e.getStatusCode());
        }

    }


    @When("I get payment QR code Payload via Gluwa SDK for invalid {}")
    public void iGetPaymentQRCodePayloadViaGluwaSDKForUnsupportedCurrency(Object Currency) {
        try {
            result = txTest.getPaymentQRCodeWithPayloadTest_Pos(Currency);
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(getResponseMessage(e.getResponseContents(),e),
                                                                  e.getStatusCode());
        }
    }

    @When("I get address via Gluwa SDK with invalid {}")
    public void iGetAddressViaGluwaSDKWithInvalidInvalidCurrency(Object invalidCurrency) {
        try {
            result = txTest.getAddressTest(invalidCurrency);
        } catch (GluwaSDKNetworkException e) {
            response.setResponseMessageAndCode(getResponseMessage(e.getResponseContents(),e),
                                                                  e.getStatusCode());
        }
    }
}