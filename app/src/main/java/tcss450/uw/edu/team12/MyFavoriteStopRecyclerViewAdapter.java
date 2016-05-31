package tcss450.uw.edu.team12;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import tcss450.uw.edu.team12.data.FavoriteStopsDB;
import tcss450.uw.edu.team12.model.Stop;

import java.util.List;

/**
 * The RecyclerView adapter for favorite stops.
 *
 * {@link RecyclerView.Adapter} that can display a {@link Stop} and makes a call to the
 * specified {@link FavoriteStopFragment.OnListFragmentInteractionListener}.
 */
public class MyFavoriteStopRecyclerViewAdapter extends RecyclerView.Adapter<MyFavoriteStopRecyclerViewAdapter.ViewHolder> {

    private List<Stop> mValues;
    private final FavoriteStopFragment.OnListFragmentInteractionListener mListener;
    private FavoriteStopsDB mFavStopsDB;

    // Context member variable
    private Context mContext;
    private List<Stop> mFavoriteStopList;

    public MyFavoriteStopRecyclerViewAdapter(List<Stop> items, FavoriteStopFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fav_stop, parent, false);

        // Get context
        mContext = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getStopId());
        holder.mContentView.setText(mValues.get(position).getStopName());

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

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener,
            RecyclerView.OnLongClickListener {

        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;

        private ImageView mOverflowIcon;
        public Stop mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id_fav);
            mContentView = (TextView) view.findViewById(R.id.content_fav);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            mOverflowIcon = (ImageView) view.findViewById(R.id.mfp_context_menu2);
            mOverflowIcon.setOnClickListener(this);

        }

        /**
         * Clicking the element will attempt to remove the stop from
         * the favorite stops SQLite database.
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (v == mOverflowIcon) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.mfp_overflow_menu_file2);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        mFavStopsDB = new FavoriteStopsDB(mContentView.getContext());


                        boolean success = mFavStopsDB.removeStop(mItem.getStopId());

                        if (success) {
                            Toast.makeText(mOverflowIcon.getContext(), "Stop id " + mItem.getStopId() +
                                    " was removed from favorite stops.", Toast.LENGTH_LONG).show();


                            // Save the changes to the dataset and notify adapter of the change
                            mFavStopsDB = new FavoriteStopsDB(mContext);
                            mFavoriteStopList = mFavStopsDB.getFavStops();
                            mValues = mFavoriteStopList;
                            notifyDataSetChanged();
                            mFavStopsDB.closeDB();
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

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}