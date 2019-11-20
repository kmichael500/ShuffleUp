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

        // Display Users hand
        final ArrayList<Integer> items = new ArrayList<>();
        final  GridView grid = findViewById(R.id.cardList);
        final CustomGridAdapter Adapter = new CustomGridAdapter(this, items);
        grid.setAdapter(Adapter);

        //displays user scores
        final GridView playerScoreGridView = findViewById(R.id.playerScores);
        final ArrayList<String> playerScoreStringList = new ArrayList<>();
        final ArrayAdapter<String> playerScoreArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playerScoreStringList);
        playerScoreGridView.setAdapter(playerScoreArrayAdapter);


        //Displays the pile (cards played so far)
        final GridView pile = findViewById(R.id.cardPile);
        final ArrayList<Integer> PileList = new ArrayList<>();
        final CustomGridAdapterPile pileAdapter = new CustomGridAdapterPile(this, PileList);
        pile.setAdapter(pileAdapter);

        //Labels on screen
        final TextView cur_suit = findViewById(R.id.current_suit);   //displays current suit
        final TextView roundInfo = findViewById(R.id.roundInfo);     //Displays round info
        final TextView playerLabel = findViewById(R.id.playerLabel); //Displays Player number

        //Button to control turns
        final Button nextPlayer = findViewById(R.id.nextPlayer);


        //gets options for game
        ArrayList<String> playerNames = getIntent().getExtras().getStringArrayList("PlayerNames");
        int numOfPlayers = getIntent().getExtras().getInt("numOfPlayers");
        int playTilPoints = getIntent().getExtras().getInt("playTilPoints");

        //create a game instantiation
        final Hearts game = new Hearts(numOfPlayers,0, playTilPoints, playerNames);

        //Refreshes the screen
        refreshScreen(game, cur_suit, playerLabel, roundInfo, PileList, playerScoreStringList,
                playerScoreArrayAdapter, Adapter, pileAdapter, items);



        // Event listener
        // Refreshes the screen when a new pile is created
        game.setOnNewPileChangeListener(new OnNewPileListener() {
            @Override
            public void onNewPileChange(boolean newValue) {
                System.out.println("NEW PILE CHANGE");
                refreshScreen(game, cur_suit, playerLabel, roundInfo, PileList, playerScoreStringList,
                        playerScoreArrayAdapter, Adapter, pileAdapter, items);
            }
        });


        //When a player clicks a card, it sends that choice to the hearts game class
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                //Player's choice
                Card newChoice = game.getCurrentPlayer().getAt(position);

                //sends the card to the game
                game.PlayCard(newChoice);

                //hides the cards until button to show next hand is pressed
                if(game.isValidPlay(newChoice)){
                    cardListView.setVisibility(View.GONE);
                }

                //When the next player clicks the button, their hand is shown
                nextPlayer.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //shows next player's hand
                        cardListView.setVisibility(View.VISIBLE);

                        //refreshes screen
                        refreshScreen(game, cur_suit, playerLabel, roundInfo, PileList, playerScoreStringList,
                        playerScoreArrayAdapter, Adapter, pileAdapter, items);
                    }
                });
            }
        });



    }

    //There has to be a better way to do this, but I didn't have time to figure it out.
    //Refreshes the screen
    public void refreshScreen(Hearts game, TextView cur_suit, TextView playerLabel, TextView pileText,
                              ArrayList<Integer> pileList, ArrayList<String> playerScoreStringList,
                              ArrayAdapter<String> playerScoreArrayAdapter, CustomGridAdapter mAdapter,
                              CustomGridAdapterPile pileAdapter, ArrayList<Integer> CardList){

        //Sets suit
        cur_suit.setText(game.getSuitString());

        //sets round info
        pileText.setText(game.getRoundInfo());

        //Display the current player
        playerLabel.setText(game.getCurrentPlayer().getPlayerName() + "'s Hand");

        CardList.clear();
        for (int i = 0; i<game.getCurrentPlayer().getHand().size(); i++){
            String uri = "@drawable/" + game.getCurrentPlayer().getHand().get(i).cardString();
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            CardList.add(imageResource);
        }

        //Displays the pile (cards played so far)
        pileList.clear();
        for (int i = 0; i< game.getPile().size(); i++){
            String uri = "@drawable/" + game.getPile().get(i).cardString();
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            pileList.add(imageResource);
        }

        //Displays the players scores
        playerScoreStringList.clear();
        for (int i = 0; i< game.getNumberOfPlayers(); i++){
            playerScoreStringList.add(game.getPlayers().get(i).getPlayerName() + ": " + game.getPlayers().get(i).getScore());
        }

        //Notifies the adapters of data change
        playerScoreArrayAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
        pileAdapter.notifyDataSetChanged();

    }

}