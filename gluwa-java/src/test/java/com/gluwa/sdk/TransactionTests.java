package com.gluwa.sdk;

import com.gluwa.sdk.*;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import static org.junit.Assert.assertNotNull;

public class TransactionTests {

    static Configuration conf = new ConfigurationForTest();
    static GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);
    static GluwaTransaction transaction = new GluwaTransaction();

    /***
     * @description = Positive test to post transaction with USDCG token
     * @return = Base64 PNG format QR code
     */
    public GluwaResponse postTransactionTest(Currency currency) {
        GluwaApiSDKImpl gluwaApiSDK = new GluwaApiSDKImpl(new ConfigurationForTest());

        transaction.setCurrency(currency); // Currency.KRWG, Currency.NGNG
        transaction.setAmount("1");
        transaction.setTargetAddress("0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9");
        transaction.setNote("This is the Test");
        transaction.setMerchantOrderID("My Order Id:20200101");
        transaction.setIdem(UUID.randomUUID());
        return gluwaApiSDK.postTransaction(transaction);
    }

    /***
     * @description = Positive test to get payment QR code in base64 format
     * @return = Base64 PNG format QR code
     */
    public GluwaResponse getPaymentQRCodeTest_Pos() {
        transaction.setCurrency(Currency.USDCG);
        transaction.setAmount("51");
        transaction.setExpiry(1800);
        return wrapper.getPaymentQRCode(transaction);
    }

    /***
     * When PR for this method is merged to master, this test will be refactored correctly
     */
    public void getPaymentQRCodeWithPayloadTest_Pos() {
        transaction.setCurrency(Currency.USDCG);
        transaction.setAmount("51");
        transaction.setExpiry(1800);
        // getPaymentQRCode API returns QR code png image as a Base64 string and payload.
        //return sdkImpl.getPaymentQRCodeWithPayload(transaction);
    }

    /***
     * When PR for this method is merged to master, this test will be refactored correctly
     */
    public static GluwaResponse getListTransactionHistoryTest(Currency currency) {
        transaction.setCurrency(currency);
        transaction.setLimit(2);
        transaction.setStatus("Confirmed");
        transaction.setOffset(0);
        return wrapper.getListTransactionHistory(transaction);
    }

    public GluwaResponse getListTransactionDetail_test(Currency currency) {
        GluwaResponse transactionList = getListTransactionHistoryTest(currency);
        String txnHash = transactionList.getMapList().get(0).get("TxnHash").toString();
        transaction.setCurrency(currency);
        transaction.setTxnHash(txnHash);
        return wrapper.getListTransactionDetail(transaction);
    }

    public GluwaResponse getAddressTest(Currency currency) {
        transaction.setCurrency(currency);
        return wrapper.getAddresses(transaction);
    }
}
