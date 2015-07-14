package pavich.com.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pavich on 6/27/15.
 */
public class TopListViewAdapter extends ArrayAdapter<TrackItem> {

    private Context context;
    private int layoutResourceId;
    private List<TrackItem> trackItems;

    public TopListViewAdapter(Context context, int layoutResourceId, List<TrackItem> trackItems){
        super(context, layoutResourceId, trackItems);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.trackItems = trackItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TrackItem trackItem = getItem(position);


        convertView = LayoutInflater.from(getContext()).inflate(R.layout.track_item, parent, false);

        ImageView trackThumbnail = (ImageView) convertView.findViewById(R.id.track_thumbnail);
        TextView trackName = (TextView) convertView.findViewById(R.id.track_name);
        TextView trackAlbum = (TextView) convertView.findViewById(R.id.track_album);

        if(trackItem.getAlbumThumbnailSmall() != null) {
            Picasso.with(context).load(trackItem.getAlbumThumbnailSmall()).into(trackThumbnail);
        }
        else{
            Picasso.with(context).load(R.mipmap.ic_launcher).into(trackThumbnail);
        }
        trackName.setText(trackItem.getTrackName());
        trackAlbum.setText(trackItem.getAlbumName());

        return convertView;

    }
}
