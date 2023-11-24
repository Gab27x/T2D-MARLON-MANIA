package com.example.marlonmania.model;

public abstract class Game2 {

    private String nickName;

    private int numOfPipes;

    private String f;

    private String d;

    public Game2(String nickName){
        this.nickName = nickName;
        this.numOfPipes = 0;
    }

    public abstract void init();
    public abstract void addPipe(String vertex, State newState);
    public abstract void initGrafo();
    public abstract void simulate();


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getNumOfPipes() {
        return numOfPipes;
    }

    public void setNumOfPipes(int numOfPipes) {
        this.numOfPipes = numOfPipes;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }
}
