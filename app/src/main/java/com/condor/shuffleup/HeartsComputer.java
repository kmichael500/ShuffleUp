package com.condor.shuffleup;

public class HeartsComputer {


    public void playCard(Hearts game){


        for (int i = 0; i< game.getCurrentPlayer().getHand().size(); i++){
            if (game.getCurrentPlayer().getHand().get(i).getSuit() == game.getSuit()){
                game.PlayCard(game.getCurrentPlayer().getHand().get(i));
                break;
            }
        }
    }

    public void leadCard(Hearts game){
        for (int i = 0; i< game.getCurrentPlayer().getHand().size(); i++){
            game.PlayCard(game.getCurrentPlayer().getHand().get(i));
            break;
        }
    }
}
