package com.example.belot.model;

public class GameItem {

    private String scoreA;
    private String scoreB;

    public GameItem(String scoreA, String scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
    }

    /*
    *  Getters
    * */

    public String getScoreA() {
        return scoreA;
    }

    public String getScoreB() {
        return scoreB;
    }
}
