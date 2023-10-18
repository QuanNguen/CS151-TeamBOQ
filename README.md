# CS151-BOQCorporation
In this project, we will create a social media system Reddit 
There are 3 phases in this project: basic implemetation, adding complexity, and adding a "Frontend" + " Stretch" goals
This website should have users, posts, and comments 
Our team have 3 members:
Quan Nguyen
Benny Ngo 
Oliver Zeyen


### Project Structure

The project is structured as follows:

Main.java: The main class that drives the code and contains the main method. It's responsible for creating users, posts, comments, and sorting them by creation time.

UserHandler: A class representing users. It includes methods for creating, updating, and deleting users.

PostService: A class representing posts. It includes methods for creating, updating, and deleting posts, as well as adding comments to posts.

Comment: A class representing comments. It includes methods for creating, updating, and deleting comments.

### Class Responsibilities

**Main.java: The primary driver of the code, responsible for the following:**

Creating random users.

Creating random posts by users.

Creating random comments on posts.

Sorting and displaying users, posts, and comments based on creation time.

**UserHandler.java: Represents user entities and includes the following methods:**

createUser(String name): Creates a new user with a random user ID.

updateUser(String newName): Updates the user's name.

deleteUser(List<User> userList): Deletes the user from the list.

**PostService.java: Represents post entities and includes the following methods:**

createPost(int userId, String content): Creates a new post with a random post ID.

updatePost(String newContent): Updates the post content.

deletePost(List<PostService> postList): Deletes the post from the list.

addComment(Comment comment): Adds a comment to the post.

**Comment.java: Represents comment entities and includes the following methods:**

createComment(int userId, String content): Creates a new comment with a random comment ID.

updateComment(String newContent): Updates the comment content.

deleteComment(List<Comment> commentList): Deletes the comment from the list.

### Basic Interactions

Creating Users:

In the Main class, you can create random users using the User class. Users are created with random user IDs.
Creating Posts:

Users create posts by calling the createPost method from the Post class. Posts have random post IDs.

Creating Comments:

Users create comments on posts by calling the createComment method from the Comment class. Comments have random comment IDs.

Updating and Deleting:

Users, posts, and comments can be updated or deleted using methods provided in their respective classes.

Sorting and Displaying:

The Main class sorts users, posts, and comments by their creation time and displays them.
