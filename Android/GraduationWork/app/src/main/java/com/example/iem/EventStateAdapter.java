package com.example.iem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventStateAdapter extends RecyclerView.Adapter<EventStateAdapter.ViewHolder> {

    private final List<EventState> states;

    public EventStateAdapter(List<EventState> states) {
        this.states = states;
    }

    @NonNull
    @Override
    public EventStateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventStateAdapter.ViewHolder holder, int position) {
        EventState state = states.get(position);
        holder.title.setText(state.getTitle());
        holder.content.setText(state.getContent());
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView content;
        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.event_title);
            content = view.findViewById(R.id.event_content);
        }
    }


}
