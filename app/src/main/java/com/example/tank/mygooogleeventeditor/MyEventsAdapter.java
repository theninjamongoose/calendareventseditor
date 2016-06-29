package com.example.tank.mygooogleeventeditor;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.api.services.calendar.model.Event;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tank on 6/28/16.
 */
public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.ViewHolder>{
    private final Activity mParentActivity;
    private List<Event> mEvents = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mEventDayOfWeekTextView;
        public TextView mEventDateTextView;
        public TextView mEventSummaryTimeLocTextView;
        public TextView mEventMonthAbrvTextView;
        public ViewHolder(View rowView) {
            super(rowView);
            this.mEventSummaryTimeLocTextView = (TextView) rowView.findViewById(R.id.event_summary_time_loc_text_view);
            this.mEventDateTextView = (TextView) rowView.findViewById(R.id.event_date_text_view);
            this.mEventMonthAbrvTextView = (TextView) rowView.findViewById(R.id.event_month_abrv_text_view);
            this.mEventDayOfWeekTextView = (TextView) rowView.findViewById(R.id.event_day_of_week_text_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyEventsAdapter(List<Event> events, Activity activity) {
        this.mEvents = events;
        mParentActivity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyEventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_event_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        init(holder, position);
    }

    private void init(final ViewHolder holder, final int position) {
        final Event currentEvent = mEvents.get(position);
        DateTime startDate = new DateTime(currentEvent.getStart().getDateTime().getValue());
        DateTime endDate = currentEvent.getEnd() == null ?
                null : new DateTime(currentEvent.getEnd().getDateTime().getValue());
        String eventTime = Util.INSTANCE.buildEventStartEndTime(startDate, endDate);
        holder.mEventSummaryTimeLocTextView.setText(currentEvent.getSummary() + "\n"
                + eventTime + " at " + currentEvent.getLocation());
        holder.mEventSummaryTimeLocTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launch fragment, bundling event
                FragmentTransaction fragmentTransaction = mParentActivity.getFragmentManager().beginTransaction();
                fragmentTransaction.add(android.R.id.content, EventInfoFragment.newInstance(currentEvent)).commit();
            }
        });



        holder.mEventDateTextView.setText(Integer.toString(startDate.getDayOfMonth()));
        holder.mEventMonthAbrvTextView.setText(startDate.toString("MMM"));
        holder.mEventDayOfWeekTextView.setText(startDate.toString("E"));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mEvents.size();
    }



}
