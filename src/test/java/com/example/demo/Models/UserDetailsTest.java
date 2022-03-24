package com.example.demo.Models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsTest {

    static UserDetails userDetails;

    @BeforeAll
    static void intialize(){
        userDetails = new UserDetails(10001,"Rohit");
    }


    @Test
    void getId() {
        userDetails = new UserDetails(10001,"Rohit");
        assertEquals(10001,userDetails.getId());
    }

    @Test
    void setId() {
        userDetails = new UserDetails(10001,"Rohit");
        userDetails.setId(1010);
        assertEquals(1010,userDetails.getId());
    }

    @Test
    void getUserName() {
        userDetails = new UserDetails(10001,"Rohit");
        assertEquals("Rohit",userDetails.getUserName());
    }

    @Test
    void setUserName() {
        userDetails = new UserDetails(10001,"Rohit");
        userDetails.setUserName("Ashutosh");
        assertEquals("Ashutosh",userDetails.getUserName());
    }
}