package com.unifier.util;

public class TrueEquals {

    public static boolean compare(boolean b1, boolean b2) {
        return b1 && b2;
    }

    public static boolean compare(boolean b1, boolean b2, boolean b3) {
        return b1 && b2 && b3;
    }

    public static boolean compare(boolean b1, boolean b2, boolean b3, boolean b4) {
        return b1 && b2 && b3 && b4;
    }

    public static boolean compare(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5) {
        return b1 && b2 && b3 && b4 && b5;
    }

    public static boolean compare(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6) {
        return b1 && b2 && b3 && b4 && b5 && b6;
    }

    public static boolean compare(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6, boolean b7) {
        return b1 && b2 && b3 && b4 && b5 && b6 && b7;
    }

    public static boolean compare(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6, boolean b7, boolean b8) {
        return b1 && b2 && b3 && b4 && b5 && b6 && b7 & b8;
    }
}
