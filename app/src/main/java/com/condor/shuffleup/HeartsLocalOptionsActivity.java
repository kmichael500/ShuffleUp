package com.condor.shuffleup;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HeartsLocalOptionsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearts_local_options);

        final Button startGameButton = findViewById(R.id.startLocalHeartsGame);
        final TextView numPlayersInput = findViewById(R.id.numOfPlayers);
        final TextView playTilPointsInput = findViewById(R.id.playTilPoints);


        //used to store number of players since object has to be final
        final HeartsUserOptions options = new HeartsUserOptions();
        options.context = this;

        ListView dynamicNames = findViewById(R.id.dynamicNames);
        final ArrayList<EditText> nameList= new ArrayList<>();
        final PlayerNameAdapter nameAdapter = new PlayerNameAdapter(this, R.layout.adapter_view_edit_text, nameList);
        dynamicNames.setAdapter(nameAdapter);
        nameAdapter.notifyDataSetChanged();

        //gets the number of players and adds EditText Objects
        numPlayersInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //clears names if number of players changes
                nameList.clear();

                //If string equals "", sets number of players to 0
                if(!numPlayersInput.getText().toString().equals("")){
                    options.numberOfPLayers = Integer.valueOf(numPlayersInput.getText().toString());
                }
                else{
                    options.numberOfPLayers = 0;
                }
                //adds edit text objects to nameList based on number of players
                for (int i = 0; i<options.numberOfPLayers; i++){
                    nameList.add(new EditText(HeartsLocalOptionsActivity.this));
                    nameList.get(i).setHint("Player " + (i+1));
                }
                nameAdapter.notifyDataSetChanged();

            }
        });

        //Gets the playTilPoints for game
        playTilPointsInput.addTextChangedListener(new TextWatcher() {
            boolean hint;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                //If string equals "", sets starting points to 0
                if(!playTilPointsInput.getText().toString().equals("")){
                    options.playTilPoints = Integer.valueOf(playTilPointsInput.getText().toString());
                }
                else{
                    options.playTilPoints = 0;
                }

            }
        });


        //starts the game and sends in game options
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gets the names and adds them to an arrayList
                ArrayList<String> nameArrayList = new ArrayList<>();
                for (int i = 0; i<nameAdapter.getCount(); i++){
                    nameArrayList.add(nameAdapter.getValueFromFirstEditText(i));
                }
                //default playTilPoints = 50;
                if (options.playTilPoints == 0){
                    options.playTilPoints = 50;
                }

                if (options.numberOfPLayers == 0){
                    Toast.makeText(HeartsLocalOptionsActivity.this, "Please enter the number of players",
                            Toast.LENGTH_LONG).show();
                }

                //starts game with options
                if (options.numberOfPLayers != 0){
                    Intent myIntent = new Intent(HeartsLocalOptionsActivity.this, HeartsLocalActivity.class);
                    myIntent.putExtra("PlayerNames", nameArrayList);
                    myIntent.putExtra("numOfPlayers", options.numberOfPLayers);
                    myIntent.putExtra("playTilPoints", options.playTilPoints);

                    startActivity(myIntent);
                }

            }
        });


    }
}
