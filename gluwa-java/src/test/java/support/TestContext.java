package support;


import com.gluwa.sdk.GluwaSDKNetworkException;
import definitions.GluwaSdkStepDefs;
import org.json.JSONArray;
import org.json.JSONObject;

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
