package miu.edu.etitle.error;

public class PostNotFoundException extends RuntimeException {
    private int postId;

    public PostNotFoundException(int postId) {
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }
}
