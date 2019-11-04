package com.condor.shuffleup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Player {

    private String playerID;
    private ArrayList<Card> hand = new ArrayList<>();

    private int cardCount = 0;

    public Card getAt(int index){
        return hand.get(index);
    }

    public void sortHand(){
        Collections.sort(hand);
    }

    public void addCard(Card card){
        cardCount++;
        hand.add(card);
    }

    public ArrayList<Card> getHand(){

        return hand;
    }

    //for use in the hearts class only
    public Card playCard(Card choice){
        hand.remove(choice);

        return choice;
    }
    public boolean hasSuit(int currentSuit){
        //determine if the players hand has the current suit
        //false by default
        boolean hasCurrentSuit = false;
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).getSuit() == currentSuit){
                hasCurrentSuit = true;
            }
        }
        return hasCurrentSuit;
    }

}
