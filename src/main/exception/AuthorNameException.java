package exception;

public class AuthorNameException {
    private String authorName;

    // EFFECTS: constructs checker for given answer
    public AuthorNameException(String authorName) {
        this.authorName = authorName;
    }

    public boolean checkName(String userResponse) {
        try {
            return authorName.equalsIgnoreCase(userResponse);
        } catch (NumberFormatException e) {
            System.out.println("Wrong answer format");
            return false;
        }
    }
}
