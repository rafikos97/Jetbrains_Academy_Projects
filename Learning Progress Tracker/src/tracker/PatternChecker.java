package tracker;

public class PatternChecker {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final String NAME_PATTERN = "[a-zA-Z]+(?:['-]?[a-zA-Z]+)+";
    private static final String SECOND_NAME_PATTERN = "(?:[a-zA-Z]+(?:['-]?[a-zA-Z]+)+\\s*)+";
    private static final String ADD_POINT_PATTERN = "\\w+(\\s\\d+){4}";
    private static final String ID_PATTERN = "[\\d\\s]+";

    protected static boolean checkPointsInput(String pointsInput) {
        return pointsInput.matches(ADD_POINT_PATTERN);
    }

    protected static boolean checkId(String id) {
        return id.matches(ID_PATTERN);
    }

    protected static boolean checkFirstName(String firstName) {
        return firstName.matches(NAME_PATTERN);
    }

    protected static boolean checkLastName(String lastName) {
        return lastName.matches(SECOND_NAME_PATTERN);
    }

    protected static boolean checkEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }
}

