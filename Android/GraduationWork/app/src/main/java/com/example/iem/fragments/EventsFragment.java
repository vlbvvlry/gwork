package com.example.iem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iem.EventState;
import com.example.iem.EventStateAdapter;
import com.example.iem.R;
import com.example.iem.network.NetworkService;
import com.example.iem.network.models.Event;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {

    ArrayList<EventState> states = new ArrayList<EventState>();
    RecyclerView rv = null;

    public EventsFragment() {
        super(R.layout.fragment_events);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        loadEvents(view);
        recyclerInit(view);
        return view;
    }

    private void recyclerInit(View view) {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        EventStateAdapter esa = new EventStateAdapter(states);
        rv = view.findViewById(R.id.events_list);
        rv.setLayoutManager(llm);
        rv.setAdapter(esa);
    }

    private void loadEvents(View view) {
        (new NetworkService())
                .getApi()
                .getEvents()
                .enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                            List<Event> r = response.body();
                            for(int i = 0; i < r.size(); i++) {
                                states.add(new EventState(r.get(i).getTitle(),
                                        r.get(i).getContent()));
                            }
                            recyclerInit(view);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Event>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}
