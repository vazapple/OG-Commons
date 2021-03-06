/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.basics;

import static com.opengamma.collect.TestHelper.assertJodaConvert;
import static com.opengamma.collect.TestHelper.assertSerialization;
import static com.opengamma.collect.TestHelper.assertThrows;
import static com.opengamma.collect.TestHelper.coverEnum;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test {@link LongShort}.
 */
@Test
public class LongShortTest {

  //-------------------------------------------------------------------------
  public void test_ofLong() {
    assertEquals(LongShort.ofLong(true), LongShort.LONG);
    assertEquals(LongShort.ofLong(false), LongShort.SHORT);
  }

  public void test_isLong() {
    assertEquals(LongShort.LONG.isLong(), true);
    assertEquals(LongShort.SHORT.isLong(), false);
  }

  public void test_isShort() {
    assertEquals(LongShort.LONG.isShort(), false);
    assertEquals(LongShort.SHORT.isShort(), true);
  }

  //-------------------------------------------------------------------------
  @DataProvider(name = "name")
  static Object[][] data_name() {
    return new Object[][] {
        {LongShort.LONG, "Long"},
        {LongShort.SHORT, "Short"},
    };
  }

  @Test(dataProvider = "name")
  public void test_toString(LongShort convention, String name) {
    assertEquals(convention.toString(), name);
  }

  @Test(dataProvider = "name")
  public void test_of_lookup(LongShort convention, String name) {
    assertEquals(LongShort.of(name), convention);
  }

  public void test_of_lookup_notFound() {
    assertThrows(() -> LongShort.of("Rubbish"), IllegalArgumentException.class);
  }

  public void test_of_lookup_null() {
    assertThrows(() -> LongShort.of(null), IllegalArgumentException.class);
  }

  //-------------------------------------------------------------------------
  public void coverage() {
    coverEnum(LongShort.class);
  }

  public void test_serialization() {
    assertSerialization(LongShort.LONG);
  }

  public void test_jodaConvert() {
    assertJodaConvert(LongShort.class, LongShort.LONG);
  }

}
