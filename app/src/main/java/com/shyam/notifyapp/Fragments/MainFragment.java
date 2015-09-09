package com.shyam.notifyapp.Fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rey.material.widget.ProgressView;
import com.shyam.listview.R;
import com.shyam.notifyapp.Activities.ArticleView;
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

    public final static String LINK = "com.shyam.notifyapp.LINK";
    public final static String MESSAGE = "com.shyam.notifyapp.MESSAGE";
    ProgressView progressView;
    // json array response url
    private String urlTopStories = Routes.base+Routes.top+Routes.pretty;

    List<Integer> topIdList= new ArrayList<Integer>();
    String TAG = "Debug Log : ";

    private List<Story> storyList = new ArrayList<Story>();
    private ListView listView;
    private ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_main, container, false);


        listView = (ListView) rootView.findViewById(R.id.list);
        progressView = (ProgressView) rootView.findViewById(R.id.progress);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        adapter = new ListAdapter(getActivity(), storyList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Story s = (Story) adapter.getItem(position);
                Log.e("Clicked to : ", s.getLink());
                Intent intent = new Intent(getActivity(), ArticleView.class);
                intent.putExtra(LINK, s.getLink());
                intent.putExtra(MESSAGE, s.getTitle());
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(getActivity(), view, "hello");
                ActivityCompat.startActivity(getActivity(), intent,  options.toBundle());
            }
        });
        showpDialog();

        // Creating volley request obj
        //get Top Stories

        JsonArrayRequest storyReq = new JsonArrayRequest(urlTopStories,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < 15; i++) {
                            try {

                                final int id = response.getInt(i);
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
                                                    story.setId(id);
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
        listView.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
        progressView.start();
    }

    private void hidepDialog() {
        progressView.stop();
        listView.setVisibility(View.VISIBLE);
        progressView.setVisibility(View.GONE);
    }

}
