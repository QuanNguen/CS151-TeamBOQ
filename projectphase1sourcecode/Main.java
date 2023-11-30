import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main extends Application {

    private List<UserHandler> userList = new ArrayList<>();
    private List<PostService> postList = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();

    private UserHandler loggedInUser;
    private TextArea displayTextArea;
    private ImageView profilePictureView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Reddit Clone");

        displayTextArea = new TextArea();
        displayTextArea.setEditable(false);

        profilePictureView = new ImageView();
        profilePictureView.setFitHeight(100);
        profilePictureView.setFitWidth(100);

        // Create buttons for authentication and actions

        Button loginButton = createActionButton("Login", this::loginUser, false);
        Button registerButton = createActionButton("Register", this::registerUser, false);
        Button logoutButton = createActionButton("Logout", this::logoutUser, true);
        Button createPostButton = createActionButton("Create Post", this::createPost, true);
        Button createCommentButton = createActionButton("Create Comment", this::createComment, true);
        Button removePostButton = createActionButton("Remove Post", this::removePost, true);
        Button updatePostButton = createActionButton("Edit Post", this::updatePost, true);
        Button upvotePostButton = createActionButton("Upvote Post", this::upvotePost, true);
        Button downvotePostButton = createActionButton("Downvote Post", this::downvotePost, true);
        Button upvoteCommentButton = createActionButton("Upvote Comment", this::upvoteComment, true);
        Button downvoteCommentButton = createActionButton("Downvote Comment", this::downvoteComment, true);
        Button updateCommentButton = createActionButton("Edit Comment", this::updateComment, true);
        Button deleteCommentButton = createActionButton("Delete Comment", this::deleteComment, true);

        Button selectProfilePicture = createActionButton("Select Profile Picture", this::selectProfilePicture, true);
        selectProfilePicture.setOnAction(event -> selectProfilePicture());

        // Create buttons for navigation
        Button homeButton = createNavigationButton("Home", this::showHomePage);
        Button commentsButton = createNavigationButton("Comments", this::showCommentsPage);
        Button usersButton = createNavigationButton("Users", this::showUsersPage);

        // Create layout for navigation
        HBox navigationBox = new HBox(10);
        navigationBox.setPadding(new Insets(10));
        navigationBox.getChildren().addAll(homeButton, commentsButton, usersButton);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        // Add buttons to the grid
        gridPane.add(profilePictureView, 0, 0, 1, 2);
        gridPane.add(selectProfilePicture, 1, 0);
        gridPane.add(loginButton, 1, 1);
        gridPane.add(registerButton, 2, 0);
        gridPane.add(logoutButton, 2, 1);
        gridPane.add(createPostButton, 3, 0);
        gridPane.add(createCommentButton, 3, 1);
        gridPane.add(removePostButton, 4, 0);
        gridPane.add(updatePostButton, 4, 1);
        gridPane.add(upvotePostButton, 5, 0);
        gridPane.add(downvotePostButton, 5, 1);
        gridPane.add(upvoteCommentButton, 6, 0);
        gridPane.add(downvoteCommentButton, 6, 1);
        gridPane.add(updateCommentButton, 7, 0);
        gridPane.add(deleteCommentButton, 7, 1);

        HBox actionBox = new HBox(10);
        actionBox.getChildren().addAll(gridPane);

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

    private void selectProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp")
        );

        // Check if the user is logged in before allowing them to set a profile picture
        if (loggedInUser != null) {
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                // Update the user's profile picture
                loggedInUser.setProfilePicture(selectedFile.getAbsolutePath());
                Image selectedImage = new Image(selectedFile.toURI().toString());
                profilePictureView.setImage(selectedImage);
                displayTextArea.appendText("Profile picture set for " + loggedInUser.getName() + "\n");
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Action not allowed", "Please log in to set a profile picture.");
        }
    }


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

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image or GIF");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp")
        );

        // Create a button to trigger file selection
        Button selectImageButton = new Button("Select Image or GIF");
        selectImageButton.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                dialog.getEditor().setText(selectedFile.getAbsolutePath());
            }
        });

        // Add the button to the dialog pane
        dialog.getDialogPane().setExpandableContent(new HBox(selectImageButton));

        dialog.showAndWait().ifPresent(content -> {
            PostService latestPost = postList.get(postList.size() - 1);

            // Check if the comment content is a valid file path
            File file = new File(content);
            if (file.exists()) {
                // Content is a file path, treat it as an image or GIF
                Comment newComment = new Comment(commentList.size() + 1, loggedInUser.getUserId(), content);
                loggedInUser.addComment(newComment);
                latestPost.addComment(newComment);
                commentList.add(newComment);
                displayTextArea.appendText("Image/GIF Comment on post by user " + newComment.getUserId() + ": " + newComment.getContent() + "\n");
            } else {
                // Content is not a valid file path, treat it as regular text
                Comment newComment = new Comment(commentList.size() + 1, loggedInUser.getUserId(), content);
                loggedInUser.addComment(newComment);
                latestPost.addComment(newComment);
                commentList.add(newComment);
                displayTextArea.appendText("Text Comment on post by user " + newComment.getUserId() + ": " + newComment.getContent() + "\n");
            }
        });
    }
    
    private void updateComment() {
        if (commentList.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Action failed", "No comments available to update.");
            return;
        }

        Comment randomComment = commentList.get(commentList.size() - 1);
        TextInputDialog dialog = new TextInputDialog(randomComment.getContent());
        dialog.setTitle("Edit Comment");
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
        dialog.setTitle("Edit Post");
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
        Collections.sort(comments, Comparator.comparingInt(Comment::getKarma).reversed());

        for (Comment comment : comments) {
            if (comment.getImageUrl() != null && !comment.getImageUrl().isEmpty()) {
                try {
                    // Convert image/gif URL to Image
                    Image image = new Image(comment.getImageUrl());

                    // Display image/gif comments
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);

                    displayTextArea.appendText(comment.getContent() + " (Karma: " + comment.getKarma() +
                            ", Upvotes: " + comment.getUpvotes() + ", Downvotes: " + comment.getDownvotes() + ")\n");
                    
                    displayTextArea.appendText("Image/GIF Comment:\n");
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    displayTextArea.appendText("Error loading image: " + comment.getImageUrl() + "\n");
                }
            } else {
                // Display regular text comments
                displayTextArea.appendText(comment.getContent() + " (Karma: " + comment.getKarma() +
                        ", Upvotes: " + comment.getUpvotes() + ", Downvotes: " + comment.getDownvotes() + ")\n");
            }
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
