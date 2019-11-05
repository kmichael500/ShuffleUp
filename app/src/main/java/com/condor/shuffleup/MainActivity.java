package com.condor.shuffleup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Card choice = new Card(100,12);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView cardListView = (GridView) findViewById(R.id.cardList);
        final TextView topstack = (TextView) findViewById(R.id.cardPile);
        final TextView cur_suit = (TextView) findViewById(R.id.current_suit);
        final ArrayList<String> cardStringList = new ArrayList<>();

        final ArrayAdapter<String> cardListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cardStringList);
        cardListView.setAdapter(cardListAdapter);



        //create the deck at the start
        CardDeck deck = new CardDeck();

        Card card;

        final Player player1 = new Player();

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

        //create a game instantiation
        final Hearts game = new Hearts(1,0, playerList);
        game.setCurrentChoice(choice);

        //game.playGame();
        //update the top of stack

        cur_suit.setText("Current Suit: " + Integer.toString(game.getSuit()));
        cardListAdapter.notifyDataSetChanged();


        //event listener for move selection
        cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Card newchoice = player1.getAt(position);
                if(game.getTurnNumber() == 0){
                    cur_suit.setText("Current Suit: " + newchoice.getSuitString());
                }
                System.out.println("WOOOOOT : " + newchoice.cardString());
                game.Turn(newchoice ,player1);
                topstack.setText(game.gettopStack().cardString());
                cardStringList.remove(position);
                cardListAdapter.notifyDataSetChanged();



            }
        });

    }

}
