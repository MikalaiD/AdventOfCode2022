package org.algo.spoj.dynamic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ACODETest {

  @Test
  void traverse2() {
    //given
    var list = List.of(2,1);

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(2, output);
  }
  @Test
  void traverse2withOnly1Option() {
    //given
    var list = List.of(5,1);

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(1, output);
  }

  @Test
  void traverse3() {
    //given
    var list = List.of(2,1,2);

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(3, output);
  }

  @Test
  void traverse4() {
    //given
    var list = List.of(2,1,2,1);

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(5, output);
  }

  @Test
  void traverse5with2chunks() {
    //given
    var list = List.of(2,5,1,1,4);

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(6, output);
  }

  @Test
  void traverse10of1() {
    //given
    var list = List.of(1,1,1,1,1,1,1,1,1,1);

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(89, output);
  }

  @Test
  void traverse10of3() {
    //given
    var list = List.of(3,3,3,3,3,3,3,3,3,3);

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(1, output);
  }

  @Test
  void traverse3of10() {
    //given
    var list = List.of(1,0,1,0,1,0);

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(1, output);
  }

  @Test
  void traverseEdgeCase1() {
    //given
    var list = List.of(3,1,2,0,1,0,1);

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(1, output);
  }

  @Test
  void traverseEdgeCase2() {
    //given
    var list = Arrays.stream("181292321118743131219236834956616923925".split("")).map(Integer::parseInt).toList();

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(10240, output);
  }

  @Test
  void traverseEdgeCase3() {
    //given
    var list = Arrays.stream("61247812412412123124579127212412435689".split("")).map(Integer::parseInt).toList();

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(19440, output);
  }

  @Test
  void traverseEdgeCase4() {
    //given
    var list = Arrays.stream("91253942735679263798126356239175735123123621561235621891231231289329845123895893912943129491253945129543951293459125912534912359145912123591256".split("")).map(Integer::parseInt).toList();

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(58773123072L, output);
  }

  @Test
  void traverseEdgeCase5() {
    //given
    var list = Arrays.stream("9125394273567124124121241266251212047128436215612356218912312312893298451238958939129431294912539451295439512934591259125349123591459121235912561451111111111".split("")).map(Integer::parseInt).toList();

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(125539390881792L, output);
  }

  @Test
  void traverseEdgeCase6() {
    //given
    var list = Arrays.stream("179823569715691278356983612927356172537861237863561359612634513257896128935618263".split("")).map(Integer::parseInt).toList();

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(73728, output);
  }

  @Test
  void traverseEdgeCase7() {
    //given
    var list = Arrays.stream("11102".split("")).map(Integer::parseInt).toList();

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(2, output);
  }
  @Test
  void traverseEdgeCase8() {
    //given
    var list = Arrays.stream("226210".split("")).map(Integer::parseInt).toList();

    //when
    long output = ACODE.solve(list);

    //then
    assertEquals(3, output);
  }

  @Test
  void traverseEdgeCase9() {
    //given
    var list = Arrays.stream("100001".split("")).map(Integer::parseInt).toList();

    //then
    assertThrows(RuntimeException.class, ()->ACODE.solve(list));
  }
}