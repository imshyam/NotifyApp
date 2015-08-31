package com.shyam.notifyapp.Adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shyam.listview.R;
import com.shyam.notifyapp.Model.Story;

/**
 * Created by shyam on 2/7/15.
 */

public class ListAdapter extends BaseAdapter implements View.OnClickListener{


    boolean notifyMe = false;

    LinearLayout notifyButton;
    RelativeLayout notifyBackground, setColor;
    ImageView notifyImage;


    private Activity activity;
    private LayoutInflater inflater;
    private List<Story> storyItems;

    public ListAdapter(Activity activity, List<Story> storyItems) {
        this.activity = activity;
        this.storyItems = storyItems;
    }

    @Override
    public int getCount() {
        return storyItems.size();
    }

    @Override
    public Object getItem(int location) {
        return storyItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_card, null);


        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView link = (TextView) convertView.findViewById(R.id.link);
        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView linuxTime = (TextView) convertView.findViewById(R.id.time);
        TextView noOfUpvotes = (TextView) convertView.findViewById(R.id.noOfUpvotes);
        TextView noOfComments = (TextView) convertView.findViewById(R.id.noOfComments);

        notifyButton = (LinearLayout) convertView.findViewById(R.id.notifyClick);
        notifyBackground = (RelativeLayout) convertView.findViewById(R.id.notifyOrNotBackground);
        setColor = (RelativeLayout) convertView.findViewById(R.id.setColorHere);
        notifyImage = (ImageView) convertView.findViewById(R.id.notifyOrNotImage);

        notifyButton.setOnClickListener(this);

        // getting story data for the row
        Story story = storyItems.get(position);

        // title
        title.setText(story.getTitle());

        //link
        //get base link
        String url = story.getLink();
        // Create a Pattern object
        Pattern r = Pattern.compile("^http(s)?:(//)+");
        Matcher m = r.matcher(url);
        if (m.find( )) {
            url = url.substring(m.group(0).length());
            Log.e("Url without https : ",url);

        }
        r = Pattern.compile("^www.");
        m = r.matcher(url);
        if (m.find( )) {
            url = url.substring(m.group(0).length());
            Log.e("Url without www. : ",url);

        }
        String [] baseUrl = url.split("/");
        String finalUrl = baseUrl[0];
        Log.e("Final Url : ",finalUrl);

        link.setText(finalUrl);

        //username
        username.setText(story.getUsername());

        //Time
        //time of post
        long unixSeconds = story.getLinuxTime();
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:30")); // give a timezone reference for formating (see comment at the bottom
        String postDateandTime = sdf.format(date);
        //current time
        String currentDateandTime = sdf.format(new Date());
        //difference
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdf.parse(postDateandTime);
            d2 = sdf.parse(currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        if(diffHours>0) {
            linuxTime.setText(String.valueOf(diffHours)+" Hours ago");
        }
        else if(diffMinutes>0) {
            linuxTime.setText(String.valueOf(diffMinutes)+" Minutes ago");
        }
        else {
            linuxTime.setText(String.valueOf(diffSeconds)+" Seconds ago");
        }
        //noOfUpvotes
        noOfUpvotes.setText(String.valueOf(story.getNoOfUpVotes()));

        //noOfComments
        noOfComments.setText(String.valueOf(story.getNoOfComments()));


        return convertView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.notifyClick){
            if(notifyMe){
                notifyBackground.setBackgroundResource(R.drawable.circular_background);
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("notification", R.drawable.ic_notification);
                notifyImage.setImageResource(map.get("notification"));
                notifyMe = false;
            }
            else {
                notifyBackground.setBackgroundResource(R.drawable.circular_background_checked);
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("notify", R.drawable.ic_notify);
                notifyImage.setImageResource(map.get("notify"));
                notifyMe = true;
            }
        }
    }
}