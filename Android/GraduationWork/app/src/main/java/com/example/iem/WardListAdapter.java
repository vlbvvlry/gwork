package com.example.iem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WardListAdapter extends BaseAdapter {

    private final Context context;
    private final int resource;
    private final List<WardState> wardStateList;

    public WardListAdapter(@NonNull Context context, int resource, List<WardState> wardStateList) {
        this.context = context;
        this.resource = resource;
        this.wardStateList = wardStateList;
    }

    @Override
    public int getCount() {
        return wardStateList.size();
    }

    @Override
    public Object getItem(int i) {
        return wardStateList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }
        TextView tv = convertView.findViewById(R.id.profileName);
        System.out.println("TFLvl2 Adapter - " + tv.getText() + wardStateList.get(position).getId());
        tv.setText(wardStateList.get(position).getName());
        Button addBtn = convertView.findViewById(R.id.addButton);
        Button delBtn = convertView.findViewById(R.id.delButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
        return convertView;
    }
}
