package definitions;

import com.gluwa.sdk.*;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.*;

public class GluwaSdkStepDefs {

    TransactionTests txTest = new TransactionTests();
    GluwaResponse result;
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> map = new HashMap<>();

    private Currency cryptoCurrency;

    private int actualStatusCode;

    private String actualBadResponseMessage;

    @When("I post transaction via Gluwa SDK for {}")
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


    @When("I get payment QR code via Gluwa SDK for currency {}")
    public void iGetPaymentQRCodeViaGluwaSDK(Currency currency) {
        result = txTest.getPaymentQRCodeTest_Pos(currency);
        System.out.println("=====================================");
        System.out.println("Status code: " + result.getCode());
    }


    @Then("I validate get response")
    public void iValidateResponse() {
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getReason()).isEqualTo("OK");
    }


    @When("I get list of transactions with {} status for {}")
    public void iGetListOfTransactionsFor(String status, Currency currency) {
        //cryptoCurrency = Currency.valueOf(currency);
        result = TransactionTests.getListTransactionHistoryTest(status, currency);
        System.out.println("=====================================");
        System.out.println("Status code: " + result.getCode());
        System.out.println("List of Transactions: " + result.getMapList());
    }


    @When("I get a transaction by hash for {}")
    public void iGetATransactionBy(Currency currency) {
        result = txTest.getListTransactionDetail_test(currency);
        System.out.println("=====================================");
        System.out.println("Status code: " + result.getCode());
        System.out.println("Transaction details: " + result.getMapList());
    }


    @When("I post transaction via Gluwa SDK using unsupported currency for {}")
    public void iPostTransactionViaGluwaSDKUsingInvalidCurrencyAs(Currency unsupportedCurrency) {
        try {
            result = txTest.postTransactionTest(unsupportedCurrency);
            System.out.println("=====================================");
            System.out.println("Status code: " + result.getCode());
            System.out.println("REASON: " + result.getReason());
            System.out.println("RESPONSE BODY: " + result.getBody());
        } catch (GluwaSDKNetworkException e) {
            actualStatusCode = e.getStatusCode();
            actualBadResponseMessage = e.extractBadRequestMessage();
        }
    }


    @Then("I validate request response {} and {}")
    public void iValidateBadRequestResponse(int code, String message) {
        assertThat(actualStatusCode).isEqualTo(code);
        assertThat(actualBadResponseMessage).isEqualTo(message);
    }


    @When("I get list of transactions with {} for unsupported currency {}")
    public void iGetListOfTransactionsForInvalidCurrency(String status, Currency unsupportedCurrency) {
        try {
            result = TransactionTests.getListTransactionHistoryTest(status, unsupportedCurrency);
            System.out.println("=====================================");
            System.out.println("Status code: " + result.getCode());
            System.out.println("REASON: " + result.getReason());
            System.out.println("RESPONSE BODY: " + result.getBody());
        } catch (GluwaSDKNetworkException e) {
            actualStatusCode = e.getStatusCode();
            actualBadResponseMessage = e.extractBadRequestMessage();
        }
    }


    @When("I get address via Gluwa SDK for {}")
    public void iGetAddressViaGluwaSDK(Currency currency) {
        result = txTest.getAddressTest(currency);
        System.out.println("=====================================");
        System.out.println("Status code: " + result.getCode());
        System.out.println("REASON: " + result.getReason());
        System.out.println("RESPONSE BODY: " + result.getBody());
    }


    @When("I get payment QR code with Payload via Gluwa SDK for {}")
    public void iGetPaymentQRCodeWithPayloadViaGluwaSDK(Currency currency) {
        result = txTest.getPaymentQRCodeWithPayloadTest_Pos(currency);
        System.out.println("=====================================");
        System.out.println("Status code: " + result.getCode());
        System.out.println("REASON: " + result.getReason());
        System.out.println("RESPONSE BODY: " + result.getBody());
    }


    @When("I get fee for currency {}")
    public void iGetFeeForCurrencyTest(Object currency) {
        try {
            result = txTest.getFeeTest_test(currency);
        } catch (GluwaSDKNetworkException e) {
            actualStatusCode = e.getStatusCode();
            actualBadResponseMessage = e.extractBadRequestMessage();
        }
    }
}