package org.algo.spoj.dynamic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AIBOHPTest {

  @ParameterizedTest
  @MethodSource("provideScenarios")
  void noChangeNeeded(String input, int expected){
    StringBuilder sb = new StringBuilder(input);
    AIBOHP.minLength = input.length()*2;
    AIBOHP.solve(sb, 0);
    int actual = AIBOHP.minLength - input.length();

    assertEquals(expected, actual);
  }

  private static Stream<Arguments> provideScenarios(){
    return Stream.of(
        Arguments.of("a", 0),
        Arguments.of("aba", 0),
        Arguments.of("aa", 0),
        Arguments.of("aab", 1),
        Arguments.of("ababbac", 2),
        Arguments.of("ababbacbbacabbaababbabbaabacbcabvbababbccabbaccabbccbebaaaaababbababababababababbabababaabababbabbababbabjhsdbababbabbabbssjhbbbababsjdasjjjasdjajjjabbakhskahskhadkahsdkjhaskdhakjdhaskjhdkashdkashdkashdkashdaskhda", 18)
    );
  }
}