package Model;

public class User {

    public String id;
    public String name;
    public String userName;
    public String password;

    public User(String id, String userName, String name, String password) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
    }
}