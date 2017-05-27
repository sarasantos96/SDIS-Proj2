package logic;

public class User {
    private final String name;
    private final String username;
    private final int id;

    public User(String name, String username, int id) {
        this.name = name;
        this.username = username;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }
}
