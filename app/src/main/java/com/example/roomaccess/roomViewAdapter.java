package com.example.roomaccess;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class roomViewAdapter extends ArrayAdapter<model_my_room> {
    ArrayList<model_my_room> room_list;
    Context context;
    public roomViewAdapter(@NonNull Context context, ArrayList<model_my_room> rooms) {
        super(context, R.layout.list_room,rooms);
        this.context=context;
        room_list =  rooms;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_room,null);
            TextView tv_room_name = convertView.findViewById(R.id.list_tv_mod_name);
            TextView tv_access_type = convertView.findViewById(R.id.list_tv_access_type);
            tv_room_name.setText(room_list.get(position).getRoom().getRoom_name());
            tv_access_type.setText(room_list.get(position).getAccess().getAccess_type());
        }
        return convertView;
    }
}
