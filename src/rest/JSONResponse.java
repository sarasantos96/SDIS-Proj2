package rest;

import logic.Message;
import logic.Task;
import logic.User;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JSONResponse {
    private String type;
    private boolean success;
    private JSONObject jsonObject;
    private ArrayList<Task> tasks;
    private ArrayList <Message> messages;
    private ArrayList<User> users;
    private User logUser;

    public JSONResponse(boolean success) {
        this.success = success;
        jsonObject = new JSONObject();
        tasks = new ArrayList<>();
        messages = new ArrayList<>();
        users = new ArrayList<>();
        logUser = null;
    }

    public JSONResponse(String json) throws JSONException{
        jsonObject = new JSONObject(json);
        tasks = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public boolean parseJSONResponse() throws JSONException{
        if(jsonObject.has("login")){
            type ="login";
            JSONObject children = (JSONObject) jsonObject.get("login");
            success = children.getBoolean("success");
            if(success){
                String username = children.getString("username");
                String name = children.getString("name");
                int id = children.getInt("id");
                logUser = new User(name, username,id);

            }
            return true;

        }else if(jsonObject.has("signIn")){
            type = "signIn";
            JSONObject children = (JSONObject) jsonObject.get("signIn");
            success = children.getBoolean("success");
            return true;

        }else if(jsonObject.has("createGroup")){
            type = "createGroup";
            JSONObject children = (JSONObject) jsonObject.get("createGroup");
            success = children.getBoolean("success");
            return true;

        }else if(jsonObject.has("joinGroup")){
            type = "joinGroup";
            JSONObject children = (JSONObject) jsonObject.get("joinGroup");
            success = children.getBoolean("success");
            return true;

        }else if(jsonObject.has("sendMessage")){
            type = "sendMessage";
            JSONObject children = (JSONObject) jsonObject.get("sendMessage");
            success = children.getBoolean("success");
            return true;

        }else if(jsonObject.has("addToDo")){
            type = "addToDo";
            JSONObject children = (JSONObject) jsonObject.get("addToDo");
            success = children.getBoolean("success");
            return true;

        }else if(jsonObject.has("checkToDo")){
            type = "checkToDo";
            JSONObject children = (JSONObject) jsonObject.get("checkToDo");
            success = children.getBoolean("success");
            return true;

        }else if(jsonObject.has("getMessagesGroup")){
            type = "getMessagesGroup";
            JSONObject children = (JSONObject) jsonObject.get("getMessagesGroup");
            parseGetMessages(children);
            return true;
        }else if(jsonObject.has("getTodoGroup")){
            type = "getTodoGroup";
            JSONObject children = (JSONObject) jsonObject.get("getTodoGroup");
            parseGetTasks(children);
            return true;
        }else if(jsonObject.has("getUsers")){
            type = "getUsers";
            JSONObject children = (JSONObject) jsonObject.get("getUsers");
            parseGetUsers(children);
            return true;
        }else {
            return false;
        }
    }

    public void logInResponse() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("success",success);
        jsonObject.put("login",obj);
    }

    public void logInResponse(User user) throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("success",success);
        obj.put("username",user.getUsername());
        obj.put("name", user.getName());
        obj.put("id",user.getId());
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

    public void getTodoGroup(ArrayList<Task> tasks) throws JSONException{
        JSONObject obj = new JSONObject();
        int id = 1;
        for(Task t : tasks){
                JSONObject task = new JSONObject();
                task.put("text",t.getTask());
                task.put("id", t.getId());
                task.put("isDone",t.isIsdone());
                obj.put("task"+id,task);
                id++;
        }

        jsonObject.put("getTodoGroup",obj);
    }



    public void getUsersResponse(ArrayList<User> users) throws JSONException{
        JSONObject object = new JSONObject();
        int id = 1;
        for(User user : users){
            JSONObject u = new JSONObject();
            u.put("id", user.getId());
            u.put("name", user.getName());
            u.put("username", user.getUsername());
            object.put("user"+id,u);
            id++;
        }

        jsonObject.put("getUsers",object);
    }


    public boolean isSuccess() {
        return success;
    }

    public void parseGetUsers(JSONObject children) throws  JSONException{
        ArrayList<User> users = new ArrayList<>();
        int i = children.length();
        int j = 1;
        while(i > 0){
            String s = children.get("user"+j).toString();
            JSONObject user = new JSONObject(s);
            String username = user.getString("username");
            String name = user.getString("name");
            int id = user.getInt("id");
            User u = new User(name,username,id);
            users.add(u);
            i--;
            j++;
        }
        this.users = new ArrayList<>(users);
    }

    public void parseGetTasks(JSONObject children) throws JSONException{
        ArrayList<Task> tasks = new ArrayList<>();
        int i = children.length();
        int j = 1;
        while(i > 0){
            String s = children.get("task"+j).toString();
            JSONObject task = new JSONObject(s);
            String text = task.getString("text");
            int id = task.getInt("id");
            boolean isDone = task.getBoolean("isDone");
            Task t = new Task(text,isDone,id);
            tasks.add(t);
            i--;
            j++;
        }
        this.tasks = new ArrayList<>(tasks);
    }

    public void parseGetMessages(JSONObject children) throws JSONException{
        ArrayList<Message> messages = new ArrayList<>();
        int i = children.length();
        int j = 1;
        while(i > 0){
            String s = children.get("message"+j).toString();
            JSONObject message = new JSONObject(s);
            String text = message.getString("text");
            String user = message.getString("user");
            Message m = new Message(text,new User("",user,0),0);
            messages.add(m);
            i--;
            j++;
        }
        this.messages = new ArrayList<>(messages);
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }

    public String getType() {
        return type;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getLogUser() {
        return logUser;
    }
}
