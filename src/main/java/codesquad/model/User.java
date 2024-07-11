package codesquad.model;

public class User {

    private String userId;
    private String password;
    private String name;

    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isNull(){
        return userId == null || password == null || name == null;
    }

    @Override
    public String toString() {
        return "User [id=" + userId + ", password=" + password + ", name=" + name + "]";
    }

}
