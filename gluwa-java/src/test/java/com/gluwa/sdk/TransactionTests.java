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
    public GluwaResponse postTransactionTest(Object currency, String amount, String targetAddress, String fee, String signature) {
        GluwaApiSDKImpl gluwaApiSDK = new GluwaApiSDKImpl(new ConfigurationForTest());

        transaction.setCurrency(currency); // Currency.KRWG, Currency.NGNG
        transaction.setAmount(amount);
        transaction.setTargetAddress(targetAddress);
        transaction.setNote("This is the Test");
        transaction.setMerchantOrderID("My Order Id:20200101");
        transaction.setFee(fee);
        transaction.setIdem(UUID.randomUUID());
        return gluwaApiSDK.postTransaction(transaction, signature);
    }

    /***
     * @description = Positive test to get payment QR code in base64 format
     * @return = Base64 PNG format QR code
     */
    public GluwaResponse getPaymentQRCodeTest_Pos(Object currency, String amount, int expiry, String fee, String basicAuth) {
        transaction.setCurrency(currency);
        transaction.setAmount(amount);
        transaction.setExpiry(expiry);
        transaction.setFee(fee);
        return wrapper.getPaymentQRCode(transaction, basicAuth);
    }

    public GluwaResponse getPaymentQRCodeWithPayloadTest_Pos(Object currency, String amount, int expiry, String fee, String signature) {
        transaction.setCurrency(currency);
        transaction.setAmount(amount);
        transaction.setExpiry(expiry);
        transaction.setFee(fee);
        return wrapper.getPaymentQRCodeWithPayload(transaction, signature);
    }

    public static GluwaResponse getListTransactionHistoryTest(int limit,
                                                              int offset,
                                                              String status,
                                                              String signature,
                                                              Object currency)
    {
        transaction.setCurrency(currency);
        transaction.setLimit(limit);
        transaction.setStatus(status);
        transaction.setOffset(offset);
        return wrapper.getListTransactionHistory(transaction, signature);
    }

    public GluwaResponse getListTransactionDetail_test(String txnHash, Object currency, String signature) {
        //GluwaResponse transactionList = getListTransactionHistoryTest(0,0,"Confirmed", currency, signature);
        //String txnHash = transactionList.getMapList().get(0).get("TxnHash").toString();
        transaction.setCurrency(currency);
        transaction.setTxnHash(txnHash);
        return wrapper.getListTransactionDetail(transaction, signature);
    }

    public GluwaResponse getAddressTest(Object currency) {
        transaction.setCurrency(currency);
        return wrapper.getAddresses(transaction);
    }

    public GluwaResponse getFeeTest_test(Object currency, String amount) {
        transaction.setCurrency(currency);
        transaction.setAmount(amount);
        return wrapper.getFee(transaction);
    }
}
