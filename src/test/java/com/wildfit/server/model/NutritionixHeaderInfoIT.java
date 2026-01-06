package com.wildfit.server.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class NutritionixHeaderInfoIT {
    @Autowired
    NutritionixHeaderInfo nutritionixHeaderInfo;

    @Test
    void getAppId() {
        assertEquals("feed098f", nutritionixHeaderInfo.getAppId());
    }

    @Test
    void getAppKey() {
        assertNotNull(nutritionixHeaderInfo.getAppKey());
    }

    @Test
    void getRemoteUserId() {
        assertEquals("0", nutritionixHeaderInfo.getRemoteUserId());
    }

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(NutritionixHeaderInfo.class, hasValidBeanConstructor());
    }

    @Test
    public void toStringOutput() {
        assertNotNull(nutritionixHeaderInfo.toString());
    }
}