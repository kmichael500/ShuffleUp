package com.condor.shuffleup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CardDeck {

    private ArrayList<Card> deck = new ArrayList<Card>();

    public CardDeck(){
        for (int suit = 100; suit<=400; suit += 100){
            for (int cardVal = 1; cardVal <= 14; cardVal++){
                deck.add(new Card(suit, cardVal));
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public Card dealCard(){
        Card deltCard = deck.get(deck.size()-1);

        deck.remove(deck.size()-1);

        return deltCard;

    }

    public int deckSize(){
        return deck.size();
    }


}
