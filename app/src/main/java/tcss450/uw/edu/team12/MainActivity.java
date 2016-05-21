package tcss450.uw.edu.team12;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;

import tcss450.uw.edu.team12.authenticate.SignInActivity;
import tcss450.uw.edu.team12.model.Route;
import tcss450.uw.edu.team12.model.Stop;

/**
 * The main activity which holds lists of bus routes or stops.
 */
public class MainActivity extends AppCompatActivity implements BusStopsListFragment.OnListFragmentInteractionListener,
                                                    StopRoutesDetailListFragment.OnListFragmentInteractionListener,
                                                    FavoriteStopFragment.OnListFragmentInteractionListener,
                                                    SearchView.OnQueryTextListener {

    public static final String LOGGED_OUT = "You have logged out";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            BusStopsListFragment stopsListFragment = new BusStopsListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, stopsListFragment)
                    .commit();
        }

    }

    /**
     * Sets the menu with the search service.
     *
     * @param menu
     * @return
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
        // Users pressed the search button
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Users changed the text
        return false;
    }

    /**
     * Sets options for logout, favorites, and updates menu items when selected.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                    SharedPreferences sharedPreferences =
                            getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                            .commit();

                    Intent i = new Intent(this, SignInActivity.class);
                    startActivity(i);
                    finish();
                Toast.makeText(MainActivity.this, LOGGED_OUT, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_favorites:
                FavoriteStopFragment favoriteFragment = new FavoriteStopFragment();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, favoriteFragment)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.alerts_updates:
                Intent in = new Intent(this, TransitAlertsActivity.class);
                startActivity(in);
//                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Replaces the fragment with the stop selected.
     *
     * @param stop
     */
    @Override
    public void onListFragmentInteraction(Stop stop) {
        StopRoutesDetailListFragment stopRoutesFragment = new StopRoutesDetailListFragment();
        Bundle args = new Bundle();
        args.putSerializable(StopRoutesDetailListFragment.STOP_SELECTED, stop);
        stopRoutesFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, stopRoutesFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onListFragmentInteraction(Route route) {

    }
}
