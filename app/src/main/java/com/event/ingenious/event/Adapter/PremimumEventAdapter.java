package com.event.ingenious.event.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.event.ingenious.event.Classes.Premimum_Event_Class;
import com.event.ingenious.event.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class PremimumEventAdapter extends RecyclerView.Adapter<PremimumEventAdapter.PEViewHolder>
{
    private Context context;
    private List<Premimum_Event_Class> premimum_event_classList;
    private String Value;

    public PremimumEventAdapter(Context context, List<Premimum_Event_Class> premimum_event_classList,String Value) {
        this.context = context;
        this.premimum_event_classList = premimum_event_classList;
        this.Value=Value;
    }

    @Override
    public PEViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_premimum_event, null);
        return new PEViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return premimum_event_classList.size();
    }

    class PEViewHolder extends RecyclerView.ViewHolder {
        CardView for_click;
        ImageView banner_image,feature_image;
        TextView ev_title, ev_category, ev_address, ev_date, ev_desc;

        public PEViewHolder(View itemView)
        {
            super(itemView);
            for_click = (CardView) itemView.findViewById(R.id.card_view);
            banner_image = (ImageView) itemView.findViewById(R.id.ev_banner_image);
            ev_title = (TextView) itemView.findViewById(R.id.ev_title);
            ev_category = (TextView) itemView.findViewById(R.id.ev_category);
            ev_address = (TextView) itemView.findViewById(R.id.ev_address);
            ev_date = (TextView) itemView.findViewById(R.id.ev_date);
            ev_desc = (TextView) itemView.findViewById(R.id.ev_desc);
            feature_image= itemView.findViewById(R.id.feature_image);
            if(Value.equals("Premium")){
                feature_image.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBindViewHolder(PEViewHolder holder , int position)
    {
        final Premimum_Event_Class premimumEventClass = premimum_event_classList.get(position);
        Glide.with(context)
                .load(premimumEventClass.getEv_image())
                .into(holder.banner_image);
        holder.ev_title.setText(premimumEventClass.getEv_title());
        holder.ev_category.setText(premimumEventClass.getEv_category());
        holder.ev_address.setText(premimumEventClass.getEv_address());
        holder.ev_date.setText(premimumEventClass.getEv_date());
        holder.ev_desc.setText(premimumEventClass.getEv_desc());
        holder.for_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.success(context,premimumEventClass.getEv_id(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
