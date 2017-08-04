package gupta.p.tvepisodes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import gupta.p.tvepisodes.R;
import gupta.p.tvepisodes.pojo.ShowNames_pojo;

/**
 * Created by pgupta on 11/7/17.
 */

public class MyShowNamesAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<ShowNames_pojo> arrayList;
    LayoutInflater layoutInflater;

    public MyShowNamesAdapter(Context context, int resource, ArrayList<ShowNames_pojo> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.context = context;
        this.resource = resource;
        this.arrayList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(resource, null);

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        TextView textViewYear = (TextView) view.findViewById(R.id.textViewYear);

        ShowNames_pojo pojo = arrayList.get(position);
        textViewYear.setText(pojo.getYear());
        textViewName.setText(pojo.getName());

        return view;
    }

}
