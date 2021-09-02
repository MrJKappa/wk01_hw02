package com.example.hw2;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GetUserTest {
    @Test
    public void dneUser() {
        assertEquals(null, MainActivity.getUser("Ad", "Password"));
    }

    @Test
    public void existUser() {
        ArrayList<String> user = (MainActivity.getUser("Admin", "Password"));
        assertEquals(3, user.size());
    }

}