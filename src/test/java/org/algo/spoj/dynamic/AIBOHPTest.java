package org.algo.spoj.dynamic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AIBOHPTest {

  @ParameterizedTest
  @MethodSource("provideScenarios")
  void noChangeNeeded(String input, int expected) {
    Instant start = Instant.now();
    int actual = AIBOHP.solve2(input);
    Instant finish = Instant.now();
    long timeElapsed = Duration.between(start, finish).toMillis();
    System.out.println("Time elapsed: " + timeElapsed);
    assertEquals(expected, actual);
  }

  @Test
  void testNormalisation (){
    AIBOHP.memo = new HashMap<>();
    var inputA = "abbc";
    var inputB = "nggt";
    var inputC = "xppr";

    AIBOHP.solve(inputA, 0, inputA.length()-1);
    AIBOHP.solve(inputB, 0, inputB.length()-1);
    AIBOHP.solve(inputC, 0, inputC.length()-1);

    assertEquals(3, AIBOHP.memo.entrySet().size());
  }

  private static Stream<Arguments> provideScenarios() throws IOException {
    return Stream.of(
//        Arguments.of("a", 0),
//        Arguments.of("aba", 0),
//        Arguments.of("aa", 0),
//        Arguments.of("aab", 1),
//        Arguments.of("ababbac", 2),
//        Arguments.of(
//            "ababbacbbacabbaababbabbaabacababbacbbacabbaababbabbaabacbcabvbababbccabbaccabbccbebaaaaababbabababababcabvbababbccabbaccabbccbebaaaaababbababababaababbacbbacabbaababbabbaabacbcabvbababbccabbaccabbccbebaaaaababbababababaababbacbbacabbaababbabbaabacbcabvbababbccabbaccabbccbebaaaaababbababababababababbabababaabababbabbababbabjhsdbababbabbabbssjhbbbababsjdasjjjasdjajjjabbakhskahskhadkahsdkjhaskdhakjdhaskjhdkashdkashdkashdkashdaskhda",
//            154),
        Arguments.of(
            Files.lines(Path.of("src/test/resources/spoj/AIBOHPT/6000.txt")).collect(Collectors.joining()),
            365)
    );
  }
}