package com.condor.shuffleup;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;

import android.widget.ImageView;

import android.widget.TextView;
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
        final ArrayList<String> PileList = new ArrayList<>();
        final ArrayAdapter<String> pileListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, PileList);
        pile.setAdapter(pileListAdapter);

        //Labels on screen
        final TextView cur_suit = findViewById(R.id.current_suit);   //displays current suit
        final TextView roundInfo = findViewById(R.id.roundInfo);     //Displays round info
        final TextView playerLabel = findViewById(R.id.playerLabel); //Displays Player number


        //create a game instantiation
        final Hearts game = new Hearts(4,0, 15);

        //Refreshes the screen
        refreshScreen(game, cur_suit, playerLabel, roundInfo, PileList, playerScoreStringList,
                playerScoreArrayAdapter, Adapter, pileListAdapter, items);



        // Event listener
        // Refreshes the screen when a new pile is created
        game.setOnNewPileChangeListener(new OnNewPileListener() {
            @Override
            public void onNewPileChange(boolean newValue) {
                System.out.println("NEW PILE CHANGE");
                refreshScreen(game, cur_suit, playerLabel, roundInfo, PileList, playerScoreStringList,
                        playerScoreArrayAdapter, Adapter, pileListAdapter, items);
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

                //refreshes screen
                refreshScreen(game, cur_suit, playerLabel, roundInfo, PileList, playerScoreStringList,
                        playerScoreArrayAdapter, Adapter, pileListAdapter, items);
            }
        });



    }

    //There has to be a better way to do this, but I didn't have time to figure it out.
    //Refreshes the screen
    public void refreshScreen(Hearts game, TextView cur_suit, TextView playerLabel, TextView pileText,
                              ArrayList<String> pileList, ArrayList<String> playerScoreStringList,
                              ArrayAdapter<String> playerScoreArrayAdapter, CustomGridAdapter mAdapter,
                              ArrayAdapter<String> pileListAdapter, ArrayList<Integer> CardList){

        //Sets suit
        cur_suit.setText(game.getSuitString());

        //sets round info
        pileText.setText(game.getRoundInfo());

        //Display the current player
        playerLabel.setText("Player's " + (game.getPlayerNumber()+1) + " Hand");

        CardList.clear();
        for (int i = 0; i<game.getCurrentPlayer().getHand().size(); i++){
            String uri = "@drawable/" + game.getCurrentPlayer().getHand().get(i).cardString();
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            CardList.add(imageResource);
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
        mAdapter.notifyDataSetChanged();
        pileListAdapter.notifyDataSetChanged();

    }
    public class CustomGridAdapter extends BaseAdapter {
        private Activity mContext;

        // Keep all Images in array
        public ArrayList<Integer> mThumbIds;

        // Constructor
        public CustomGridAdapter(Activity mainActivity, ArrayList<Integer> items) {
            this.mContext = mainActivity;
            this.mThumbIds = items;
        }

        @Override
        public int getCount() {
            return mThumbIds.size();
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mThumbIds.get(position));

            imageView.setLayoutParams(new GridView.LayoutParams(320, 480));
            return imageView;
        }

    }
}