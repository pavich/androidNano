package pavich.com.spotifystreamer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pavich on 6/27/15.
 */
public class RowItem implements Parcelable {
    private Uri thumbnail;
    private String name;
    private String id;

    public RowItem(Uri thumbnail, String name, String id) {
        this.thumbnail = thumbnail;
        this.name = name;
        this.id = id;
    }

    public Uri getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Uri thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
