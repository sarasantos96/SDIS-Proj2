package rest;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONResponse {
    private boolean success;
    private JSONObject jsonObject;

    public JSONResponse(boolean success) {
        this.success = success;
        jsonObject = new JSONObject();
    }

    public void logInResponse() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("success",success);
        jsonObject.put("login",obj);
    }

    public void signInResponse() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("success",success);
        jsonObject.put("signIn",obj);
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }

}
