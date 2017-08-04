package gupta.p.tvepisodes.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import gupta.p.tvepisodes.R;
import gupta.p.tvepisodes.adapter.MyShowNamesAdapter;
import gupta.p.tvepisodes.adapter.MyTVShowsAdapter;
import gupta.p.tvepisodes.hepler.UrlHelper;
import gupta.p.tvepisodes.pojo.ShowNames_pojo;
import gupta.p.tvepisodes.pojo.TVShows_pojo;

public class EpisodesList extends AppCompatActivity {

    private ListView listView;

    private ArrayList<TVShows_pojo> arrayList = new ArrayList<>();
    private ArrayList<ShowNames_pojo> arrayListShows =new ArrayList<>();

    private MyTVShowsAdapter myTVShowsAdapter;
    private MyShowNamesAdapter myShowNamesAdapter;
    public static String showName;
    private String ep,key,name,year;
    private JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes_list);

        showName = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(showName);

        year = getIntent().getStringExtra("year");

        Toast.makeText(this, "" + showName, Toast.LENGTH_SHORT).show();

        init();
        methodListener();
        fetchDataFromServer();
    }

    private void init() {
        listView = (ListView) findViewById(R.id.listView);
    }

    private void fetchDataFromServer() {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        func();

        StringRequest request =
                new StringRequest(Request.Method.GET, UrlHelper.TVSHOWS_URL+ep,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dialog.cancel();
                                parseShowsJSON(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.cancel();
                                Toast.makeText(EpisodesList.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    private void parseShowsJSON(String response) {
        try {
            //of in and the to a
             object = new JSONObject(response);
            Iterator iterator = object.keys();
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (key.equals("shows")) {
                    myShowNamesAdapter = new MyShowNamesAdapter(this, R.layout.list_item_shows, arrayListShows);
                    listView.setAdapter(myShowNamesAdapter);
                    JSONArray jsonArray = object.getJSONArray(key);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        ShowNames_pojo pojo = new ShowNames_pojo();
                        pojo.setName(object2.getString("name"));
                        pojo.setYear(object2.getString("year"));
                        arrayListShows.add(pojo);
                    }
                    myShowNamesAdapter.notifyDataSetChanged();
                }
                else {
                    myTVShowsAdapter = new MyTVShowsAdapter(this, R.layout.list_item, arrayList);
                    listView.setAdapter(myTVShowsAdapter);


                    JSONObject object2 = object.getJSONObject(key);

                    JSONArray jsonArray = object2.getJSONArray("episodes");


                    ArrayList<JSONObject> array = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            array.add(jsonArray.getJSONObject(i));
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Collections.sort(array, new Comparator<JSONObject>() {

                        @Override
                        public int compare(JSONObject lhs, JSONObject rhs) {
                            // TODO Auto-generated method stub

                            try {
                                String valA = "" + lhs.get("season");
                                String valB = "" + rhs.get("season");
                                int val1 = Integer.parseInt(valA);
                                int val2 = Integer.parseInt(valB);
                                if(val1>val2)
                                    return 1;
                                else if(val1<val2)
                                    return -1;
                                else {
                                    int numA = lhs.getInt("number");
                                    int numB = rhs.getInt("number");
                                    if(numA>numB)
                                        return 1;
                                    else if(numA<numB)
                                        return -1;
                                    else
                                        return 0;
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                                return 0;
                            }
                        }
                    });


                    for (int i = 0; i < array.size(); i++) {
                        JSONObject jsonObject = array.get(i);

                        TVShows_pojo pojo = new TVShows_pojo();

                        pojo.setSeason("S" + jsonObject.getString("season"));
                        pojo.setName(jsonObject.getString("name"));
                        pojo.setNumber("E" + jsonObject.getString("number"));

                        Log.d("1234", "parseShowsJSON: "+ pojo.getName());

                        arrayList.add(pojo);
                    }

                    myTVShowsAdapter.notifyDataSetChanged();
                }
                break;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void func() {
        if(year.equals(""))
        ep = showName.replaceAll(" ", "+");
        else {
            ep = showName.replaceAll(" ", "+");
            ep= ep + "&year=" + year;
        }
    }

    private void methodListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(key.equals("shows")) {
                    ShowNames_pojo pojo;
                    pojo = arrayListShows.get(position);
                    name = pojo.getName();
                    year = pojo.getYear();
                    Intent i= new Intent(EpisodesList.this,EpisodesList.class);
                    i.putExtra("name",name);
                    i.putExtra("year",year);
                    startActivity(i);
                }
            }
        });

    }

}
