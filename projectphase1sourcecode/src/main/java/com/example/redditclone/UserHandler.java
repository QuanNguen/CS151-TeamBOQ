package com.example.redditclone;

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
    private String profilePicture;

    public UserHandler(int userId, String name) {
        this.userId = random.nextInt(1000);
        this.name = name;
        this.createdAt = System.currentTimeMillis();
        this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
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

    public List<PostService> getPosts() {
        return posts;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getTotalKarma() {
        int postKarma = getPostKarma();
        int commentKarma = getCommentKarma();
        return postKarma + commentKarma;
    }

    public int getTotalUpvotes() {
        int postUpvotes = getPostUpvotes();
        int commentUpvotes = getCommentUpvotes();
        return postUpvotes + commentUpvotes;
    }

    public int getTotalDownvotes() {
        int postDownvotes = getPostDownvotes();
        int commentDownvotes = getCommentDownvotes();
        return postDownvotes + commentDownvotes;
    }

    private int getPostKarma() {
        int postKarma = 0;
        for (PostService post : posts) {
            postKarma += post.getKarma();
        }
        return postKarma;
    }

    private int getPostUpvotes() {
        int postUpvotes = 0;
        for (PostService post : posts) {
            postUpvotes += post.getUpvotes();
        }
        return postUpvotes;
    }

    private int getPostDownvotes() {
        int postDownvotes = 0;
        for (PostService post : posts) {
            postDownvotes += post.getDownvotes();
        }
        return postDownvotes;
    }

    private int getCommentKarma() {
        int commentKarma = 0;
        for (PostService post : posts) {
            for (Comment comment : post.getComments()) {
                commentKarma += comment.getKarma();
            }
        }
        return commentKarma;
    }

    private int getCommentUpvotes() {
        int commentUpvotes = 0;
        for (PostService post : posts) {
            for (Comment comment : post.getComments()) {
                commentUpvotes += comment.getUpvotes();
            }
        }
        return commentUpvotes;
    }

    private int getCommentDownvotes() {
        int commentDownvotes = 0;
        for (PostService post : posts) {
            for (Comment comment : post.getComments()) {
                commentDownvotes += comment.getDownvotes();
            }
        }
        return commentDownvotes;
    }
}

