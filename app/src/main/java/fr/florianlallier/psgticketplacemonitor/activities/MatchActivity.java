package fr.florianlallier.psgticketplacemonitor.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.MessageFormat;

import fr.florianlallier.psgticketplacemonitor.R;
import fr.florianlallier.psgticketplacemonitor.models.Match;

public class MatchActivity extends AppCompatActivity {

    private Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        TextView opponent = findViewById(R.id.opponent);
        TextView date = findViewById(R.id.date);
        TextView price = findViewById(R.id.price);
        Button button = findViewById(R.id.button);

        // Get the intent that started this activity and extract the match
        Intent intent = getIntent();
        match = (Match) intent.getSerializableExtra(MainActivity.EXTRA_MATCH);

        opponent.setText(match.getOpponent());
        date.setText(match.getDate());
        price.setText(MessageFormat.format("{0} €", match.getPrice()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ticketplace.psg.fr/fr/recherche-place/" + match.getId()));
                startActivity(browserIntent);
            }
        });
    }
}
