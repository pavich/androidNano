package pavich.com.spotifystreamer;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopFragment extends Fragment {

    private ArrayAdapter<TrackItem> topAdapter;

    private String artistId;

    private ListView listView;

    private List<TrackItem> lstTrackItem;

    private String artistName;

    private static final String LOG_TAG = TopFragment.class.getName();

    public TopFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_top, container, false);
        topAdapter = new TopListViewAdapter(getActivity(), R.layout.track_item, new ArrayList<TrackItem>());
        listView = (ListView) rootView.findViewById(R.id.listview_top);
        listView.setAdapter(topAdapter);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            lstTrackItem = savedInstanceState.getParcelableArrayList(String.valueOf(R.string.TOP_CONSTANT));
            artistName = savedInstanceState.getString(String.valueOf(R.string.ARTIST_NAME_CONSTANT));
            topAdapter.clear();
            topAdapter.addAll(lstTrackItem);
        } else {
            // Probably initialize members with default values for a new instance
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                Bundle bundle = intent.getExtras();
                artistId = bundle.getString(Intent.EXTRA_TEXT);
                FetchContentTask fetchContentTask = new FetchContentTask();
                fetchContentTask.execute(artistId);
                if (intent.hasExtra(Intent.EXTRA_TITLE)) {
                    artistName = bundle.getString(Intent.EXTRA_TITLE);
                }
            }
        }
        getActivity().getActionBar().setSubtitle(artistName);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(String.valueOf(R.string.TOP_CONSTANT), new ArrayList<Parcelable>(lstTrackItem));
        savedInstanceState.putString(String.valueOf(R.string.ARTIST_NAME_CONSTANT), artistName);
        super.onSaveInstanceState(savedInstanceState);
    }

    public class FetchContentTask extends AsyncTask<String, Void, List<TrackItem>> {


        @Override
        protected List<TrackItem> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }


            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            Map<String, Object> options = new HashMap<String, Object>();
            options.put(SpotifyService.COUNTRY, "EC");
            Tracks results = spotify.getArtistTopTrack(params[0], options);
            List<TrackItem> trackItems = new ArrayList<>();
            for (Track track : results.tracks) {
                String trackName = track.name;
                String albumName = null;
                Uri albumArtThumbnailLarge = null;
                Uri albumArtThumbnailSmall = null;
                Uri previewUrl = null;
                if (track.album != null) {
                    albumName = track.album.name;
                    if (track.album.images != null && track.album.images.size() > 0) {
                        albumArtThumbnailLarge = Uri.parse(track.album.images.get(0).url).buildUpon().build();
                        if (track.album.images.size() > 2) {
                            albumArtThumbnailSmall = Uri.parse(track.album.images.get(2).url).buildUpon().build();
                        } else {
                            albumArtThumbnailSmall = albumArtThumbnailLarge;
                        }
                    }
                }

                previewUrl = Uri.parse(track.preview_url).buildUpon().build();
                TrackItem trackItem = new TrackItem(trackName, albumName, albumArtThumbnailLarge, albumArtThumbnailSmall, previewUrl);
                trackItems.add(trackItem);
            }
            return trackItems;
        }

        @Override
        protected void onPostExecute(List<TrackItem> result) {
            if (result != null && result.size() > 0) {
                lstTrackItem = result;
                topAdapter.clear();
                topAdapter.addAll(result);
            } else {
                Toast.makeText(getActivity(), getString(R.string.no_tracks_found_message), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
