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
 * The RecyclerView adapter for a list of Route.
 *
 * {@link RecyclerView.Adapter} that can display a {@link Route} and makes a call to the
 * specified {@link StopRoutesDetailListFragment.OnListFragmentInteractionListener}.
 */
public class MyStopRoutesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Route> mValues;
    private final StopRoutesDetailListFragment.OnListFragmentInteractionListener mListener;
    private Stop mSelectedStop;
    private FavoriteStopsDB mFavStopsDB;

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
        mSelectedStop = new Stop(selectedStop.getStopId(),
                                 routes.get(0).getStopName());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }
        return TYPE_ROUTE_LIST;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_ROUTE_LIST) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_route, parent, false);
            return new MyStopRoutesRecyclerViewAdapter.RoutesViewHolder(view);
        } else if (viewType == TYPE_HEAD) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.head_layout, parent, false);
            return new HeaderViewHolder(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyStopRoutesRecyclerViewAdapter.RoutesViewHolder) {
            final MyStopRoutesRecyclerViewAdapter.RoutesViewHolder routesViewHolder =
                    (MyStopRoutesRecyclerViewAdapter.RoutesViewHolder) holder;
            routesViewHolder.mItem = mValues.get(position - 1);
            routesViewHolder.mIdView.setText(mValues.get(position - 1).getRouteName());

            routesViewHolder.mContentView.setText(mValues.get(position - 1).getDepartureTime());
            routesViewHolder.mContentView2.setText(mValues.get(position - 1).getTripHeadSign());

            routesViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(routesViewHolder.mItem);
                    }
                }
            });
        }

    }

    /**
     * Return the numer of items in the recyclerview adapter.
     * @return
     */
    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }


    /**
     * Sets the viewholder with stop information (i.e. stop name).
     */
    public class HeaderViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener,
                                          RecyclerView.OnLongClickListener {
        TextView txtTitle;
        private ImageView mOverflowIcon;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            this.txtTitle = (TextView)itemView.findViewById(R.id.stop_name_header);
            this.txtTitle.setText(mSelectedStop.getStopName().replace("\"", ""));

            mOverflowIcon = (ImageView) itemView.findViewById(R.id.route_head_context_menu);
            mOverflowIcon.setOnClickListener(this);
        }

        /**
         * Attempt to add the route to the users favorite stops.
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (v == mOverflowIcon) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                final Context ct = v.getContext();
                popup.inflate(R.menu.mfp_overflow_menu_file);



                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mFavStopsDB = new FavoriteStopsDB(ct);

                        boolean success = mFavStopsDB.insertStop(mSelectedStop.getStopId(),
                                                    mSelectedStop.getStopName().replace("\"", ""));

                        if (success) {
                            Toast.makeText(mOverflowIcon.getContext(), "Stop id " + mSelectedStop.getStopId() +
                                    " added to favorite stops.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mOverflowIcon.getContext(), "Stop" +
                                    " already exists in your list of favorite stops.", Toast.LENGTH_LONG).show();
                        }

                        return success;
                    }

                });
                popup.show();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
    /**
     * Sets the viewholder with Route information, such as ID, departure time, and the
     * route destination.
     */
    public class RoutesViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener,
            RecyclerView.OnLongClickListener {
        public View mView;
        public TextView mIdView;
        public TextView mContentView;
        public TextView mContentView2;
        public Route mItem;
        private ImageView mOverflowIcon;

        /**
         * Sets the TextView elements with the stop id, departure time, and destination.
         * @param view the view
         */
        public RoutesViewHolder(View view) {
            super(view);

            mView = view;
            mIdView = (TextView) view.findViewById(R.id.stop_route_id);
            mContentView = (TextView) view.findViewById(R.id.content_stop_route_depart_time);
            mContentView2 = (TextView) view.findViewById(R.id.content2_stop_route_dest);
            mOverflowIcon = (ImageView) view.findViewById(R.id.route_context_menu);
            mOverflowIcon.setOnClickListener(this);


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
                                mIdView.getText() + " from " + mSelectedStop.getStopName().replace("\"", "") +
                                ". It departs at " + mContentView.getText() +
                                " and goes to " + mContentView2.getText() + ".");
                        sendIntent.setType("text/plain");
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
