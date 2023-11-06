package com.example.marlonmania.model;

public class Player {
    private String nickName;
    private double score;

    public Player(String nickName, double score) {
        this.nickName = nickName;
        this.score = score;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "NickName: " + nickName
                + "\nScore: " + score;
    }
}
