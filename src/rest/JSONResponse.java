package rest;

import org.json.JSONException;
import org.json.JSONObject;
import sun.text.resources.nl.JavaTimeSupplementary_nl;

import java.util.List;

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

    public void createGroupResponse() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("success",success);
        jsonObject.put("createGroup",obj);
    }

    public void joinGroupResponse() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("success",success);
        jsonObject.put("joinGroup",obj);
    }

    public void sendMessageResponse() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("success",success);
        jsonObject.put("sendMessage",obj);
    }

    public void addToDoResponse() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("success",success);
        jsonObject.put("addToDo",obj);
    }

    public void checkToDoResponse() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("success",success);
        jsonObject.put("checkToDo",obj);
    }

    public void getMessagesGroup() throws JSONException{
        //TODO: iterate through all messages
        JSONObject message = new JSONObject();
        message.put("text","ehehh");
        message.put("user","dani");

        JSONObject obj = new JSONObject();
        obj.put("message1",message);
        obj.put("message2",message);
        jsonObject.put("getMessagesGroup",obj);
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }

}
