import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main {
    
    class UserHandler {
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

        public int getUserId() {
            return userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // Method to delete a user
        public void deleteUser(List<UserHandler> userList) {
            userList.remove(this);
        }

        // Method to update user information
        public void updateUser(String newName) {
            this.name = newName;
        }

        public long getCreatedAt() {
            return createdAt;
        }
    }

    class PostService {
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

        // Method to delete a post
        public void deletePost(List<PostService> postList) {
            postList.remove(this);
        }

        // Method to update post content
        public void updatePost(String newContent) {
            this.content = newContent;
        }

        // Method to add a comment to the post
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

    class Comment {
        private int commentId;
        private int userId;
        private String content;
        private long createdAt;
        private int upvotes;
        private int downvotes;

        private Random random = new Random();

        public Comment(int commentId, int userId, String content) {
            this.commentId = random.nextInt(1000);
            this.userId = userId;
            this.content = content;
            this.createdAt = System.currentTimeMillis();
            this.upvotes = 0;
            this.downvotes = 0;
        }

        // Getters and setters

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

        // Method to delete a comment
        public void deleteComment(List<Comment> commentList) {
            commentList.remove(this);
        }

        // Method to update comment content
        public void updateComment(String newContent) {
            this.content = newContent;
        }
        
        public void upvote() {
            upvotes++;
        }

        public void downvote() {
            downvotes++;
        }
    }
    
    public static void main(String[] args) {

        List<UserHandler> userList = new ArrayList<>();
        List<PostService> postList = new ArrayList<>();
        List<Comment> commentList = new ArrayList<>();


        UserHandler user1 = new Main().new UserHandler(1, "User1");
        UserHandler user2 = new Main().new UserHandler(2, "User2");
        userList.add(user1);
        userList.add(user2);

        // Predefined Posts
        PostService post1 = new Main().new PostService(1, user1.getUserId(), "Post by User1");
        PostService post2 = new Main().new PostService(2, user2.getUserId(), "Post by User2");
        postList.add(post1);
        postList.add(post2);

        // Predefined Comments
        Comment comment1 = new Main().new Comment(1, user1.getUserId(), "Comment on Post1");
        Comment comment2 = new Main().new Comment(2, user2.getUserId(), "Comment on Post2");
        Comment comment3 = new Main().new Comment(3, user1.getUserId(), "Reply to Comment1");
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);

        // Sort the lists by the time created
        Collections.sort(userList, Comparator.comparingLong(UserHandler::getCreatedAt));
        Collections.sort(postList, Comparator.comparingLong(PostService::getCreatedAt));
        Collections.sort(commentList, Comparator.comparingLong(Comment::getCreatedAt));

        // Display the sorted data
        System.out.println("Sorted Users:");
        for (UserHandler user : userList) {
            System.out.println("User ID: " + user.getUserId() + ", User Name: " + user.getName());
        }

        System.out.println("\nSorted Posts:");
        for (PostService post : postList) {
            System.out.println("Post ID: " + post.getPostId() + ", Post Content: " + post.getContent());
        }

        System.out.println("\nSorted Comments:");
        for (Comment comment : commentList) {
            System.out.println("Comment ID: " + comment.getCommentId() + ", Comment Content: " + comment.getContent());
        }
    }
}
