package fr.florianlallier.psgticketplacemonitor.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.ArrayList;

import fr.florianlallier.psgticketplacemonitor.R;
import fr.florianlallier.psgticketplacemonitor.models.Match;

public class MatchAdapter extends ArrayAdapter<Match> {

    public MatchAdapter(Context context, ArrayList<Match> matches) {
        super(context, 0, matches);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_match, parent, false);
        }

        MatchViewHolder matchViewHolder = (MatchViewHolder) convertView.getTag();

        if (matchViewHolder == null) {
            matchViewHolder = new MatchViewHolder();

            // Lookup view for data population
            matchViewHolder.opponent = convertView.findViewById(R.id.opponent);
            matchViewHolder.date = convertView.findViewById(R.id.date);
            matchViewHolder.price = convertView.findViewById(R.id.price);

            // Cache the matchViewHolder object inside the view
            convertView.setTag(matchViewHolder);
        }

        // Get the data item for this position
        Match match = getItem(position);

        if (match != null) {
            // Populate the data from the data object via the matchViewHolder object into the template view
            matchViewHolder.opponent.setText(MessageFormat.format("Paris Saint-Germain - {0}", match.getOpponent()));
            matchViewHolder.date.setText(match.getDate());
            matchViewHolder.price.setText(MessageFormat.format("{0} €", match.getPrice()));
        }

        // Return the completed view to render on screen
        return convertView;
    }

    private class MatchViewHolder {

        public TextView opponent;
        public TextView date;
        public TextView price;
    }
}
