package tcss450.uw.edu.team12;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import tcss450.uw.edu.team12.authenticate.SignInActivity;
import tcss450.uw.edu.team12.model.Route;
import tcss450.uw.edu.team12.model.Stop;

public class MainActivity extends AppCompatActivity implements BusStopsListFragment.OnListFragmentInteractionListener,
                                                    StopRoutesDetailListFragment.OnListFragmentInteractionListener,
        SearchView.OnQueryTextListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



        if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            BusStopsListFragment stopsListFragment = new BusStopsListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, stopsListFragment)
                    .commit();

        }

    }

//    public void setActionBarTitle(String title) {
//        getSupportActionBar().setTitle(title);
//    }
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

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            Toast.makeText(this, "Searching by: " + query, Toast.LENGTH_SHORT).show();
//        }
////        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
////            String uri = intent.getDataString();
////            Toast.makeText(this, "Suggestion: "+ uri, Toast.LENGTH_SHORT).show();
////        }
//    }

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
                Toast.makeText(MainActivity.this, "You have logged out logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_favorites:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onListFragmentInteraction(Stop stop) {
//        StopDetailFragment stopDetailFragment = new StopDetailFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(StopDetailFragment.STOP_SELECTED, stop);
//        stopDetailFragment.setArguments(args);
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, stopDetailFragment)
//                .addToBackStack(null)
//                .commit();
//
//    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v,
//                                    ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_bus, menu);
//    }

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
