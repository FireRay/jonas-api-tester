package motivation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AffirmationUtils — behavior tests")
@Tag("unit")
class AffirmationUtilsTest {

    // --- getAll --------------------------------------------------------------

    @Test
    @DisplayName("getAll: returns a non-empty, stable list (size >= 5)")
    void getAll_returnsStableList() {
        List<String> all = AffirmationUtils.getAll();
        assertNotNull(all);
        assertTrue(all.size() >= 5, "Expected at least 5 affirmations");
        // sanity: elements look like sentences
        assertTrue(all.stream().allMatch(s -> s.endsWith(".") || s.endsWith("!")));
    }

    @DisplayName("getAll: each affirmation starts with a capital letter")
    @Test
    void getAll_affirmationsStartWithCapital(){
        List<String> all = AffirmationUtils.getAll();
        assertNotNull(all);
        assertFalse(all.isEmpty(), "Expected affirmations to be present");

        assertTrue(
                all.stream().allMatch(AffirmationUtilsTest::startsWithCapital),
                "All affirmations should start with an uppercase letter (A-Z)"
        );
    }

    // --- searchByKeyword -----------------------------------------------------

    @ParameterizedTest(name = "[{index}] keyword='{0}' finds at least one")
    @DisplayName("searchByKeyword: case-insensitive match")
    @ValueSource(strings = {"you", "YOU", "confidence"})
    void searchByKeyword_caseInsensitive(String keyword) {
        List<String> hits = AffirmationUtils.searchByKeyword(keyword);
        assertNotNull(hits);
        assertFalse(hits.isEmpty(), "Expected at least one match for: " + keyword);
        // each hit must contain the keyword ignoring case
        String low = keyword.toLowerCase();
        assertTrue(hits.stream().allMatch(a -> a.toLowerCase().contains(low)));
    }

    @ParameterizedTest(name = "[{index}] emptyish keyword → empty list")
    @DisplayName("searchByKeyword: empty/null → empty result")
    @ValueSource(strings = {"", " ", "   "})
    void searchByKeyword_emptyGivesEmpty(String keyword) {
        assertTrue(AffirmationUtils.searchByKeyword(keyword).isEmpty());
    }

    @Test
    @DisplayName("searchByKeyword: null → empty list")
    void searchByKeyword_nullGivesEmpty() {
        assertTrue(AffirmationUtils.searchByKeyword(null).isEmpty());
    }

    // --- isEncouraging -------------------------------------------------------

    @ParameterizedTest(name = "[{index}] \"{0}\" → encouraging={1}")
    @DisplayName("isEncouraging: detects 'you'/'your' (case-insensitive)")
    @CsvSource({
            "'You are capable.', true",
            "'your momentum is real', true",
            "'This sentence has no keyword', false",
            "'', false"
    })
    void isEncouraging_detectsKeywords(String text, boolean expected) {
        assertEquals(expected, AffirmationUtils.isEncouraging(text));
    }

    @Test
    @DisplayName("isEncouraging: null → false")
    void isEncouraging_null() {
        assertFalse(AffirmationUtils.isEncouraging(null));
    }

    // --- randomAffirmation ---------------------------------------------------

    @Test
    @DisplayName("randomAffirmation: always returns one from getAll()")
    void randomAffirmation_isFromList() {
        List<String> all = AffirmationUtils.getAll();
        String pick = AffirmationUtils.randomAffirmation();
        assertTrue(all.contains(pick), "Random pick must be from the known list");
    }

    @DisplayName("getAll: each affirmation ends with '.' or '!'")
    @Test
    void getAll_endWithPunctuation() {
        List<String> all = AffirmationUtils.getAll();
        assertTrue(
                all.stream().allMatch(s -> {
                    String t = s.trim();
                    return t.endsWith(".") || t.endsWith("!");
                }),
                "Every affirmation should end with '.' or '!'"
        );
    }



    private static boolean startsWithCapital(String s) {
        if (s == null) return false;
        String t = s.trim();
        if (t.isEmpty()) return false;

        // Skip any leading non-letters (just in case, e.g., quotes)
        int i = 0;
        while (i < t.length() && !Character.isLetter(t.charAt(i))) i++;
        if (i == t.length()) return false;

        return Character.isUpperCase(t.charAt(i));
    }


}
