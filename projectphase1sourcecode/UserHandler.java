import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class UserHandler {
    private int userId;
    private String name;
    private long createdAt;
    private Random random = new Random();
    private List<PostService> posts;
    private List<Comment> comments;
    private int karma;

    public UserHandler(int userId, String name) {
        this.userId = random.nextInt(1000);
        this.name = name;
        this.createdAt = System.currentTimeMillis();
        this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.karma = 0;
    }

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
    
    public void addPost(PostService post) {
        posts.add(post);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public int getKarma() {
        return karma;
    }

    public void calculateKarma() {
        karma = 0;
        for (PostService post : posts) {
            karma += post.getKarma();
        }
        for (Comment comment : comments) {
            karma += comment.getKarma();
        }
    }
}
