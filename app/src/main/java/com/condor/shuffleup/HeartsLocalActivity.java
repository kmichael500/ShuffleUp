package com.condor.shuffleup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;

public class HeartsLocalActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearts_local);


        //displays the cards in a players hand
        final GridView cardListView = findViewById(R.id.cardList);
        final ArrayList<String> cardStringList = new ArrayList<>();
        final ArrayAdapter<String> cardListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cardStringList);
        cardListView.setAdapter(cardListAdapter);


        //displays user scores
        final GridView playerScoreGridView = findViewById(R.id.playerScores);
        final ArrayList<String> playerScoreStringList = new ArrayList<>();
        final ArrayAdapter<String> playerScoreArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playerScoreStringList);
        playerScoreGridView.setAdapter(playerScoreArrayAdapter);


        //Displays the pile (cards played so far)
        final GridView pile = findViewById(R.id.cardPile);
        final ArrayList<String> PileList = new ArrayList<>();
        final ArrayAdapter<String> pileListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, PileList);
        pile.setAdapter(pileListAdapter);

        //Labels on screen
        final TextView cur_suit = findViewById(R.id.current_suit);   //displays current suit
        final TextView roundInfo = findViewById(R.id.roundInfo);     //Displays round info
        final TextView playerLabel = findViewById(R.id.playerLabel); //Displays Player number

        //Button to control turns
        final Button nextPlayer = findViewById(R.id.nextPlayer);



        //create a game instantiation
        final Hearts game = new Hearts(4,0, 15);


        //Refreshes the screen
        refreshScreen(game, cur_suit, playerLabel, roundInfo, cardStringList, PileList, playerScoreStringList,
                playerScoreArrayAdapter, cardListAdapter, pileListAdapter);



        // Event listener
        // Refreshes the screen when a new pile is created
        game.setOnNewPileChangeListener(new OnNewPileListener() {
            @Override
            public void onNewPileChange(boolean newValue) {
                System.out.println("NEW PILE CHANGE");
                refreshScreen(game, cur_suit, playerLabel, roundInfo, cardStringList, PileList, playerScoreStringList,
                        playerScoreArrayAdapter, cardListAdapter, pileListAdapter);
            }
        });


        //When a player clicks a card, it sends that choice to the hearts game class
        cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                //Player's choice
                Card newChoice = game.getCurrentPlayer().getAt(position);

                //sends the card to the game
                game.PlayCard(newChoice);

                //hides the cards until button to show next hand is pressed
                cardListView.setVisibility(View.GONE);

                //When the next player clicks the button, their hand is shown
                nextPlayer.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //shows next player's hand
                        cardListView.setVisibility(View.VISIBLE);

                        //refreshes screen
                        refreshScreen(game, cur_suit, playerLabel, roundInfo, cardStringList, PileList, playerScoreStringList,
                                playerScoreArrayAdapter, cardListAdapter, pileListAdapter);
                    }
                });
            }
        });



    }

    //There has to be a better way to do this, but I didn't have time to figure it out.
    //Refreshes the screen
    public void refreshScreen(Hearts game, TextView cur_suit, TextView playerLabel, TextView pileText, ArrayList<String> cardStringList,
                              ArrayList<String> pileList, ArrayList<String> playerScoreStringList,
                              ArrayAdapter<String> playerScoreArrayAdapter, ArrayAdapter<String> cardListAdapter,
                              ArrayAdapter<String> pileListAdapter){

        //Sets suit
        cur_suit.setText(game.getSuitString());

        //sets round info
        pileText.setText(game.getRoundInfo());

        //Display the current player
        playerLabel.setText("Player's " + (game.getPlayerNumber()+1) + " Hand");


        //Displays the current player's hand
        cardStringList.clear();
        for (int i = 0; i<game.getCurrentPlayer().getHand().size(); i++){
            cardStringList.add(game.getCurrentPlayer().getHand().get(i).cardString());
        }

        //Displays the pile (cards played so far)
        pileList.clear();
        for (int i = 0; i< game.getPile().size(); i++){
            pileList.add(game.getPile().get(i).cardString());
        }

        //Displays the players scores
        playerScoreStringList.clear();
        for (int i = 0; i< game.getNumberOfPlayers(); i++){
            playerScoreStringList.add("Player " + (i+1) + ": " + game.getPlayers().get(i).getScore());
        }

        //Notifies the adapters of data change
        playerScoreArrayAdapter.notifyDataSetChanged();
        cardListAdapter.notifyDataSetChanged();
        pileListAdapter.notifyDataSetChanged();

    }



}
