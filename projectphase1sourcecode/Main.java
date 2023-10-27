package skfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<UserHandler> userList = new ArrayList<>();
        List<PostService> postList = new ArrayList<>();
        List<Comment> commentList = new ArrayList<>();

        UserHandler user1 = new UserHandler(1, "User1");
        UserHandler user2 = new UserHandler(2, "User2");
        userList.add(user1);
        userList.add(user2);

        // Predefined Posts
        PostService post1 = new PostService(1, user1.getUserId(), "Post by User1");
        PostService post2 = new PostService(2, user2.getUserId(), "Post by User2");
        postList.add(post1);
        postList.add(post2);

        // Predefined Comments
        Comment comment1 = new Comment(1, user1.getUserId(), "Comment on Post1");
        Comment comment2 = new Comment(2, user2.getUserId(), "Comment on Post2");
        Comment comment3 = new Comment(3, user1.getUserId(), "Reply to Comment1");
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
