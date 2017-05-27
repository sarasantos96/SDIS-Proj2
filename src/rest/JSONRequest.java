package rest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class JSONRequest{
    private String type;
    private String username;
    private String name;
    private String password;
    private String groupname;
    private int idUser;
    private int idGroup;
    private int idTodo;
    private String message_text;
    private String todo_text;
    private String request;
    private JSONObject jsonObject;

    public JSONRequest(String type, String username, String name, String password, String groupname, String idUser, String idGroup, String idTodo, String message_text, String todo_text) throws JSONException {
        if(!type.equals("")) this.type = type;
        if(!username.equals("")) this.username = username;
        if(!name.equals("")) this.name = name;
        if(!password.equals("")) this.password = hashPassword(password);
        if(!groupname.equals("")) this.groupname = groupname;
        if(!idUser.equals("")) this.idUser =  Integer.parseInt(idUser);
        if(!idGroup.equals("")) this.idGroup = Integer.parseInt(idGroup);
        if(!idTodo.equals("")) this.idTodo = Integer.parseInt(idTodo);
        if(!message_text.equals("")) this.message_text = message_text;
        if(!todo_text.equals("")) this.todo_text = todo_text;
        jsonObject = new JSONObject();
        request = new String();
        buildRequest();
    }

    public JSONRequest(String request) throws JSONException {
        this.request = request;
        jsonObject = new JSONObject(this.request);
    }

    public void buildRequest() throws JSONException{
        JSONObject obj = new JSONObject();
        switch (type){
            case "login":
                obj.put("username",username);
                obj.put("password",password);
                break;
            case "signIn":
                obj.put("name", name);
                obj.put("username",username);
                obj.put("password",password);
                break;
            case "createGroup":
                obj.put("name", groupname);
                break;
            case "joinGroup":
                obj.put("idGroup", idGroup);
                obj.put("idUser", idUser);
                break;
            case "sendMessage":
                obj.put("idGroup", idGroup);
                obj.put("idUser", idUser);
                obj.put("text",message_text);
                break;
            case "addToDo":
                obj.put("idGroup", idGroup);
                obj.put("text",todo_text);
                break;
            case "checkToDo":
                obj.put("idToDo", idTodo);
                break;
        }

        jsonObject.put(type,obj);
        request = jsonObject.toString();
    }

    public boolean parseRequest() throws JSONException{
        if(jsonObject.has("login")){
            type ="login";
            parseLogin();
            return true;

        }else if(jsonObject.has("signIn")){
            type = "signIn";
            parseSignIn();
            return true;

        }else if(jsonObject.has("createGroup")){
            type = "createGroup";
            parseCreateGroup();
            return true;

        }else if(jsonObject.has("joinGroup")){
            type = "joinGroup";
            parseJoinGroup();
            return true;

        }else if(jsonObject.has("sendMessage")){
            type = "sendMessage";
            parseSendMessage();
            return true;

        }else if(jsonObject.has("addToDo")){
            type = "addToDo";
            parseAddToDo();
            return true;

        }else if(jsonObject.has("checkToDo")){
            type = "checkToDo";
            parseCheckToDo();
            return true;

        }else {
            return false;
        }
    }

    public boolean parseLogin() throws JSONException{
        JSONObject children = (JSONObject) jsonObject.get("login");
        username = children.getString("username");
        password = children.getString("password");
        if(username.equals("") || password.equals("")){
            return false;
        }

        return true;
    }

    public boolean parseSignIn() throws  JSONException{
        JSONObject children = (JSONObject) jsonObject.get("signIn");
        username = children.getString("username");
        password = children.getString("password");
        name = children.getString("name");
        if(username.equals("") || password.equals("") || name.equals("")){
            return false;
        }

        return true;
    }

    public boolean parseCreateGroup() throws JSONException{
        JSONObject children = (JSONObject) jsonObject.get("createGroup");
        groupname = children.getString("name");
        if(groupname.equals("")){
            return false;
        }
        return true;
    }

    public boolean parseJoinGroup() throws JSONException{
        JSONObject children = (JSONObject) jsonObject.get("joinGroup");
        idGroup = children.getInt("idGroup");
        idUser = children.getInt("idUser");
        return true;
    }

    public boolean parseSendMessage() throws JSONException{
        JSONObject children = (JSONObject) jsonObject.get("sendMessage");
        idGroup = children.getInt("idGroup");
        idUser = children.getInt("idUser");
        message_text = children.getString("text");
        if(message_text.equals("")){
            return false;
        }

        return true;
    }

    public boolean parseAddToDo() throws JSONException{
        JSONObject children = (JSONObject) jsonObject.get("addToDo");
        idGroup = children.getInt("idGroup");
        todo_text = children.getString("text");
        if(todo_text.equals("")){
            return false;
        }

        return true;
    }

    public boolean parseCheckToDo() throws JSONException{
        JSONObject children = (JSONObject) jsonObject.get("checkToDo");
        idTodo = children.getInt("idToDo");
        return true;
    }

    public String hashPassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);

        return encoded;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdTodo() {
        return idTodo;
    }

    public void setIdTodo(int idTodo) {
        this.idTodo = idTodo;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getTodo_text() {
        return todo_text;
    }

    public void setTodo_text(String todo_text) {
        this.todo_text = todo_text;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
