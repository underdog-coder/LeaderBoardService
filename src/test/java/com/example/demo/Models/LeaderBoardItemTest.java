package com.example.demo.Models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeaderBoardItemTest {


    static LeaderBoardItem leaderBoardItem;

    @BeforeAll
    static void intialize() {
        leaderBoardItem = new LeaderBoardItem("Rohit", 1.1, "1010");
    }


    @Test
    void getUserName() {
        leaderBoardItem = new LeaderBoardItem("Rohit", 1.1, "1010");
        assertEquals("Rohit", leaderBoardItem.getUserName());
    }

    @Test
    void setUserName() {
        leaderBoardItem = new LeaderBoardItem("Rohit", 1.1, "1010");
        leaderBoardItem.setUserName("Ashu");
        assertEquals("Ashu", leaderBoardItem.getUserName());
    }

    @Test
    void setUserID() {
        leaderBoardItem = new LeaderBoardItem("Rohit", 1.1, "1010");
        leaderBoardItem.setUserID("1001");
        assertEquals("1001", leaderBoardItem.getUserID());
    }

    @Test
    void getUserScore() {
        leaderBoardItem = new LeaderBoardItem("Rohit", 1.1, "1010");
        assertEquals(1.1, leaderBoardItem.getUserScore());
    }

    @Test
    void setUserScore() {
        leaderBoardItem = new LeaderBoardItem("Rohit", 1.1, "1010");
        leaderBoardItem.setUserScore(32);
        assertEquals(32, leaderBoardItem.getUserScore());
    }

    @Test
    void getUserID() {
        leaderBoardItem = new LeaderBoardItem("Rohit", 1.1, "1010");
        assertEquals("1010", leaderBoardItem.getUserID());
    }

}