package org.algo.spoj.dynamic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class ACODETest {

  @Test
  void traverse2() {
    //given
    var list = List.of(2,1);

    //when
    long output = ACODE.traverse(list);

    //then
    assertEquals(2, output);
  }
  @Test
  void traverse2withOnly1Option() {
    //given
    var list = List.of(5,1);

    //when
    long output = ACODE.traverse(list);

    //then
    assertEquals(1, output);
  }

  @Test
  void traverse3() {
    //given
    var list = List.of(2,1,2);

    //when
    long output = ACODE.traverse(list);

    //then
    assertEquals(3, output);
  }

  @Test
  void traverse4() {
    //given
    var list = List.of(2,1,2,1);

    //when
    long output = ACODE.traverse(list);

    //then
    assertEquals(5, output);
  }

  @Test
  void traverse5with2chunks() {
    //given
    var list = List.of(2,5,1,1,4);

    //when
    long output = ACODE.traverse(list);

    //then
    assertEquals(6, output);
  }

  @Test
  void traverse10of1() {
    //given
    var list = List.of(1,1,1,1,1,1,1,1,1,1);

    //when
    long output = ACODE.traverse(list);

    //then
    assertEquals(89, output);
  }

  @Test
  void traverse10of3() {
    //given
    var list = List.of(3,3,3,3,3,3,3,3,3,3);

    //when
    long output = ACODE.traverse(list);

    //then
    assertEquals(1, output);
  }

  @Test
  void traverse3of10() {
    //given
    var list = List.of(1,0,1,0,1,0);

    //when
    long output = ACODE.traverse(list);

    //then
    assertEquals(1, output);
  }
}