import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends Application {

    private List<UserHandler> userList = new ArrayList<>();
    private List<PostService> postList = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();

    private UserHandler loggedInUser;
    private TextArea displayTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Reddit Clone");

        displayTextArea = new TextArea();
        displayTextArea.setEditable(false);

        // Create buttons for authentication and actions

        Button loginButton = createActionButton("Login", this::loginUser, false);
        Button registerButton = createActionButton("Register", this::registerUser, false);
        Button logoutButton = createActionButton("Logout", this::logoutUser, true);
        Button createPostButton = createActionButton("Create Post", this::createPost, true);
        Button createCommentButton = createActionButton("Create Comment", this::createComment, true);
        Button removePostButton = createActionButton("Remove Post", this::removePost, true);
        Button updatePostButton = createActionButton("Update Post", this::updatePost, true);
        Button upvotePostButton = createActionButton("Upvote Post", this::upvotePost, true);
        Button downvotePostButton = createActionButton("Downvote Post", this::downvotePost, true);
        Button upvoteCommentButton = createActionButton("Upvote Comment", this::upvoteComment, true);
        Button downvoteCommentButton = createActionButton("Downvote Comment", this::downvoteComment, true);
        Button updateCommentButton = createActionButton("Update Comment", this::updateComment, true);
        Button deleteCommentButton = createActionButton("Delete Comment", this::deleteComment, true);

        // Create buttons for navigation
        Button homeButton = createNavigationButton("Home", this::showHomePage);
        Button commentsButton = createNavigationButton("Comments", this::showCommentsPage);
        Button usersButton = createNavigationButton("Users", this::showUsersPage);

        // Create layout for navigation
        HBox navigationBox = new HBox(10);
        navigationBox.setPadding(new Insets(10));
        navigationBox.getChildren().addAll(homeButton, commentsButton, usersButton);

        // Create layout for authentication and actions
        HBox actionBox = new HBox(10);
        actionBox.setPadding(new Insets(10));
        actionBox.getChildren().addAll(loginButton, registerButton, logoutButton, createPostButton,
                createCommentButton, removePostButton, updatePostButton, upvotePostButton, downvotePostButton,
                upvoteCommentButton, downvoteCommentButton, updateCommentButton, deleteCommentButton);


        // Create home page
        showHomePage();

        // Create layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(navigationBox, actionBox, displayTextArea);

        // Set up the scene
        Scene scene = new Scene(layout, 1400, 600);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    // Other methods
    private Button createNavigationButton(String buttonText, Runnable action) {
        Button button = new Button(buttonText);
        button.setOnAction(event -> action.run());
        return button;
    }

    private Button createActionButton(String buttonText, Runnable action, boolean requiresLogin) {
        Button button = new Button(buttonText);
        button.setOnAction(event -> {
            if (requiresLogin && loggedInUser == null) {
                showAlert(Alert.AlertType.INFORMATION, "Action not allowed", "Please log in to perform this action.");
            } else {
                action.run();
            }
        });
        return button;
    }

    private void loginUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Login");
        dialog.setHeaderText("Enter your username:");

        dialog.showAndWait().ifPresent(username -> {
            for (UserHandler user : userList) {
                if (user.getName().equals(username)) {
                    loggedInUser = user;
                    displayTextArea.appendText("Logged in as: " + loggedInUser.getName() + "\n");
                    return;
                }
            }
            showAlert(Alert.AlertType.ERROR, "Login failed", "User not found.");
        });
    }

    private void registerUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Register");
        dialog.setHeaderText("Enter your desired username:");

        dialog.showAndWait().ifPresent(username -> {
            UserHandler newUser = new UserHandler(userList.size() + 1, username);
            userList.add(newUser);
            loggedInUser = newUser;
            displayTextArea.appendText("Registered and logged in as: " + loggedInUser.getName() + "\n");
        });
    }

    private void logoutUser() {
        loggedInUser = null;
        displayTextArea.appendText("Logged out\n");
    }

    private void createPost() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Post");
        dialog.setHeaderText("Enter your post content:");

        dialog.showAndWait().ifPresent(content -> {
            PostService newPost = new PostService(postList.size() + 1, loggedInUser.getUserId(), content);
            loggedInUser.addPost(newPost);
            postList.add(newPost);
            displayTextArea.appendText("Post created by " + loggedInUser.getName() + ": " + newPost.getContent() + "\n");
        });
    }

    private void createComment() {
        if (postList.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Action failed", "No posts available to comment on.");
            return;
        }

        // Create a new user for each new comment
        UserHandler newUser = new UserHandler(userList.size() + 1, "User" + (userList.size() + 1));
        userList.add(newUser);
        loggedInUser = newUser;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Comment");
        dialog.setHeaderText("Enter your comment:");

        dialog.showAndWait().ifPresent(content -> {
            PostService latestPost = postList.get(postList.size() - 1);
            Comment newComment = new Comment(commentList.size() + 1, loggedInUser.getUserId(), content);
            loggedInUser.addComment(newComment);
            latestPost.addComment(newComment);
            commentList.add(newComment);
            displayTextArea.appendText("Comment on post by user " + newComment.getUserId() + ": " + newComment.getContent() + "\n");
        });
    }

    
    private void updateComment() {
        if (commentList.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Action failed", "No comments available to update.");
            return;
        }

        Comment randomComment = commentList.get(commentList.size() - 1);
        TextInputDialog dialog = new TextInputDialog(randomComment.getContent());
        dialog.setTitle("Update Comment");
        dialog.setHeaderText("Enter the updated comment content:");

        dialog.showAndWait().ifPresent(newContent -> {
            randomComment.updateComment(newContent);
            displayTextArea.appendText("Comment updated: " + randomComment.getContent() + "\n");
        });
    }

    private void deleteComment() {
        if (commentList.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Action failed", "No comments available to delete.");
            return;
        }

        Comment randomComment = commentList.remove(commentList.size() - 1);
        displayTextArea.appendText("Comment deleted: " + randomComment.getContent() + "\n");
    }

    private void removePost() {
        if (postList.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Action failed", "No posts available to remove.");
            return;
        }

        // Get the last post created in the system
        PostService removedPost = postList.get(postList.size() - 1);

        // Remove all comments associated with the removed post
        for (Comment comment : removedPost.getComments()) {
            commentList.remove(comment);
        }

        postList.remove(removedPost);
        // Display a simple message indicating post removal
        displayTextArea.appendText("Post removed.\n");
    }

    private void updatePost() {
        if (postList.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Action failed", "No posts available to update.");
            return;
        }

        // Get the last post created in the system
        PostService randomPost = postList.get(postList.size() - 1);

        TextInputDialog dialog = new TextInputDialog(randomPost.getContent());
        dialog.setTitle("Update Post");
        dialog.setHeaderText("Enter the updated post content:");

        dialog.showAndWait().ifPresent(newContent -> {
            randomPost.updatePost(newContent);
            // Display a simple message indicating post update
            displayTextArea.appendText("Post updated.\n");
        });
    }

    private void upvotePost() {
        if (!postList.isEmpty()) {
            PostService latestPost = postList.get(postList.size() - 1);
            latestPost.upvote();
            displayTextArea.appendText("Upvoted post: " + latestPost.getContent() +
                    " (Karma: " + latestPost.getKarma() + ")\n");
        } else {
            showAlert(Alert.AlertType.ERROR, "Action failed", "No posts available to upvote.");
        }
    }

    private void downvotePost() {
        if (!postList.isEmpty()) {
            PostService latestPost = postList.get(postList.size() - 1);
            latestPost.downvote();
            displayTextArea.appendText("Downvoted post: " + latestPost.getContent() +
                    " (Karma: " + latestPost.getKarma() + ")\n");
        } else {
            showAlert(Alert.AlertType.ERROR, "Action failed", "No posts available to downvote.");
        }
    }

    private void upvoteComment() {
        if (!commentList.isEmpty()) {
            Comment latestComment = commentList.get(commentList.size() - 1);
            latestComment.upvote();
            displayTextArea.appendText("Upvoted comment: " + latestComment.getContent() +
                    " (Karma: " + latestComment.getKarma() + ")\n");
        } else {
            showAlert(Alert.AlertType.ERROR, "Action failed", "No comments available to upvote.");
        }
    }

    private void downvoteComment() {
        if (!commentList.isEmpty()) {
            Comment latestComment = commentList.get(commentList.size() - 1);
            latestComment.downvote();
            displayTextArea.appendText("Downvoted comment: " + latestComment.getContent() +
                    " (Karma: " + latestComment.getKarma() + ")\n");
        } else {
            showAlert(Alert.AlertType.ERROR, "Action failed", "No comments available to downvote.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showHomePage() {
        displayTextArea.appendText("Home Page - Posts\n");
        displayPosts(postList);
    }

    private void showCommentsPage() {
        displayTextArea.appendText("Comments Page\n");
        displayComments(commentList);
    }

    private void showUsersPage() {
        displayTextArea.appendText("Users Page\n");
        displayUsers(userList);
    }

    private void displayPosts(List<PostService> posts) {
        Collections.sort(posts, (p1, p2) -> Integer.compare(p2.getKarma(), p1.getKarma()));
        for (PostService post : posts) {
            displayTextArea.appendText(post.getContent() + " (Karma: " + post.getKarma() + ", Upvotes: " +
                    post.getUpvotes() + ", Downvotes: " + post.getDownvotes() + ")\n");
        }
    }

    private void displayComments(List<Comment> comments) {
        Collections.sort(comments, (c1, c2) -> Integer.compare(c2.getKarma(), c1.getKarma()));
        for (Comment comment : comments) {
            displayTextArea.appendText(comment.getContent() + " (Karma: " + comment.getKarma() + ", Upvotes: " +
                    comment.getUpvotes() + ", Downvotes: " + comment.getDownvotes() + ")\n");
        }
    }

    private void displayUsers(List<UserHandler> users) {
        Collections.sort(users, (u1, u2) -> Integer.compare(u2.getTotalKarma(), u1.getTotalKarma()));
        for (UserHandler user : users) {
            displayTextArea.appendText(user.getName() + " (Karma: " + user.getTotalKarma() +
                    ", Upvotes: " + user.getTotalUpvotes() + ", Downvotes: " + user.getTotalDownvotes() + ")\n");
        }
    }
}