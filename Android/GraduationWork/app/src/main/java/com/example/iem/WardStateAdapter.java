package com.example.iem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
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

public class WardStateAdapter extends ArrayAdapter<WardState> {

    private final Context context;
    private final int resource;
    private final List<WardState> wardStateList;
    private FragmentChanger fragmentChanger;

    public interface FragmentChanger {
        void taskAddingDisplay(int position);
    }

    public WardStateAdapter(@NonNull Context context, int resource, List<WardState> wardStateList) {
        super(context, resource, wardStateList);
        this.context = context;
        this.resource = resource;
        this.wardStateList = wardStateList;
    }

    public void setFragmentChanger(FragmentChanger fragmentChanger) {
        this.fragmentChanger = fragmentChanger;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        ((TextView)convertView
                .findViewById(R.id.profileName))
                .setText(wardStateList.get(position).getName());
        ((Button)convertView
                .findViewById(R.id.addButton))
                .setOnClickListener(view -> {
                    fragmentChanger.taskAddingDisplay(position);
                });
        ((Button)convertView
                .findViewById(R.id.delButton))
                .setOnClickListener(view -> {
                    //
                });
        Log.d("WardListAdapter", "getView actuation. Position: " + position);
        Log.d("WardListAdapter. getView.", "List size: " + wardStateList.size());
        return convertView;
    }
}
