package com.wildfit.server.util;

public class TransformEnum {
    private static final String INDENT = "   ";
    private static final String RIGHT_BRACE = "}";
    private static final String PERIOD = ".";

    private TransformEnum() {
    }

    public static void transform(final Enum from, final Enum to) {
        final Class fromKlazz = from.getDeclaringClass();
        final String fromClass = shortenClassName(fromKlazz.getCanonicalName());
        final String toClass = shortenClassName(to.getDeclaringClass().getCanonicalName());

        System.out.println("public static " +
                toClass +
                " transform" +
                to.getDeclaringClass().getSimpleName() +
                "(final " +
                fromClass +
                " status) {");

        System.out.println(INDENT + "if (status == null) {");
        System.out.println(INDENT + INDENT + "return null;");
        System.out.println(INDENT + RIGHT_BRACE);

        System.out.println(INDENT + "switch (status) {");
        for (Object obj : fromKlazz.getEnumConstants()) {
            System.out.println(INDENT + INDENT + "case " + obj + ": return " + toClass + PERIOD + obj + ";");
        }
        System.out.println(INDENT + INDENT + "default: return null;");
        System.out.println(INDENT + RIGHT_BRACE);
        System.out.println(RIGHT_BRACE);
    }

    private static String shortenClassName(final String canonicalName) {

        final String[] packages = canonicalName.split("\\.");
        final int index = packages.length - 1;

        return packages[index - 1] + PERIOD + packages[index];
    }
}
