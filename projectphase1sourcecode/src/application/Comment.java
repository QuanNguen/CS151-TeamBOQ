package application;

import java.util.List;
import java.util.Random;

public class Comment {
    private int commentId;
    private int userId;
    private String content;
    private long createdAt;
    private int upvotes;
    private int downvotes;
    private Random random = new Random();
    private String imageUrl;

    public Comment(int commentId, int userId, String content) {
        this.commentId = random.nextInt(1000);
        this.userId = userId;
        this.content = content;
        this.createdAt = System.currentTimeMillis();
        this.upvotes = 0;
        this.downvotes = 0;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public int getKarma() {
        return upvotes - downvotes;
    }

    public void deleteComment(List<Comment> commentList) {
        commentList.remove(this);
    }

    public void updateComment(String newContent) {
        this.content = newContent;
    }

    void upvote() {
        upvotes++;
    }

    void removeUpVote() {
        upvotes--;
    }

    void downvote() {
        downvotes++;
    }

    void removeDownVote() {
        downvotes--;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}