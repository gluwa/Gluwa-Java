package support;


import definitions.GluwaSdkStepDefs;

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
}
