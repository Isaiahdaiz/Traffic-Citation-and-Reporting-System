package tcrs;

public class User {
    private String username;
    private String password;
    private String type;
    private String status;

    public User(String username, String password, String type, String status) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.status = status;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters for all fields

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}