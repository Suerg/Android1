package com.example.android.sunshine.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    ShareActionProvider mShareActionProvider;
    String detailString;
    Intent mShareIntent;

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent startIntent = getActivity().getIntent();
        Activity act = getActivity();
        TextView textView = (TextView) rootView.findViewById(R.id.fragment_detail_weather);
        String text = startIntent.getStringExtra("FORECAST");
        textView.setText(text);
        detailString = text;


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItem = menu.findItem(R.id.action_share);
        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);


        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        String shareBody = detailString + "#SunshineApp";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

        setShareIntent(shareIntent);
        mShareIntent = shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_share)
            startActivity(Intent.createChooser(mShareIntent, "Share via"));

        return true;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
//
//        String[] forecastArray = {""};
//
//        if (mForecastAdapter == null) {
//            List<String> weekForecast = new ArrayList<String>(
//                                                                     Arrays.asList(forecastArray)
//            );
//            mForecastAdapter = new ArrayAdapter<String>(getActivity(),
//                                                               R.layout.list_item_forecast, R.id.list_item_forecast_textview,
//                                                               weekForecast);
//        }
//        ListView forecastListView = (ListView) rootView.findViewById(R.id.listview_forecast);
//        forecastListView.setAdapter(mForecastAdapter);
//        forecastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                Context context = view.getContext();
////                CharSequence text = mForecastAdapter.getItem(i).toString();
////                int duration = Toast.LENGTH_SHORT;
////
////                Toast toast = Toast.makeText(context, text, duration);
////                toast.show();
//
//                Intent detailIntent = new Intent(view.getContext(), DetailActivity.class);
//                detailIntent.putExtra("forecast", mForecastAdapter.getItem(i).toString());
//                startActivity(detailIntent);
//            }
//        });
//
//        return rootView;
//    }
}
