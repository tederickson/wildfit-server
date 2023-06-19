package com.wildfit.server.util;

import java.lang.reflect.Field;

import com.wildfit.server.model.Recipe;

public class MapperTemplate {

    public static void transform(final Class from, String instanceName) {

        for (Field field : from.getDeclaredFields()) {
            final var fieldSuffix = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            System.out.println(".with" + fieldSuffix + "(" + instanceName + ".get" + fieldSuffix + "())");
        }
    }

    public static void main(String[] args) {
        MapperTemplate.transform(Recipe.class, "recipe");
    }
}
