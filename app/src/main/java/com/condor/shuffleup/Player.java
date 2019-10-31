package com.condor.shuffleup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Player {

    private String playerID;
    private ArrayList<Card> hand = new ArrayList<>();

    private int cardCount = 0;



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

    public Card playCard(Card card){
        hand.remove(card);
        return card;
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
