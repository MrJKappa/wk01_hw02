package com.example.hw2;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginValidatorTest {
    @Test
    public void incorrectPassword() {
        ArrayList<String> cred = MainActivity.validate("Admin", "badpassword");
        assertEquals(1, cred.size());
    }

    @Test
    public void correctUsername() {
        ArrayList<String> cred = MainActivity.validate("Adm1n", "Password");
        assertEquals(1, cred.size());
    }

    @Test
    public void incorrectUsernameAndPassword() {
        ArrayList<String> cred = MainActivity.validate("ad", "pas");
        assertEquals(0, cred.size());
    }

    @Test
    public void correctUsernameAndPassword() {
        ArrayList<String> cred = MainActivity.validate("Admin", "Password");
        assertEquals(2, cred.size());
    }

}