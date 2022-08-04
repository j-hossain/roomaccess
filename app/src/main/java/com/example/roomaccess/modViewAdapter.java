package com.example.roomaccess;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class modViewAdapter extends ArrayAdapter<model_user> {
    ArrayList<model_user> modsList;
    Context context;

    public modViewAdapter(@NonNull Context context, ArrayList<model_user> mods) {
        super(context, R.layout.list_room, mods);
        this.context = context;
        modsList = mods;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_mods,null);
            TextView tv_mod_name = convertView.findViewById(R.id.list_tv_mod_name);
            TextView tv_mod_email = convertView.findViewById(R.id.list_tv_mod_email);
            Button btn_mod_delete = convertView.findViewById(R.id.btn_delete_mod);
            tv_mod_name.setText(modsList.get(position).getName());
            tv_mod_email.setText(modsList.get(position).getEmail());
            btn_mod_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manage_moderators.deleteMod(position);
                }
            });
        }
        return convertView;
    }
}
