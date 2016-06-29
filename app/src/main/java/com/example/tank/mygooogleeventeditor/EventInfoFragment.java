package com.example.tank.mygooogleeventeditor;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tank on 6/29/16.
 */

public class EventInfoFragment extends Fragment {

    private static final String EVENT_SUMMARY = "eventSummary";
    private static final String EVENT_START_TIME = "eventStartTime";
    private static final String EVENT_END_TIME = "eventEndTime";
    private static final String EVENT_DESCRIPTION = "eventDescription";
    private static final String EVENT_LOCATION = "eventLocation";
    private static final String EVENT_ATTENDEES = "eventAttendees";
    private static final String EVENT_CREATOR = "eventCreator";
    private static final String TAG = "EventInfoFragment";
    private String mEventSummary;
    private DateTime mEventStartTime;
    private DateTime mEventEndTime;
    private String mEventDescription;
    private String mEventLocation;
    private ArrayList<String> mEventAttendees;
    private String mEventCreator;
    private View mEventInforFragmentView;

    private TextView mEventTimeTextView;
    private TextView mEventLocationTextView;
    private TextView mEventAttendeesTextView;
    private TextView mEventDescriptionTextView;


    public static EventInfoFragment newInstance(Event event){
        EventInfoFragment eventInfoFragment = new EventInfoFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_SUMMARY, event.getSummary());
        args.putLong(EVENT_START_TIME, event.getStart().getDateTime().getValue());
        args.putLong(EVENT_END_TIME, event.getEnd() == null ? 0 : event.getEnd().getDateTime().getValue());
        args.putString(EVENT_DESCRIPTION, event.getDescription());
        args.putString(EVENT_LOCATION, event.getLocation());
        ArrayList<String> eventAttendees = new ArrayList<String>();
        for(EventAttendee eventAttendee : event.getAttendees()){
            eventAttendees.add(eventAttendee.getEmail());
        }
        args.putStringArrayList(EVENT_ATTENDEES, eventAttendees);
        args.putString(EVENT_CREATOR, event.getCreator().getEmail());
        eventInfoFragment.setArguments(args);
        return eventInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mEventSummary = getArguments().getString(EVENT_SUMMARY);
            mEventStartTime = new DateTime(getArguments().getLong(EVENT_START_TIME));
            mEventEndTime = new DateTime(getArguments().getLong(EVENT_END_TIME));
            mEventDescription = getArguments().getString(EVENT_DESCRIPTION);
            mEventLocation = getArguments().getString(EVENT_LOCATION);
            mEventAttendees = getArguments().getStringArrayList(EVENT_ATTENDEES);
            mEventCreator = getArguments().getString(EVENT_CREATOR);
        } else {
            Log.e(TAG, "no data in saved bundle state");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        mEventInforFragmentView = inflater.inflate(R.layout.fragment_event_info, container, false);
        init();
        return mEventInforFragmentView;

    }

    private void init() {
        mEventTimeTextView = (TextView) mEventInforFragmentView.findViewById(R.id.event_time_text_view);
        mEventLocationTextView = (TextView) mEventInforFragmentView.findViewById(R.id.event_location_text_view);
        mEventAttendeesTextView = (TextView) mEventInforFragmentView.findViewById(R.id.event_attendees_text_view);
        mEventDescriptionTextView = (TextView) mEventInforFragmentView.findViewById(R.id.event_description_text_view);

        mEventTimeTextView.setText(Util.INSTANCE.buildEventStartEndTime(mEventStartTime, mEventEndTime));
        mEventLocationTextView.setText(mEventLocation);
        mEventAttendeesTextView.setText(Integer.toString(mEventAttendees.size()));
        mEventDescriptionTextView.setText(mEventDescription);
    }
}
