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


    public int getSuit() { return suit; }
    public String getSuitString(){
        switch (suit){
            case HEARTS: return "Hearts";
            case SPADES: return "Spades";
            case CLUBS: return "Clubs";
            case DIAMONDS: return "Diamonds";
        }
        return "Error";
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
                case JACK: return "j";
                case QUEEN: return "q";
                case KING: return "k";
                case ACE: return "a";
            }
            return "Error";
        }
    }

    public String cardString(){
        switch (suit){
            case HEARTS: return "h" + getValueString();
            case DIAMONDS: return "d" + getValueString();
            case SPADES: return "s" + getValueString();
            case CLUBS: return "c" + getValueString();
        }
        return "Error";
    }


    @Override
    public int compareTo(Card card) {
        return (this.value+this.suit) - (card.getValue()+card.getSuit());
    }
}