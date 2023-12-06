package application;



public class VotingService {
    Boolean votedPost = null;
    Boolean votedComment = null;

    public void upVote(PostService post) {
        if (votedPost == null) {
            post.upvote();
            votedPost = true;
        } else if (votedPost == false) {
            post.upvote();
            post.removeDownVote();
            votedPost = true;
        } else {
            votedPost = null;
            post.removeUpVote();
        }
    }

    public void upVote(Comment comment) {
        if (votedComment == null) {
            comment.upvote();
            votedComment = true;
        } else if (votedComment == false) {
            comment.upvote();
            comment.removeDownVote();
            votedComment = true;
        } else {
            votedComment = null;
            comment.removeUpVote();
        }
    }

    public void downVote(PostService post) {
        if (votedPost == null) {
            post.downvote();
            votedPost = false;
        } else if (votedPost == true) {
            post.downvote();
            post.removeUpVote(); 
            votedPost = true;
        } else {
            votedPost = null;
            post.removeDownVote();
        }
    }

    public void downVote(Comment comment) {
        if (votedComment == null) {
            comment.downvote();
            votedComment = false;
        } else if (votedComment == true) {
            comment.downvote();
            comment.removeUpVote(); 
            votedComment = true;
        } else {
            votedComment = null;
            comment.removeDownVote();
        }
    }
}