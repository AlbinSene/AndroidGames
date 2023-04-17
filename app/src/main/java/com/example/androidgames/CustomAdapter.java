package com.example.androidgames;

//import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;



    public CustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_list_games, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.gamename);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button playBtn = (Button)view.findViewById(R.id.play_btn);


        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String s = list.get(position);

                switch (s){
                    case "Light_sensor":
                        Intent lightSensorIntent = new Intent(context, Light_sensor.class);
                        context.startActivity(lightSensorIntent);
                        break;
                    case "Mega_ball":
                        Intent megaBallIntent = new Intent(context, MegaBall.class);
                        context.startActivity(megaBallIntent);
                        break;
                    case "Questionnaire":
                        Intent questionnaireIntent = new Intent(context, Questionnaire.class);
                        context.startActivity(questionnaireIntent);
                        break;
                    case "Speed_Questionnaire":
                        Intent speedQuestionnaireIntent = new Intent(context, SpeedQuestionnaire.class);
                        context.startActivity(speedQuestionnaireIntent);
                        break;
                }



            }
        });

        return view;
    }
}
