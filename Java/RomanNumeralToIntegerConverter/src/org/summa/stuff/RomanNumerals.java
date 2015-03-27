package org.summa.stuff;

import java.util.Arrays;

/**
 * Each item represents a Roman numeral and some info about how it relates to
 * other Roman numerals. The order of the items matters, they must be in
 * monotonic progression so that ordinal increases as value increases.
 * 
 */
enum RomanNumerals {
    I(1, true, "VX"), V(5, false, ""), X(10, true, "LC"), L(50, false, ""), C(
            100, true, "DM"), D(500, false, ""), M(1000, true, "");
    private int val;
    private boolean additive;
    private String legalMinuends;
    private String names = null;

    /**
     * @param value
     *            the Arabic value of this Roman numeral
     * @param additive
     *            can this Roman numeral be repeated, a la XX
     */
    RomanNumerals(int value, boolean additive, String legalMinuends) {
        val = value;
        this.additive = additive;
        this.legalMinuends = legalMinuends;
    }

    public int getIntValue() {
        return val;
    }

    public boolean isAdditive() {
        return additive;
    }

    public boolean isLegalMinuend(RomanNumerals otherNumeral) {
        return legalMinuends.contains(otherNumeral.name());
    }

    public boolean containsName(String name) {
        if (names == null) {
            getNumeralCharactersAsString();
        }
        return names.contains(name);
    }

    private void getNumeralCharactersAsString() {
        names = Arrays.toString(values());
        names = names.replaceAll("\\W", "");
    }

    public String getMinuends() {
        return legalMinuends;
    }
}