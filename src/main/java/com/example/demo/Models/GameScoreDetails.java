package com.example.demo.Models;

import com.google.gson.annotations.SerializedName;

public class GameScoreDetails {
    @SerializedName("game_id")
    private Integer gameId;
    @SerializedName("user_id")
    private Integer userID;
    @SerializedName("user_score")
    private Integer userScore;
    @SerializedName("created_at")
    private String createdAt;

    public GameScoreDetails(Integer gameId, Integer userID, Integer userScore, String createdAt) {
        this.gameId = gameId;
        this.userID = userID;
        this.userScore = userScore;
        this.createdAt = createdAt;
    }


    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
