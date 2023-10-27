import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class PostService {
    private int postId;
    private int userId;
    private String content;
    private long createdAt;
    private List<Comment> comments;
    private int upvotes;
    private int downvotes;

    private Random random = new Random();

    public PostService(int postId, int userId, String content) {
        this.postId = random.nextInt(1000);
        this.userId = userId;
        this.content = content;
        this.createdAt = System.currentTimeMillis();
        this.comments = new ArrayList<>();
        this.upvotes = 0;
        this.downvotes = 0;
    }

    // Getters and setters
    // Methods for deletePost, updatePost, addComment, and other PostService specific functionality

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void deletePost(List<PostService> postList) {
        postList.remove(this);
    }

    public void updatePost(String newContent) {
        this.content = newContent;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void upvote() {
        upvotes++;
    }

    public void downvote() {
        downvotes++;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
