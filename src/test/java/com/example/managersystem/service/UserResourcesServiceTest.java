package com.example.managersystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserResourcesServiceTest {

    @Autowired
    private UserResourcesService userResourcesService;

    @Test
    void assign() {
        userResourcesService.assign(123, Arrays.asList("C","D","E"));
        userResourcesService.assign(345,Arrays.asList("C","D","B"));
        userResourcesService.assign(456,Arrays.asList("A","E","B"));

    }

    // run assign first before calling this method
    @Test
    void hasUserResource() {
        boolean hasPermission1 = userResourcesService.hasUserResource(123, "D");
        boolean hasPermission2 = userResourcesService.hasUserResource(345, "B");
        boolean hasPermission3 = userResourcesService.hasUserResource(456, "C");
        assertTrue(hasPermission1);
        assertTrue(hasPermission2);
        assertFalse(hasPermission3);
    }
}