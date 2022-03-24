package com.example.demo.Models;

public class LeaderBoardItem {
    private String userName;
    private double userScore;
    private String userID;

    public LeaderBoardItem(String userName, double userScore, String userID) {
        this.userName = userName;
        this.userScore = userScore;
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getUserScore() {
        return userScore;
    }

    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
