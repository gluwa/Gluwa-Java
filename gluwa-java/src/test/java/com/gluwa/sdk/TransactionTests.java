package com.gluwa.sdk;

import com.gluwa.sdk.*;
import org.junit.Test;
import java.util.concurrent.CompletableFuture;
import static org.junit.Assert.assertNotNull;

public class TransactionTests {

    public GluwaResponse PostTransactionTest_Pos() {
        GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(new ConfigurationForTest());
        GluwaTransaction transaction = new GluwaTransaction();

        transaction.setCurrency(Currency.USDCG); // Currency.KRWG, Currency.NGNG
        transaction.setAmount("1");
        transaction.setTargetAddress("0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9");
        transaction.setFee("0");
        return wrapper.getAddresses(transaction);
    }

    public GluwaResponse getPaymentQRCodeTest_Pos() {
        Configuration conf = new ConfigurationForTest();
        GluwaApiSDKImpl sdkImpl = new GluwaApiSDKImpl(conf);

        GluwaTransaction transaction = new GluwaTransaction();
        transaction.setCurrency(Currency.USDCG);
        transaction.setAmount("51");
        transaction.setExpiry(1800);
        return sdkImpl.getPaymentQRCode(transaction);
    }
}
