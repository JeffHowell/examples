package org.summa.stuff;

import java.util.Arrays;

enum RomanNumerals {
    I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

    private int val;

    RomanNumerals(int value) {
        val = value;
    }

    public int getIntValue() {
        return val;
    }

    static public String names = null;

    static public boolean containsName(String name) {
        if (names == null) {
            getNumeralCharactersAsString();
        }
        return names.contains(name);
    }

    private static void getNumeralCharactersAsString() {
        names = Arrays.toString(values());
        names = names.replaceAll("\\W", "");
    }
}