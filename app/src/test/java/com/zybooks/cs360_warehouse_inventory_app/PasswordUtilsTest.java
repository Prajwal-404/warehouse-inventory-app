package com.zybooks.cs360_warehouse_inventory_app;

import org.junit.Test;
import static org.junit.Assert.*;

public class PasswordUtilsTest {


    @Test
    public void hash_returnsNonNullResult() {
        String result = PasswordUtils.hash("password987");
        assertNotNull(result);
    }

    @Test
    public void hash_sameInputsResultInSameHash() {
        String hash1 = PasswordUtils.hash("Bond007");
        String hash2 = PasswordUtils.hash("Bond007");
        assertEquals(hash1, hash2);
    }

    @Test
    public void hash_differentInputsResultInDifferentHash() {
        String hash1 = PasswordUtils.hash("Bond007");
        String hash2 = PasswordUtils.hash("BondJamesBond007");
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void hash_ReturnsExpectdLength() {
        // SHA-256 should always produce a 64 character string
        String result = PasswordUtils.hash("Bond007");
        assertEquals(64, result.length());
    }
}
