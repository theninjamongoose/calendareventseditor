package com.example.tank.mygooogleeventeditor;

/**
 * Created by tank on 6/29/16.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.api.services.calendar.model.Event;

import java.util.List;

/**
 * Created by tank on 6/29/16.
 */

public class UserEventsFragment extends Fragment {

    private View mUserEventFragmentView;
    private List<Event> mEvents;
    private RecyclerView mMyEventsRecyclerView;
    private LinearLayoutManager mMyEventsLayoutManager;
    private MyEventsAdapter mMyEventsAdapter;

    public static UserEventsFragment newInstance(List<Event> events) {
        UserEventsFragment userEventsFragment = new UserEventsFragment();
        userEventsFragment.setEvents(events);
        return userEventsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        mUserEventFragmentView = inflater.inflate(R.layout.content_my_events, container, false);
        init();
        return mUserEventFragmentView;
    }

    private void init() {
        mMyEventsRecyclerView = (RecyclerView) mUserEventFragmentView.findViewById(R.id.my_events_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mMyEventsRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mMyEventsLayoutManager = new LinearLayoutManager(getActivity());
        mMyEventsRecyclerView.setLayoutManager(mMyEventsLayoutManager);
        // specify an adapter (see also next example)
        mMyEventsAdapter = new MyEventsAdapter(mEvents, getActivity());
        mMyEventsRecyclerView.setAdapter(mMyEventsAdapter);
    }

    public void setEvents(List<Event> events) {
        this.mEvents = events;
    }
}
