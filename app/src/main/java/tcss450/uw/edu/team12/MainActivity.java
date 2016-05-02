package tcss450.uw.edu.team12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
