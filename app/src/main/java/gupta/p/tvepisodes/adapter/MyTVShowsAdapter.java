package gupta.p.tvepisodes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import gupta.p.tvepisodes.R;
import gupta.p.tvepisodes.pojo.TVShows_pojo;

/**
 * Created by pgupta on 3/7/17.
 */

public class MyTVShowsAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<TVShows_pojo> arrayList;
    LayoutInflater layoutInflater;

    public MyTVShowsAdapter(Context context, int resource, ArrayList<TVShows_pojo> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.context = context;
        this.resource = resource;
        this.arrayList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(resource, null);

        TextView textViewSeason = (TextView) view.findViewById(R.id.txtViewSeason);
        TextView textViewNumber = (TextView) view.findViewById(R.id.txtViewNumber);
        TextView textViewName = (TextView) view.findViewById(R.id.txtViewName);

        TVShows_pojo pojo = arrayList.get(position);
        textViewSeason.setText(pojo.getSeason());
        textViewNumber.setText(pojo.getNumber());
        textViewName.setText(pojo.getName());

        return view;
    }

}
