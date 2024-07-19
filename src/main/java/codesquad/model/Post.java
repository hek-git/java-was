package codesquad.model;

public class Post {

        private User author;
        private String content;
        private String imagePath;

        public Post(User user, String content, String imagePath) {
            this.author = user;
            this.content = content;
            this.imagePath = imagePath;
        }

        public User getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public String getImagePath() {
            return imagePath;
        }

        @Override
        public String toString() {
            return "Post[author=" + author + ", content=" + content + "]";
        }
}
