package com.example.demo.Models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameScoreDetailsTest {


    static GameScoreDetails gameScoreDetails;
    @BeforeAll
    static void intialize(){
         gameScoreDetails = new GameScoreDetails(1,1,50,"2022-03-08 23:04:000");
    }

    @Test
    void getGameId() {
        gameScoreDetails = new GameScoreDetails(1,1,50,"2022-03-08 23:04:000");
        assertEquals(1,gameScoreDetails.getGameId());
    }

    @Test
    void setGameId() {
        gameScoreDetails = new GameScoreDetails(1,1,50,"2022-03-08 23:04:000");
        gameScoreDetails.setGameId(34);
        assertEquals(34,gameScoreDetails.getGameId());
    }

    @Test
    void getUserID() {
        gameScoreDetails = new GameScoreDetails(1,1,50,"2022-03-08 23:04:000");
        assertEquals(1,gameScoreDetails.getUserID());
    }

    @Test
    void setUserID() {
        gameScoreDetails = new GameScoreDetails(1,1,50,"2022-03-08 23:04:000");
        gameScoreDetails.setUserID(32);
        assertEquals(32,gameScoreDetails.getUserID());

    }

    @Test
    void getUserScore() {
        gameScoreDetails = new GameScoreDetails(1,1,50,"2022-03-08 23:04:000");
        assertEquals(50,gameScoreDetails.getUserScore());
    }

    @Test
    void setUserScore() {
        gameScoreDetails = new GameScoreDetails(1,1,50,"2022-03-08 23:04:000");
        gameScoreDetails.setUserScore(39);
        assertEquals(39,gameScoreDetails.getUserScore());
    }

    @Test
    void getCreatedAt() {
        gameScoreDetails = new GameScoreDetails(1,1,50,"2022-03-08 23:04:000");
        assertEquals("2022-03-08 23:04:000",gameScoreDetails.getCreatedAt());
    }

    @Test
    void setCreatedAt() {
        gameScoreDetails = new GameScoreDetails(1,1,50,"2022-03-08 23:04:000");
        gameScoreDetails.setCreatedAt("2022-03-09 23:04:000");
        assertEquals("2022-03-09 23:04:000",gameScoreDetails.getCreatedAt());
    }
}