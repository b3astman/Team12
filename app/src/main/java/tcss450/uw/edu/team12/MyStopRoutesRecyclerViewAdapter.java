package tcss450.uw.edu.team12;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

//import ldimov.tacoma.uw.edu.rideontime.ItemFragment.OnListFragmentInteractionListener;
//import ldimov.tacoma.uw.edu.rideontime.dummy.DummyContent.DummyItem;

import tcss450.uw.edu.team12.data.FavoriteStopsDB;
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
    // Selected stop
    private Stop mSelectedStop;
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_ROUTE_LIST = 1;

    /**
     * Initializes a MyStopRoutesRecyclerViewAdapter.
     *
     * @param routes the list of routes.
     * @param listener a fragment interaction listener.
     * @param selectedStop the selected stop.
     */
    public MyStopRoutesRecyclerViewAdapter(List<Route> routes,
                                           StopRoutesDetailListFragment.OnListFragmentInteractionListener listener,
                                           Stop selectedStop) {
        mValues = routes;
        mListener = listener;
        mSelectedStop = selectedStop;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }
        return TYPE_ROUTE_LIST;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_ROUTE_LIST) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_route, parent, false);
            return new ViewHolder(view, viewType);
        } else if (viewType == TYPE_HEAD) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.head_layout, parent, false);
            return new ViewHolder(view, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        if (holder.view_type == TYPE_ROUTE_LIST) {
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
// else if (holder.view_type == TYPE_HEAD) {
//            holder.mStopName.setText("Bus stop name");
//        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Sets the viewholder with Route information, such as ID, departure time, and the
     * route destination.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener,
            RecyclerView.OnLongClickListener {
        public View mView;
        public TextView mIdView;
        public TextView mContentView;
        public TextView mContentView2;
        public Route mItem;
        private ImageView mOverflowIcon;

        int view_type;

        // header
        TextView mStopName;

        /**
         * Sets the TextView elements with the stop id, departure time, and destination.
         * @param view
         */
        public ViewHolder(View view, int viewType) {
            super(view);

            if (viewType == TYPE_ROUTE_LIST) {
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.stop_route_id);
                mContentView = (TextView) view.findViewById(R.id.content_stop_route_depart_time);
                mContentView2 = (TextView) view.findViewById(R.id.content2_stop_route_dest);
                mOverflowIcon = (ImageView) view.findViewById(R.id.route_context_menu);
                mOverflowIcon.setOnClickListener(this);
                view_type = 1;
            } else if (viewType == TYPE_HEAD) {
                mStopName = (TextView) view.findViewById(R.id.stop_name);
                mStopName.setText(mSelectedStop.getStopName());
                view_type = 0;
            }


        }

        @Override
        public void onClick(View v) {
            if (v == mOverflowIcon) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                final Context ct = v.getContext();
                popup.inflate(R.menu.mfp_overflow_menu_file3);



                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "I'm catching the " +
                                mIdView.getText() + " from " + mSelectedStop.getStopName() +
                                ". It departs at " + mContentView.getText() +
                                " and goes to " + mContentView2.getText() + ".");
                        sendIntent.setType("text/plain");
//                        startActivity(sendIntent);
                        ct.startActivity(sendIntent);
                        return true;
                    }

                });
                popup.show();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        @Override
        public String toString() {
            return super.toString() + mIdView.getText() + mContentView.getText()
                    + mContentView2.getText();
        }
    }
}
