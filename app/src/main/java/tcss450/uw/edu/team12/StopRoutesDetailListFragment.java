package tcss450.uw.edu.team12;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import tcss450.uw.edu.team12.model.Route;
import tcss450.uw.edu.team12.model.Stop;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class StopRoutesDetailListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static String STOP_ROUTES_URL = "http://cssgate.insttech.washington.edu/~ldimov/" +
            "rideontime/queries.php?cmd=routes&stop_id=";

    // Captures which stop was selected amongst stops
    public static String STOP_SELECTED;

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private List<Route> mRoutesList;

    // Selected stop
    private Stop mSelectedStop;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StopRoutesDetailListFragment() {
    }

//    // TODO: Customize parameter initialization
//    @SuppressWarnings("unused")
//    public static StopRoutesDetailListFragment newInstance(int columnCount) {
//        StopRoutesDetailListFragment fragment = new StopRoutesDetailListFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);

        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        Bundle args = getArguments();
//        if (args != null) {
//            mSelectedStop = (Stop) args.getSerializable(STOP_SELECTED);
//            STOP_ROUTES_URL.concat("&stop_id=" + mSelectedStop.getStopId());
//
//            Toast.makeText(getActivity().getApplicationContext(), STOP_ROUTES_URL, Toast.LENGTH_LONG)
//                    .show();
//
//        } else {
//            mSelectedStop = new Stop("9138", "NE Campus Pkwy & 12th Ave NE - Bay 4");
//            //STOP_ROUTES_URL.concat("&stop_id=9138");
//
//            Toast.makeText(getActivity().getApplicationContext(), STOP_ROUTES_URL, Toast.LENGTH_LONG)
//                    .show();
//        }
//        DownloadStopBusTimesTask task = new DownloadStopBusTimesTask();
//
//        task.execute(new String[]{STOP_ROUTES_URL});
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_list, container, false);

//        // Get selected stop id
//        mSelectedStop = getArguments().getString("stop_id");
        Bundle args = getArguments();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            if (args != null) {
                mSelectedStop = (Stop) args.getSerializable(STOP_SELECTED);
                STOP_ROUTES_URL = STOP_ROUTES_URL.concat("&stop_id=" + mSelectedStop.getStopId());

                Toast.makeText(getActivity().getApplicationContext(), STOP_ROUTES_URL, Toast.LENGTH_LONG)
                        .show();

            }
            DownloadStopBusTimesTask task = new DownloadStopBusTimesTask();
//            mSelectedStop = new Stop("9138", "NE Campus Pkwy & 12th Ave NE - Bay 4");
            task.execute(new String[]{STOP_ROUTES_URL});

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Route route);
    }

    /**
     * Download selected stop's bus times.
     */
    private class DownloadStopBusTimesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of bus times. Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            List<Route> routesList = new ArrayList<Route>();
            result = Route.parseCourseJSON(result, routesList);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of courses.
            if (!routesList.isEmpty()) {
//                mSelectedStop = new Stop("9138", "NE Campus Pkwy & 12th Ave NE - Bay 4");
//                Toast.makeText(getActivity().getApplicationContext(), mSelectedStop, Toast.LENGTH_LONG)
//                        .show();
                mRecyclerView.setAdapter(new MyStopRoutesRecyclerViewAdapter(routesList, mListener));
            }
        }
    }
}
