package com.condor.shuffleup;

import android.graphics.drawable.Drawable;

public class Card implements Comparable<Card> {

    private final static int HEARTS = 100,
            DIAMONDS = 200,
            SPADES = 300,
            CLUBS = 400;

    private final static int JACK = 11,
            QUEEN = 12,
            KING = 13,
            ACE = 14;

    private final int suit;

    private final int value;

    public Card(int suit, int value){
        this.suit = suit;
        this.value = value;
    }


    public int getSuit() {
        return suit;
    }

    public int getValue(){
        return value;
    }

    public String getValueString(){
        if (value < JACK){
            return String.valueOf(value);
        }
        else{
            switch (value){
                case JACK: return "Jack";
                case QUEEN: return "Queen";
                case KING: return "King";
                case ACE: return "Ace";
            }
            return "Error";
        }
    }

    public String cardString(){
        switch (suit){
            case HEARTS: return "Hearts: " + getValueString();
            case DIAMONDS: return "Diamonds: " + getValueString();
            case SPADES: return "Spades: " + getValueString();
            case CLUBS: return "Clubs: " + getValueString();
        }
        return "Error";
    }


    @Override
    public int compareTo(Card card) {
        return (this.value+this.suit) - (card.getValue()+card.getSuit());
    }
}