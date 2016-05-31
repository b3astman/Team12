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

import tcss450.uw.edu.team12.data.FavoriteStopsDB;
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
public class FavoriteStopFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private RecyclerView mRecyclerView;
    private List<Stop> mFavoriteStopList;
    private FavoriteStopsDB mFavoriteStopsDB;
    private FavoriteStopFragment.OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteStopFragment() {
    }

    public static FavoriteStopFragment newInstance(int columnCount) {
        FavoriteStopFragment fragment = new FavoriteStopFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_stop_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            DownloadFavoriteStops task = new DownloadFavoriteStops();
            task.execute(new String[]{""});
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FavoriteStopFragment.OnListFragmentInteractionListener) {
            mListener = (FavoriteStopFragment.OnListFragmentInteractionListener) context;
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
     * Sets the title of the fragment's activity to 'Favorite Stops'
     */
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getText(R.string.fav_stops));

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
        void onListFragmentInteraction(Stop stop);
    }

    /**
     * Class that handles creating / downloading the favorite stops.
     */
    private class DownloadFavoriteStops extends AsyncTask<String, Void, String> {

        /**
         * Get the favorite stops the user has saved.
         *
         * @param urls
         * @return
         */
        @Override
        protected String doInBackground(String... urls) {
            mFavoriteStopsDB = new FavoriteStopsDB(getContext());
            mFavoriteStopList = mFavoriteStopsDB.getFavStops();
            mFavoriteStopsDB.closeDB();
            return "";
        }

        /**
         * Handle the display of the favorite stops. If there are no stops, display a toast.
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            if (!mFavoriteStopList.isEmpty()) {
                mRecyclerView.setAdapter(new MyFavoriteStopRecyclerViewAdapter(mFavoriteStopList, mListener));
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "You don't have any favorite stops.", Toast.LENGTH_LONG)
                        .show();
            }

        }
    }
}