package pavich.com.spotifystreamer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pavich on 6/27/15.
 */
public class TrackItem implements Parcelable {

    private String trackName;
    private String albumName;
    private Uri albumThumbnailLarge;
    private Uri albumThumbnailSmall;
    private Uri addressUrl;

    public TrackItem(String trackName, String albumName, Uri albumThumbnailLarge, Uri albumThumbnailSmall, Uri addressUrl) {
        this.trackName = trackName;
        this.albumName = albumName;
        this.albumThumbnailLarge = albumThumbnailLarge;
        this.albumThumbnailSmall = albumThumbnailSmall;
        this.addressUrl = addressUrl;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Uri getAlbumThumbnailLarge() {
        return albumThumbnailLarge;
    }

    public void setAlbumThumbnailLarge(Uri albumThumbnailLarge) {
        this.albumThumbnailLarge = albumThumbnailLarge;
    }

    public Uri getAlbumThumbnailSmall() {
        return albumThumbnailSmall;
    }

    public void setAlbumThumbnailSmall(Uri albumThumbnailSmall) {
        this.albumThumbnailSmall = albumThumbnailSmall;
    }

    public Uri getAddressUrl() {
        return addressUrl;
    }

    public void setAddressUrl(Uri addressUrl) {
        this.addressUrl = addressUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
