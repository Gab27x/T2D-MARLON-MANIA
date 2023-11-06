package com.example.marlonmania.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.marlonmania.model.Player;


public class ControllerPlayers {
    private static ControllerPlayers instance;
    public static ControllerPlayers getInstance(){

        if(instance == null){
            instance = new ControllerPlayers();

        }

        return instance;
    }

    private ObservableList<Player> players;


    private ControllerPlayers(){
        players = FXCollections.observableArrayList();
        init();
    }
    public void init(){
        players.add(new Player("gab",600));
        players.add(new Player("gab2",200));
    }
    public void addPlayer(String nickName, double score){
        players.add(new Player(nickName,score));

    }


    public ObservableList<Player> getPlayers(){
        return players;
    }

}
