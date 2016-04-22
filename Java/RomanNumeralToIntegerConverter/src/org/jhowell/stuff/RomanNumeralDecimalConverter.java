package org.jhowell.stuff;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Converts a String of Roman Numerals to an int value.
 * <ul>
 * <li>Validate all characters in the String are recognized as Roman numerals</li>
 * <li>Validate the grammar rules:
 * <ul>
 * <li>A Roman character can be followed by a lower valued Roman character, but
 * the lower value character can be repeated at most 3 times. [ XIII is OK, but
 * XIIII is an error ]</li>
 * <li>A Roman character can be preceded by a lower valued Roman character, but
 * the lower value character can not be repeated. [ IX is OK, but IIX is an
 * error ]</li>
 * </ul>
 * </li>
 * <li>Convert the numerals</li>
 * </ul>
 * 
 * @author jeff
 */
public class RomanNumeralDecimalConverter {
    private List<RomanNumerals> numeralsList = new ArrayList<RomanNumerals>();
    private static final String INVALID_PREAMBLE = "The roman numeral string appears to be invalid.";

    public int convertRomanNumeralToInt(String romanNumerals) {
        if (romanNumerals == null) {
            throw new IllegalArgumentException("input is null");
        }
        parseRomanNumeralString(romanNumerals);
        if (numeralsList.isEmpty()) {
            return 0; // ironic as Roman numerals cannot represent the value 0
        } else {
            return eventaluateNumerals();
        }
    }

    private void parseRomanNumeralString(String roman) {
        for (int idx = 0; idx < roman.length(); idx++) {
            String aNumeral = getCharAt(roman, idx);
            addNumeralToList(aNumeral);
        }
        validateRomanGrammar();
    }

    private void addNumeralToList(String numeral) {
        validateLetterIsRomanNumeral(numeral);
        RomanNumerals theNumeral = RomanNumerals.valueOf(numeral);
        numeralsList.add(theNumeral);
    }

    private String getCharAt(String theString, int index) {
        return theString.substring(index, index + 1);
    }

    private void validateLetterIsRomanNumeral(String aChar) {
        if (RomanNumerals.valueOf(aChar) == null) {
            unrecognizedRomanNumeralError(aChar);
        }
    }

    private void validateRomanGrammar() {
        if (numeralsList.size() > 1) {
            checkIlleagalRepeats();
            checkIllegalMinuends();
        }
        if (numeralsList.size() > 2) {
            checkSubtractiveLengths();
            checkAdditiveLengths();
        }
    }

    private void checkIllegalMinuends() {
        ListIterator<RomanNumerals> iter = numeralsList.listIterator();
        RomanNumerals numeral = iter.next();
        while (iter.hasNext()) {
            RomanNumerals nextNumeral = iter.next();
            if (isNumeralLessThanNumeral(numeral, nextNumeral)
                    && !numeral.isLegalMinuend(nextNumeral)) {
                subtractiveMinuendError(numeral, nextNumeral);
            }
        }
    }

    private void checkIlleagalRepeats() {
        ListIterator<RomanNumerals> iter = numeralsList.listIterator();
        RomanNumerals numeral = iter.next();
        while (iter.hasNext()) {
            RomanNumerals nextNumeral = iter.next();
            if ((numeral == nextNumeral) && !numeral.isAdditive()) {
                additiveNumeralError(numeral);
            }
            numeral = nextNumeral;
        }
    }

    private void checkAdditiveLengths() {
        ListIterator<RomanNumerals> iter = numeralsList.listIterator();
        RomanNumerals numeral = iter.next();
        checkAdditiveLengths(iter, numeral, 1);
    }

    // recursive!
    private void checkAdditiveLengths(ListIterator<RomanNumerals> iter,
            RomanNumerals numeral, int len) {
        int length = len;
        if (iter.hasNext()) {
            RomanNumerals nextNumeral = iter.next();
            if ((numeral == nextNumeral) && !numeral.isAdditive()) {
                additiveNumeralError(numeral);
            }
            if (numeral == nextNumeral) {
                if (++length > 3) {
                    additiveLengthError(numeral);
                }
                checkAdditiveLengths(iter, nextNumeral, length);
            } else {
                checkAdditiveLengths(iter, nextNumeral, 1);
            }
        }
    }

    private void checkSubtractiveLengths() {
        ListIterator<RomanNumerals> iter = numeralsList.listIterator();
        RomanNumerals numeral = iter.next();
        checkSubtractiveLength(iter, numeral, 1);
    }

    // recursive!
    private void checkSubtractiveLength(ListIterator<RomanNumerals> iter,
            RomanNumerals numeral, int len) {
        int length = len;
        if (iter.hasNext()) {
            RomanNumerals nextNumeral = iter.next();
            if (numeral == nextNumeral) {
                checkSubtractiveLength(iter, nextNumeral, ++length);
            } else if (isNumeralLessThanNumeral(numeral, nextNumeral)
                    && length > 1) {
                subtractiveLengthError(numeral, nextNumeral);
            } else {
                checkSubtractiveLength(iter, nextNumeral, 1);
            }
        }
    }

    private boolean isNumeralLessThanNumeral(RomanNumerals numeral,
            RomanNumerals other) {
        return numeral.ordinal() < other.ordinal();
    }

    private int eventaluateNumerals() {
        ListIterator<RomanNumerals> iter = numeralsList.listIterator();
        RomanNumerals numeral = iter.next();
        int accumulator = numeral.getIntValue();
        while (iter.hasNext()) {
            RomanNumerals nextNumeral = iter.next();
            if (isNumeralLessThanNumeral(numeral, nextNumeral)) {
                // subtractive - back out the previous addition and then again
                // to subtract from the numeral to the right
                accumulator = accumulator - 2 * numeral.getIntValue();
            }
            accumulator += nextNumeral.getIntValue();
            numeral = nextNumeral;
        }
        return accumulator;
    }

    private void unrecognizedRomanNumeralError(String unknownChar) {
        throw new IllegalArgumentException(
                "The roman numeral string appears to be invalid.  \""
                        + unknownChar
                        + "\" is not a valid Roman numeral character.");
    }

    private void subtractiveLengthError(RomanNumerals numeral,
            RomanNumerals nextNumeral) {
        throw new IllegalArgumentException(INVALID_PREAMBLE
                + "  Did not expect more than one " + numeral.name()
                + " before " + nextNumeral.name() + ".");
    }

    private void subtractiveMinuendError(RomanNumerals numeral,
            RomanNumerals nextNumeral) {
        throw new IllegalArgumentException("Roman numeral " + numeral
                + "cannot preceed " + nextNumeral + "."
                + " It may only preceed " + numeral.getMinuends());
    }

    private void additiveLengthError(RomanNumerals numeral) {
        throw new IllegalArgumentException(INVALID_PREAMBLE
                + "  Did not expect more than three \"" + numeral.name()
                + "\"s in a row.");
    }

    private void additiveNumeralError(RomanNumerals numeral) {
        throw new IllegalArgumentException(INVALID_PREAMBLE + numeral.name()
                + " cannot be repeated.");
    }
}