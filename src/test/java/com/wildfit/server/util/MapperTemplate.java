package com.wildfit.server.util;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.TreeSet;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.model.RecipeIngredient;

public class MapperTemplate {

    private static Set<String> getFieldSuffixes(final Class from) {
        final var fieldSuffixes = new TreeSet<String>();

        for (Field field : from.getDeclaredFields()) {
            final var fieldSuffix = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            fieldSuffixes.add(fieldSuffix);
        }
        return fieldSuffixes;
    }

    private static String getInstanceName(final Class from) {
        final var name = from.getSimpleName();

        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    public static void transform(final Class from, String to) {
        final var instanceName = getInstanceName(from);

        System.out.println("public static " + to + " map(" +
                from.getSimpleName() +
                " " + instanceName + ") {\n" +
                "        return " + to + ".builder()");
        for (var fieldSuffix : getFieldSuffixes(from)) {
            System.out.println(".with" + fieldSuffix + "(" + instanceName + ".get" + fieldSuffix + "())");
        }
        System.out.println(".build();");
        System.out.println("}");
    }

    public static void create(final Class from, String to) {
        final var instanceName = getInstanceName(from);

        System.out.println("public static " + to + " create(" +
                from.getSimpleName() +
                " " + instanceName + ") {\n" +
                "        return " + to + ".builder()");
        for (var fieldSuffix : getFieldSuffixes(from)) {
            System.out.println(".with" + fieldSuffix + "(" + instanceName + ".get" + fieldSuffix + "())");
        }
        System.out.println(".build();");
        System.out.println("}");
    }

    public static void main(String[] args) {
        MapperTemplate.transform(RecipeIngredient.class, "IngredientDigest");
        MapperTemplate.create(IngredientDigest.class, "RecipeIngredient");
    }
}
