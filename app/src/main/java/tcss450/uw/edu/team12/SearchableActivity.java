package tcss450.uw.edu.team12;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import tcss450.uw.edu.team12.model.Route;
import tcss450.uw.edu.team12.model.Stop;

public class SearchableActivity extends AppCompatActivity implements StopRoutesDetailListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_searchable);
//        TextView txt = (TextView)findViewById(R.id.textView);

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
//            txt.setText("Searching by: " + query);
        }


//        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
//            String uri = intent.getDataString();
//            txt.setText("Suggestion: "+ uri);
//        }
    }

    @Override
    public void onListFragmentInteraction(Route route) {

    }
}
