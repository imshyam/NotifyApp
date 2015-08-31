package com.shyam.notifyapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shyam.listview.R;
import com.shyam.notifyapp.Adapter.ListAdapter;
import com.shyam.notifyapp.Constants.Routes;
import com.shyam.notifyapp.Controller.AppController;
import com.shyam.notifyapp.Model.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shyam on 29/6/15.
 */

public class MainFragment extends Fragment {

    ProgressDialog progressDialog;
    // json array response url
    private String urlTopStories = Routes.base+Routes.top+Routes.pretty;

    List<Integer> topIdList= new ArrayList<Integer>();
    String TAG = "Debug Log : ";

    private List<Story> storyList = new ArrayList<Story>();
    private ListView listView;
    private ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_main,container,false);


        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        adapter = new ListAdapter(getActivity(), storyList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Log.e("Clicked to : ", "cl");
            }
        });
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        showpDialog();

        // Creating volley request obj

        JsonArrayRequest storyReq = new JsonArrayRequest(urlTopStories,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < 15; i++) {
                            try {

                                int id = response.getInt(i);
                                topIdList.add(id);
                                Log.e("Responce is: ", String.valueOf(id));
//                                story.setTitle(obj.getString("title"));
//                                story.setThumbnailUrl(obj.getString("image"));
//                                story.setRating(((Number) obj.get("rating"))
//                                        .doubleValue());
//                                story.setYear(obj.getInt("releaseYear"));
//
//                                // Genre is json array


                                String url = Routes.base + Routes.story + String.valueOf(id) + Routes.pretty;

                                Log.e("Url is : ",url);

                                showpDialog();

                                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                        url, null,
                                        new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                hidepDialog();
                                                Log.d(TAG + " ", response.toString());
                                                try {
                                                    Story story = new Story();
                                                    story.setTitle(response.getString("title"));
                                                    story.setLink(response.getString("url"));
                                                    story.setNoOfUpVotes(Integer.parseInt(response.getString("score")));
                                                    story.setUsername(response.getString("by"));
                                                    story.setTitle(response.getString("title"));
                                                    story.setLinuxTime(Integer.parseInt(response.getString("time")));
                                                    story.setNoOfComments(Integer.parseInt(response.getString("descendants")));
                                                    story.setNotify(true);
                                                    // adding story to storys array
                                                    storyList.add(story);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                adapter.notifyDataSetChanged();
                                            }
                                        }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                                        // hide the progress dialog
                                        hidepDialog();
                                    }
                                });

                                    // Adding request to request queue
                                    AppController.getInstance().addToRequestQueue(jsonObjReq);



//                                JSONArray genreArry = obj.getJSONArray("genre");
//                                ArrayList<String> genre = new ArrayList<String>();
//                                for (int j = 0; j < genreArry.length(); j++) {
//                                    genre.add((String) genreArry.get(j));
//                                }
//                                story.setGenre(genre);
//

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidepDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(storyReq);


        return rootView;

    }

    private void showpDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hidepDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
