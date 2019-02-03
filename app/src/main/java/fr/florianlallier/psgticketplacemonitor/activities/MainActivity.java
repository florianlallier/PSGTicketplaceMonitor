package fr.florianlallier.psgticketplacemonitor.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.florianlallier.psgticketplacemonitor.R;
import fr.florianlallier.psgticketplacemonitor.adapters.MatchAdapter;
import fr.florianlallier.psgticketplacemonitor.models.Match;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MATCH = "fr.florianlallier.psgticketplacemonitor.extras.MATCH";

    private ListView listView;

    private ArrayList<Match> matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.refresh, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                refresh();
            }
        });

        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Match match = matches.get(position);
                Log.i(getString(R.string.app_name), "Match against " + match.getOpponent() + " selected.");

                // Start MatchActivity
                Intent matchActivity = new Intent(MainActivity.this, MatchActivity.class);
                matchActivity.putExtra(EXTRA_MATCH, match);
                startActivity(matchActivity);
            }
        });

        matches = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        // Get all matches
        GetMatchesTask task = new GetMatchesTask();

        task.execute();
        try {
            // Wait for the computation to complete
            task.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Populate the listView
        MatchAdapter matchAdapter = new MatchAdapter(this, matches);
        listView.setAdapter(matchAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    private class GetMatchesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            matches = Match.getMatches();

            Log.i(getString(R.string.app_name), matches.size() + " matche(s) found.");

            return null;
        }
    }
}
