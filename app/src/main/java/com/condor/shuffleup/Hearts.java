package com.condor.shuffleup;

import java.util.ArrayList;

public class Hearts {
    private int turnNumber;
    private int suit;
    private Card currentChoice;
    //user should always be index 0
    private ArrayList<Player> players = new ArrayList<>();  //list of players - dependent on gamemode
    private ArrayList<Card> pile = new ArrayList<>();       //card pile
    private int gamemode;

    //set up the game
    Hearts(int playerCount, int mode, ArrayList<Player> playerlist){
        players = (ArrayList) playerlist.clone();
        gamemode = mode;
    }

    public void setCurrentChoice(Card currentChoice) {
        this.currentChoice = currentChoice;
    }

    public int getTurnNumber(){
        return turnNumber;
    }

    public Card gettopStack(){
        return pile.get(pile.size() - 1);
    }
    public int getSuit(){
        return suit;
    }

    public void playGame(){
        //play function
        boolean gameover = false;
        int curPlayer = 0;

        Turn(currentChoice, players.get(0));
        turnNumber++;


    }

    private void Turn(Card choice, Player curplayer){
        //remove card from hand and add it to the pile
        pile.add(curplayer.playCard(choice));
    }

}
