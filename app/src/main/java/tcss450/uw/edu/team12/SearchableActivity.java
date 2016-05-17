package tcss450.uw.edu.team12;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import tcss450.uw.edu.team12.model.Route;
import tcss450.uw.edu.team12.model.Stop;

/**
 * Searches fragments for a User's desired stop.
 */
public class SearchableActivity extends AppCompatActivity implements StopRoutesDetailListFragment.OnListFragmentInteractionListener,
        SearchView.OnQueryTextListener {

    /**
     * Instantiate the SearchableActivity to find the routes selected in the
     * corresponding activity.
     *
     * @param savedInstanceState the saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        String query = "";
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);

            if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {

                StopRoutesDetailListFragment stopRoutesFragment = new StopRoutesDetailListFragment();
                Bundle args = new Bundle();
                Stop stop = new Stop(query, "");
                args.putSerializable(StopRoutesDetailListFragment.STOP_SELECTED, stop);
                stopRoutesFragment.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container2, stopRoutesFragment)
                        .commit();

            }
        }

    }

    /**
     * Resets the title of the activity to 'Enter stop ID...'
     */
    @Override
    public void onResume() {
        super.onResume();
        this.setTitle(getResources().getText(R.string.hint_search));

    }
    /**
     * Inflates the menu with a search activity.
     *
     * @param menu the menu to inflate.
     * @return true if setting the menu was successful.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.menu_bus, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, SearchableActivity.class)));
        searchView.setIconifiedByDefault(false);

        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        return false;
    }
    @Override
    public void onListFragmentInteraction(Route route) {

    }
}
