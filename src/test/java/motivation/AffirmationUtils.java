package motivation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Small helper methods to work with positive affirmations.
 */

public class AffirmationUtils {

    // A simple in-memory List of affirmations
    private static final List<String> AFFIRMATIONS = Collections.unmodifiableList(
            Arrays.asList(
                    "Jonas, every breath you take is clean and yours.",
                    "You are proving you can sit with discomfort and still win.",
                    "Each minute you resist, you’re rewiring your brain toward freedom.",
                    "You’ve already thrown it away once — that was the real you speaking.",
                    "You don’t need to inhale chemicals to exhale confidence."
            )
    );

    /** Returns all affirmations. */
    public static List<String> getAll() {
        return new ArrayList<>(AFFIRMATIONS);
    }

    /** Returns all affirmations containing the given keyword (case-insensitive). */
    public static List<String> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList(); // ✅ Works in Java 8 and later
        }
        String lower = keyword.toLowerCase();
        return AFFIRMATIONS.stream()
                .filter(a -> a.toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }


    /** Returns a random affirmation from the list. */
    public static String randomAffirmation() {
        int index = (int) (Math.random() * AFFIRMATIONS.size());
        return AFFIRMATIONS.get(index);
    }

    /** Returns true if the affirmation text is encouraging (contains “you” or “your”). */
    public static boolean isEncouraging(String text) {
        if (text == null) return false;
        String lower = text.toLowerCase();
        return lower.contains("you") || lower.contains("your");
    }
}
