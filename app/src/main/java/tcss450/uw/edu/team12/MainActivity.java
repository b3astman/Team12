package tcss450.uw.edu.team12;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
                                                    StopRoutesDetailListFragment.OnListFragmentInteractionListener {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.menu_bus, menu);
        return super.onCreateOptionsMenu(menu);
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
