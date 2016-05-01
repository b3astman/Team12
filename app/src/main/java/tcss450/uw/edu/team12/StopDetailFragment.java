package tcss450.uw.edu.team12;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tcss450.uw.edu.team12.model.Route;
import tcss450.uw.edu.team12.model.Stop;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopDetailFragment extends Fragment {

    // Captures which stop was selected amongst stops
    public static String STOP_SELECTED;

    //    private TextView mStopIdTextView;
    private TextView mRouteIdTextView;
    private TextView mRouteNoTextView;
    private TextView mRouteDestinationTextView;
    private TextView mDepartureTimeTextView;
    private TextView mMinutesLeftTextView;

    public StopDetailFragment() {
        // Required empty public constructor
    }

//    public void updateView(Stop stop) {
//        STOP_SELECTED = stop.getStopId();
//        if (stop != null) {
////            mCourseIdTextView.setText(course.getCourseID());
////            mCourseShortDescTextView.setText(course.getShortDescription());
////            mCourseLongDescTextView.setText(course.getLongDescription());
////            mCoursePrereqsTextView.setText(course.getPrereqs());
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stop_detail, container, false);
        mRouteIdTextView = (TextView) view.findViewById(R.id.stop_route_id);
        mRouteNoTextView = (TextView) view.findViewById(R.id.stop_route_no);
        mRouteDestinationTextView = (TextView) view.findViewById(R.id.stop_route_dest);
        mDepartureTimeTextView = (TextView) view.findViewById(R.id.stop_route_depart_time);
        mMinutesLeftTextView = (TextView) view.findViewById(R.id.stop_route_mins_left);
        return view;
    }

    public void updateView(Stop stop) {
        if (stop != null) {
            mRouteIdTextView.setText("Stop ID: " + stop.getStopId());
            mRouteNoTextView.setText("Route No");
            mRouteDestinationTextView.setText("Route Destination");
            mDepartureTimeTextView.setText("Route Departure Time");
            mMinutesLeftTextView.setText("Route minutes left");
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below.
        Bundle args = getArguments();
        if (args != null) {
            // Set stop based on argument passed in
            updateView((Stop) args.getSerializable(STOP_SELECTED));
        }
    }

}
