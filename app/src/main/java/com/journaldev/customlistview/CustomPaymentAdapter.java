package com.journaldev.customlistview;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class CustomPaymentAdapter extends ArrayAdapter<Payment> implements View.OnClickListener{

    private ArrayList<Payment> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView textView_list_item_title;
        TextView textView_list_item_payment_type;
        TextView textView_list_item_location;
        TextView textView_list_item_status;
        ImageView imageView_list_item_company_logo;
        Button bookmark_button;
        TextView days_textView;
        Button bid_button;

    }

    public CustomPaymentAdapter(ArrayList<Payment> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Payment dataModel = (Payment) object;
        Log.i("List Item Click","Inside List onClick");
        switch (v.getId()) {
            case R.id.list_item_company_logo:
                Snackbar.make(v, "Status : " +dataModel.getStatus(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        notifyDataSetChanged();
        Log.i("List Item Click 2","Inside List onClick 2");
        // Get the data item for this position
        Payment dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);

            viewHolder.textView_list_item_title = (TextView) convertView.findViewById(R.id.list_item_payment_title);
            viewHolder.textView_list_item_payment_type = (TextView) convertView.findViewById(R.id.list_item_payment_type);
            viewHolder.textView_list_item_location = (TextView) convertView.findViewById(R.id.list_item_location);
            viewHolder.textView_list_item_status = (TextView) convertView.findViewById(R.id.list_item_status);
            viewHolder.imageView_list_item_company_logo = (ImageView) convertView.findViewById(R.id.list_item_company_logo);
            viewHolder.bookmark_button = (Button) convertView.findViewById(R.id.bookmark_button);
            viewHolder.days_textView = (TextView) convertView.findViewById(R.id.days_textView);
            viewHolder.bid_button = (Button) convertView.findViewById(R.id.bid_button);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.textView_list_item_title.setText(dataModel.getTitle());
        viewHolder.textView_list_item_payment_type.setText(dataModel.getPaymentType());
        viewHolder.textView_list_item_status.setText(dataModel.getStatus());
        viewHolder.textView_list_item_location.setText(dataModel.getLocation());
        viewHolder.imageView_list_item_company_logo.setOnClickListener(this);
        viewHolder.imageView_list_item_company_logo.setTag(position);

        DateTime jodaTime = DateTime.parse(dataModel.getDate());
        DateTimeFormatter resultFormat = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");

        long time = jodaTime.getMillis();
        long now = System.currentTimeMillis();
        CharSequence relativeTimeStr = DateUtils.getRelativeTimeSpanString(time,
                now, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);

        viewHolder.days_textView.setText(String.valueOf(relativeTimeStr));

        viewHolder.bookmark_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Bookmark payment list item on click... To Do

            }
        });

        viewHolder.bid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Bid on payment list item on click... To Do

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }


}
