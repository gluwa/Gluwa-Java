package support;


import com.gluwa.sdk.*;
import definitions.GluwaSdkStepDefs;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;

import io.cucumber.java8.He;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

/***
 * All shared methods to be added in this class
 */
public class TestContext {

    private int responseCode;
    private String responseMessage;

    public int getResponseCode(){
        return responseCode;
    }

    public void setResponseCode(int newCode){
        this.responseCode = newCode;
    }

    public String getResponseMessage(){
        return responseMessage;
    }

    public void setResponseMessage(String newMessage){
        this.responseMessage = newMessage;
    }

    public void setResponseMessageAndCode(String newMessage, int newCode){
        this.responseMessage = newMessage.trim();
        this.responseCode = newCode;
    }

    public String extractValidationMessageFromPath(GluwaSDKNetworkException ex) {
        JSONObject badRequestJson = ex.getResponseContents();
        StringBuilder errorMessage = new StringBuilder();
        try {
            JSONArray innerErrors = badRequestJson.getJSONArray("InnerErrors");
            for (int i = 0; i < innerErrors.length(); i++) {
                errorMessage.append(innerErrors.getJSONObject(i).getString("Message"));
            }
        } catch (Exception e) {
            System.out.println("No Inner Errors");
            errorMessage = new StringBuilder(badRequestJson.getString("Message"));
        }
        return errorMessage.toString();
    }
}
