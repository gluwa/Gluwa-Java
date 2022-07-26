package com.gluwa.sdk;

import java.util.UUID;

public class TransactionTests {

    static Configuration conf = new ConfigurationForTest();
    static GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);
    static GluwaTransaction transaction = new GluwaTransaction();

    /***
     * @description = Positive test to post transaction with USDCG token
     * @return = Base64 PNG format QR code
     */
    public GluwaResponse postTransactionTest(Object currency) {
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
    public GluwaResponse getPaymentQRCodeTest_Pos(Object currency) {
        transaction.setCurrency(currency);
        transaction.setAmount("51");
        transaction.setExpiry(1800);
        return wrapper.getPaymentQRCode(transaction);
    }

    public GluwaResponse getPaymentQRCodeWithPayloadTest_Pos(Object currency) {
        transaction.setCurrency(currency);
        transaction.setAmount("102");
        transaction.setExpiry(1800);
        transaction.setFee("1");
        return wrapper.getPaymentQRCodeWithPayload(transaction);
    }

    public static GluwaResponse getListTransactionHistoryTest(String status, Object currency) {
        transaction.setCurrency(currency);
        transaction.setLimit(2);
        transaction.setStatus(status);
        transaction.setOffset(0);
        return wrapper.getListTransactionHistory(transaction);
    }

    public GluwaResponse getListTransactionDetail_test(Object currency) {
        GluwaResponse transactionList = getListTransactionHistoryTest("Confirmed", currency);
        String txnHash = transactionList.getMapList().get(0).get("TxnHash").toString();
        transaction.setCurrency(currency);
        transaction.setTxnHash(txnHash);
        return wrapper.getListTransactionDetail(transaction);
    }

    public GluwaResponse getAddressTest(Object currency) {
        transaction.setCurrency(currency);
        return wrapper.getAddresses(transaction);
    }

    public GluwaResponse getFeeTest_test(Object currency) {
        transaction.setCurrency(currency);
        transaction.setAmount("51");
        return wrapper.getFee(transaction);
    }
}
