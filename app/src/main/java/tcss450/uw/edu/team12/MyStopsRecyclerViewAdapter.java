package tcss450.uw.edu.team12;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import tcss450.uw.edu.team12.BusStopsListFragment.OnListFragmentInteractionListener;
import tcss450.uw.edu.team12.data.FavoriteStopsDB;
import tcss450.uw.edu.team12.model.Stop;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Stop} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyStopsRecyclerViewAdapter extends RecyclerView.Adapter<MyStopsRecyclerViewAdapter.ViewHolder> {

    private final List<Stop> mValues;
    private final OnListFragmentInteractionListener mListener;

    private FavoriteStopsDB mFavStopsDB;

    public MyStopsRecyclerViewAdapter(List<Stop> stops, OnListFragmentInteractionListener listener) {
        mValues = stops;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_stop, parent, false);
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

    /**
     * Sets the ViewHolder element with Stop information such as ID and
     * destination information.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener,
                                                            RecyclerView.OnLongClickListener

    {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;

        private ImageView mOverflowIcon;

        public Stop mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            mOverflowIcon = (ImageView) view.findViewById(R.id.mfp_context_menu);
            mOverflowIcon.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if (v == mOverflowIcon) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.mfp_overflow_menu_file);



                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        mFavStopsDB = new FavoriteStopsDB(mContentView.getContext()); // IS THIS THE RIGHT PLACE TO DO THAT?

                        boolean success = mFavStopsDB.insertStop(mItem.getStopId(), mItem.getStopName());

                        if (success) {
                            Toast.makeText(mOverflowIcon.getContext(), "Stop id " + mItem.getStopId() +
                                    " added to favorites.", Toast.LENGTH_LONG).show();
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

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
