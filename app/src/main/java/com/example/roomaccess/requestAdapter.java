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

public class requestAdapter extends ArrayAdapter<model_request> {

    ArrayList<model_request> modsList;
    Context context;
    public requestAdapter(@NonNull Context context, ArrayList<model_request> mods) {
        super(context, R.layout.list_request, mods);
        this.context = context;
        modsList = mods;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_request,null);
            TextView list_tv_request_user = convertView.findViewById(R.id.list_tv_request_user);
            TextView list_tv_req_message = convertView.findViewById(R.id.list_tv_req_message);
            TextView list_tv_room_name = convertView.findViewById(R.id.list_tv_room_name);
            Button btn_mod_delete = convertView.findViewById(R.id.btn_details);
            list_tv_request_user.setText(modsList.get(position).getUser());
            list_tv_req_message.setText(modsList.get(position).getMessage());
            list_tv_room_name.setText(modsList.get(position).getRoom());
//            btn_mod_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    manage_moderators.deleteMod(position);
//                }
//            });
        }
        return convertView;
    }
}
