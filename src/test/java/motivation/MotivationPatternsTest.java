package motivation;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Motivation patterns — small JUnit 5 practice")
@Tag("meta")
class MotivationPatternsTest {

    @DisplayName("ValueSource → print messages")
    @ParameterizedTest(name = "[{index}] {0}")
    @ValueSource(strings = {
            "Steady focus, clean code.",
            "Small steps compound into mastery.",
            "Ship small, ship often."
    })
    void valueSource_prints(String msg) {
        System.out.println(msg);
        assertTrue(true);
    }

    @DisplayName("CsvSource → validate basic message shape")
    @ParameterizedTest(name = "[{index}] \"{0}\" (minLen={1})")
    @CsvSource({
            "'Consistency turns challenges into growth.', 20",
            "'Every run is progress; every commit builds leverage.', 25",
            "'Clarity first, then craft.', 10"
    })
    void csvSource_validate(String msg, int minLen) {
        Executable notBlank = () -> assertFalse(msg.trim().isEmpty(), "message should not be blank");
        Executable longEnough = () -> assertTrue(msg.length() >= minLen, "message meets length");
        Executable hasSpaces = () -> assertTrue(msg.contains(" "), "message contains spaces");
        assertAll("message shape", notBlank, longEnough, hasSpaces);
    }

    @DisplayName("Focus primer (repeat 2 quick cycles)")
    @RepeatedTest(value = 2, name = "Cycle {currentRepetition}/{totalRepetitions}")
    void focusPrimer() {
        System.out.println("Prime → Lock-in → Slide");
        assertTrue(true);
    }

    @DisplayName("Dynamic tests → each phrase prints once")
    @TestFactory
    Stream<DynamicTest> dynamic_messages() {
        List<String> items = Arrays.asList(
                "Clarity → Action → Momentum.",
                "One clean commit beats ten half-starts.",
                "Friction down, consistency up."
        );
        return items.stream()
                .map(text -> DynamicTest.dynamicTest(text, () -> {
                    System.out.println(text);
                    assertTrue(true);
                }));
    }
}
