package pavich.com.spotifystreamer;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    private ListViewAdapter artistAdapter;

    private List<RowItem> itemsArtists;

    private static final String LOG_TAG = SearchFragment.class.getName();

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);


        artistAdapter = new ListViewAdapter(getActivity(), R.layout.list_item, new ArrayList<RowItem>());

        ListView listView = (ListView) rootView.findViewById(R.id.listview_artist);

        listView.setAdapter(artistAdapter);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            itemsArtists = savedInstanceState.getParcelableArrayList(String.valueOf(R.string.ARTIST_QUERY_RESULT_CONSTANT));
            artistAdapter.clear();
            artistAdapter.addAll(itemsArtists);
        }

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        RowItem rowItem = artistAdapter.getItem(position);
                        Intent intent = new Intent(getActivity(), TopActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT, rowItem.getId());
                        intent.putExtra(Intent.EXTRA_TITLE, rowItem.getName());
                        startActivity(intent);
                    }
                }
        );

        final SearchView searchView = (SearchView) rootView.findViewById(R.id.search_field);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                                              // for implementing typing delay in searchview
                                              Timer timer = new Timer();
                                              final long DELAY = 1500; // in ms

                                              /**
                                               * Executed when the user submits the query.
                                               * @param query the query text that is to be submitted
                                               * @return true if the query has been handled by the listener, false to let the SearchView perform the default action.
                                               */
                                              @Override
                                              public boolean onQueryTextSubmit(String query) {
                                                  searchView.clearFocus();
                                                  updateArtistList(query.toString());
                                                  return true;
                                              }

                                              /**
                                               * Executed when the query text is changed by the user.
                                               * @param newText the new content of the query text field.
                                               * @return false if the SearchView should perform the default action of showing any suggestions if available, true if the action was handled by the listener.
                                               */
                                              @Override
                                              public boolean onQueryTextChange(final String newText) {
                                                  // TODO timed change
                                                  // If text is changed, timer is cancelled
                                                  timer.cancel();
                                                  //instance of a new Timer
                                                  timer = new Timer();
                                                  // reset timer
                                                  timer.schedule(new TimerTask() {
                                                      @Override
                                                      public void run() {
                                                          updateArtistList(newText.toString());
                                                      }
                                                  }, DELAY);
                                                  return false;

                                              }
                                          }
        );

        return rootView;
    }

    private void updateArtistList(String searchCriteria) {

        if (searchCriteria != null && !searchCriteria.isEmpty()) {
            FetchContentTask fetchContentTask = new FetchContentTask();
            fetchContentTask.execute(searchCriteria);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(String.valueOf(R.string.ARTIST_QUERY_RESULT_CONSTANT), new ArrayList<Parcelable>(itemsArtists));
        super.onSaveInstanceState(outState);
    }

    public class FetchContentTask extends AsyncTask<String, Void, List<RowItem>> {


        @Override
        protected List<RowItem> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists(params[0]);
            Log.i(LOG_TAG, results.toString());
            List<RowItem> rowItems = new ArrayList<>();
            for (Artist artist : results.artists.items) {
                String id = artist.id;
                String name = artist.name;
                Uri thumbnail = null;
                if (artist.images.size() > 0) {
                    thumbnail = Uri.parse(artist.images.get(0).url).buildUpon().build();
                }
                RowItem rowItem = new RowItem(thumbnail, name, id);
                rowItems.add(rowItem);
            }
            return rowItems;
        }

        // Invoked on the UI thread after the background computation finishes
        @Override
        protected void onPostExecute(List<RowItem> result) {
            itemsArtists = result;
            artistAdapter.clear();
            if (result != null && result.size() > 0) {
                artistAdapter.addAll(result);
            } else {
                Toast.makeText(getActivity(), getString(R.string.no_artist_found_message), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
