package support;

import com.gluwa.sdk.Currency;
import com.gluwa.sdk.GluwaApiSDKImpl;
import com.gluwa.sdk.GluwaResponse;
import com.gluwa.sdk.GluwaTransaction;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class TransactionTests {


    /*public void PostTransactionTest_Pos() {
        GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(config.ConfigurationForTest());
        GluwaTransaction transaction = new GluwaTransaction();

        transaction.setCurrency(Currency.USDCG); // Currency.KRWG, Currency.NGNG
        transaction.setAmount("1");
        transaction.setTargetAddress("0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9");
        transaction.setFee("0");
        GluwaResponse result = wrapper.getAddresses(transaction);
    }*/

    public GluwaResponse getPaymentQRCodeTest_Pos() {
        Configuration config = new Configuration();

        GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(config.ConfigurationForTest());
        GluwaTransaction transaction = new GluwaTransaction();
        transaction.setCurrency(Currency.USDCG);
        transaction.setAmount("51");
        transaction.setExpiry(1800);
        return wrapper.getPaymentQRCode(transaction);
    }
}
