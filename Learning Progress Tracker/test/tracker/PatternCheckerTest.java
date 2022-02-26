package tracker;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatternCheckerTest {

    @ParameterizedTest
    @ValueSource(strings = {"Anna-Maria", "Adam", "O'Neil"})
    void checkValidFirstName(String name) {
        assertTrue(PatternChecker.checkFirstName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"'A", "A-", "O'-Neil", "'Ala-"})
    void checkInvalidFirstName(String name) {
        assertFalse(PatternChecker.checkFirstName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jemison Van de Graaff", "Ronald Reuel Tolkien", "Adam-Brand", "O'Neil-Adams"})
    void checkValidLastName(String name) {
        assertTrue(PatternChecker.checkLastName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Anna Mar'-ia", "'Jemi'son Van de Graaff-", "123-"})
    void checkInvalidLastName(String name) {
        assertFalse(PatternChecker.checkLastName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"name@mail.com", "mail-adam@mail.pl", "mail123@mail.net"})
    void checkValidEmail(String email) {
        assertTrue(PatternChecker.checkEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"name@mail", "name @mail.com", "name%^&@#@mail.pl"})
    void checkInvalidEmail(String email) {
        assertFalse(PatternChecker.checkEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"234564 1 123 9 0", "10000 1 1 1 1", "1 1 1 1 1"})
    void checkValidPointsInput(String points) {
        assertTrue(PatternChecker.checkPointsInput(points));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1 1 1 1", "1 ? 1 1 1", "250938234"})
    void checkInvalidPointsInput(String points) {
        assertFalse(PatternChecker.checkPointsInput(points));
    }

    @ParameterizedTest
    @ValueSource(strings = {"152889", "1", "0"})
    void checkValidId(String id) {
        assertTrue(PatternChecker.checkId(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "asffgasfas", "j-"})
    void checkInvalidId(String id) {
        assertFalse(PatternChecker.checkId(id));
    }
}
