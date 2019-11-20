package com.condor.shuffleup;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;


//custom adapter for adding player names
public class PlayerNameAdapter extends ArrayAdapter<EditText> {

    //used to store/retrieve name values
    private HashMap<String, String> textValues = new HashMap<String, String>();
    private HashMap<String, EditText> editTextObjects = new HashMap<>();

    private static final String TAG = "PlayerNameAdapter";
    private Context mContext;
    private int mResource;

    public PlayerNameAdapter(Context context, int resource, ArrayList<EditText> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean convertViewWasNull = false;
        if(view == null){
            view = inflater.inflate(mResource, parent, false);
            convertViewWasNull = true;
        }

        //Fade in animation
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeIn.setDuration(500);
        fadeIn.start();

        EditText editText = view.findViewById(R.id.editText1);

        if(convertViewWasNull ){
            //just once  on ListItem when convertView is null
            editText.addTextChangedListener(new GenericTextWatcher(editText));
        }

        //called on each getView call, to update view tags.
        editText.setTag("theFirstEditTextAtPos:"+position);
        editText.setHint(getItem(position).getHint().toString());


        return view;

    }

    @Override
    public void notifyDataSetChanged() {

        ArrayList<ObjectAnimator> animatorArrayList = new ArrayList<>();


        super.notifyDataSetChanged();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    //watches for changes to name fields
    private class GenericTextWatcher implements TextWatcher{

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {

            String text = editable.toString();
            //save the value for the given tag :
            PlayerNameAdapter.this.textValues.put(view.getTag().toString(), editable.toString());
        }
    }

    public String getValueFromFirstEditText(int position){
        //here you need to recreate the id for the first editText
        String result = textValues.get("theFirstEditTextAtPos:"+position);
        if(result ==null || result.equals(""))
            result = "Player " + (position+1);
        return result;
    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


}


