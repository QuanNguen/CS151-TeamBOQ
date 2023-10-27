import java.util.Random;
import java.util.List;

public class UserHandler {
    private int userId;
    private String name;
    private long createdAt;
    private Random random = new Random();

    public UserHandler(int userId, String name) {
        this.userId = random.nextInt(1000);
        this.name = name;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters and setters
    // Methods for deleteUser, updateUser, and other UserHandler specific functionality

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void deleteUser(List<UserHandler> userList) {
        userList.remove(this);
    }

    public void updateUser(String newName) {
        this.name = newName;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
