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
public class ListViewAdapter extends ArrayAdapter<RowItem> {

    private Context context;
    private int layoutId;
    private List<RowItem> rowItems;

    public ListViewAdapter(Context context, int layoutId, List<RowItem> rowItems){
        super(context, layoutId, rowItems);
        this.layoutId = layoutId;
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RowItem rowItem = getItem(position);

        if(convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView name = (TextView) convertView.findViewById(R.id.name);

        if(rowItem.getThumbnail() != null) {
            Picasso.with(context).load(rowItem.getThumbnail()).into(icon);
        }
        else{
            Picasso.with(context).load(R.mipmap.ic_launcher).into(icon);
        }
        name.setText(rowItem.getName());

        return convertView;

    }
}
