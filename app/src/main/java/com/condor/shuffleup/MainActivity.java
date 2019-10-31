package com.condor.shuffleup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView cardListView = (GridView) findViewById(R.id.cardList);
        TextView topstack = (TextView) findViewById(R.id.cardPile);
        ArrayList<String> cardStringList = new ArrayList<>();

        ArrayAdapter<String> cardListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cardStringList);
        cardListView.setAdapter(cardListAdapter);

        cardListView.setAdapter(cardListAdapter);

        //create the deck at the start
        CardDeck deck = new CardDeck();

        Card card;

        Player player1 = new Player();

        deck.shuffle();

        for ( int i = 0; i<13; i++){
            player1.addCard(deck.dealCard());
        }

        player1.sortHand();

        ArrayList<Card> player1Hand = player1.getHand();

        for (int i = 0; i<player1Hand.size(); i++){
            cardStringList.add(player1Hand.get(i).cardString());
        }

        cardListAdapter.notifyDataSetChanged();

        //add all players here
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player1);

        //WE NEED TO GET A CARD CHOICE FROM LIST:
        //just a temp for test
        Card choice = new Card(100,12);

        Hearts game = new Hearts(1,0, playerList);
        game.setCurrentChoice(choice);

        game.playGame();
        //update the top of stack
        topstack.setText(game.gettopStack().cardString());
        cardListAdapter.notifyDataSetChanged();





    }
}