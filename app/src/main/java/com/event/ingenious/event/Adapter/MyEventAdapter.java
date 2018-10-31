package com.event.ingenious.event.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.event.ingenious.event.Classes.MyEventClass;
import com.event.ingenious.event.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.MyEventViewHolder>
{
    private Context context;
    private List<MyEventClass> myEventClassList;

    public MyEventAdapter(Context context, List<MyEventClass> myEventClassList) {
        this.context = context;
        this.myEventClassList = myEventClassList;
    }

    @Override
    public MyEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_my_event, null);
        return new MyEventViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return myEventClassList.size();
    }

    class MyEventViewHolder extends RecyclerView.ViewHolder {
        CardView for_click;
        ImageView banner_image;
        TextView ev_title, ev_category, ev_address, ev_date;

        public MyEventViewHolder(View itemView)
        {
            super(itemView);
            for_click = (CardView) itemView.findViewById(R.id.card_view);
            banner_image = (ImageView) itemView.findViewById(R.id.event_image);
            ev_title = (TextView) itemView.findViewById(R.id.tv_event_name);
            ev_category = (TextView) itemView.findViewById(R.id.tv_event_cat);
            ev_address = (TextView) itemView.findViewById(R.id.tv_event_address);
            ev_date = (TextView) itemView.findViewById(R.id.ev_date);
        }
    }

    @Override
    public void onBindViewHolder(MyEventViewHolder holder , int position)
    {
        final MyEventClass myEventClass = myEventClassList.get(position);
        Glide.with(context)
                .load(myEventClass.getEv_image())
                .into(holder.banner_image);
        holder.ev_title.setText(myEventClass.getEv_title());
        holder.ev_category.setText(myEventClass.getEv_category());
        holder.ev_address.setText(myEventClass.getEv_address());
        holder.ev_date.setText(myEventClass.getEv_date());
        holder.for_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.success(context,myEventClass.getEv_id(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
