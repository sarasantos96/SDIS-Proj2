package rest;

import logic.Message;
import org.json.JSONException;
import org.json.JSONObject;
import sun.text.resources.nl.JavaTimeSupplementary_nl;

import java.util.ArrayList;
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

    public void getMessagesGroupResponse(ArrayList<Message> messages) throws JSONException{
        JSONObject obj = new JSONObject();
        int id = 1;
        for(Message m : messages){
            JSONObject message = new JSONObject();
            message.put("text",m.getContent());
            message.put("user",m.getSender().getName());

            obj.put("message"+id,message);
            id++;
        }

        jsonObject.put("getMessagesGroup",obj);
    }

    public void getTodoGroup() throws JSONException{

    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }

}
