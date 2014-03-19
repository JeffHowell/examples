package org.summa.stuff;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RomanNumeralDecimalConverterTest {
    RomanNumeralDecimalConverter uut = null;

    @Before
    public void setUp() throws Exception {
	uut = new RomanNumeralDecimalConverter();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_null() {
	Assert.assertEquals(0, uut.convertRomanNumeralToInt(null));
    }

    @Test
    public void test_empty() {
	Assert.assertEquals(0, uut.convertRomanNumeralToInt(""));
    }

    @Test
    public void test_I() {
	Assert.assertEquals(1, uut.convertRomanNumeralToInt("I"));
    }

    @Test
    public void test_II() {
	Assert.assertEquals(2, uut.convertRomanNumeralToInt("II"));
    }

    @Test
    public void test_III() {
	Assert.assertEquals(3, uut.convertRomanNumeralToInt("III"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_IIII() {
	Assert.assertEquals(4, uut.convertRomanNumeralToInt("IIII"));
    }

    @Test
    public void test_IV() {
	Assert.assertEquals(4, uut.convertRomanNumeralToInt("IV"));
    }

    @Test
    public void test_V() {
	Assert.assertEquals(5, uut.convertRomanNumeralToInt("V"));
    }

    @Test
    public void test_VI() {
	Assert.assertEquals(6, uut.convertRomanNumeralToInt("VI"));
    }

    @Test
    public void test_VII() {
	Assert.assertEquals(7, uut.convertRomanNumeralToInt("VII"));
    }

    @Test
    public void test_VIII() {
	Assert.assertEquals(8, uut.convertRomanNumeralToInt("VIII"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_VIIII() {
	Assert.assertEquals(9, uut.convertRomanNumeralToInt("VIIII"));
    }

    @Test
    public void test_IX() {
	Assert.assertEquals(9, uut.convertRomanNumeralToInt("IX"));
    }

    @Test
    public void test_X() {
	Assert.assertEquals(10, uut.convertRomanNumeralToInt("X"));
    }

    @Test
    public void test_XI() {
	Assert.assertEquals(11, uut.convertRomanNumeralToInt("XI"));
    }

    @Test
    public void test_XII() {
	Assert.assertEquals(12, uut.convertRomanNumeralToInt("XII"));
    }

    @Test
    public void test_XIII() {
	Assert.assertEquals(13, uut.convertRomanNumeralToInt("XIII"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_XIIV() {
	Assert.assertEquals(13, uut.convertRomanNumeralToInt("XIIV"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_XIIII() {
	Assert.assertEquals(14, uut.convertRomanNumeralToInt("XIIII"));
    }

    @Test
    public void test_XIV() {
	Assert.assertEquals(14, uut.convertRomanNumeralToInt("XIV"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_IIIII() {
	Assert.assertEquals(5, uut.convertRomanNumeralToInt("IIIII"));
    }

    @Test
    public void test_XL() {
	Assert.assertEquals(40, uut.convertRomanNumeralToInt("XL"));
    }

    @Test
    public void test_MMXIII() {
	Assert.assertEquals(2013, uut.convertRomanNumeralToInt("MMXIII"));
    }

    @Test
    public void test_XXXIX() {
	Assert.assertEquals(39, uut.convertRomanNumeralToInt("XXXIX"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_VV() {
	Assert.assertEquals(0, uut.convertRomanNumeralToInt("VV"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_XBI() {
	Assert.assertEquals(0, uut.convertRomanNumeralToInt("XBI"));
    }

    @Test
    public void test_legalMinuend_XM() {
	Assert.assertEquals(false,
		RomanNumerals.X.isLegalMinuend(RomanNumerals.M));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_IL() {
	Assert.assertEquals(0, uut.convertRomanNumeralToInt("IL"));
    }

}
