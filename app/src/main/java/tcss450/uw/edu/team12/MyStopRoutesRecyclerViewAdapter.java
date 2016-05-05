package tcss450.uw.edu.team12;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import ldimov.tacoma.uw.edu.rideontime.ItemFragment.OnListFragmentInteractionListener;
//import ldimov.tacoma.uw.edu.rideontime.dummy.DummyContent.DummyItem;

import tcss450.uw.edu.team12.model.Route;
import tcss450.uw.edu.team12.model.Stop;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Route} and makes a call to the
 * specified {@link StopRoutesDetailListFragment.OnListFragmentInteractionListener}.
 */
public class MyStopRoutesRecyclerViewAdapter extends RecyclerView.Adapter<MyStopRoutesRecyclerViewAdapter.ViewHolder> {

    private final List<Route> mValues;
    private final StopRoutesDetailListFragment.OnListFragmentInteractionListener mListener;

    /**
     * Initializes a MyStopRoutesRecyclerViewAdapter.
     *
     * @param routes the list of routes.
     * @param listener a fragment interaction listener.
     */
    public MyStopRoutesRecyclerViewAdapter(List<Route> routes,
                                           StopRoutesDetailListFragment.OnListFragmentInteractionListener listener) {
        mValues = routes;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_route, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getRouteName());

        holder.mContentView.setText(mValues.get(position).getDepartureTime());
        holder.mContentView2.setText(mValues.get(position).getTripHeadSign());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Sets the viewholder with Route information, such as ID, departure time, and the
     * route destination.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mContentView2;
        public Route mItem;

        /**
         * Sets the TextView elements with the stop id, departure time, and destination.
         * @param view
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.stop_route_id);
            mContentView = (TextView) view.findViewById(R.id.content_stop_route_depart_time);
            mContentView2 = (TextView) view.findViewById(R.id.content2_stop_route_dest);
        }

        @Override
        public String toString() {
            return super.toString() + mIdView.getText() + mContentView.getText()
                    + mContentView2.getText();
        }
    }
}
