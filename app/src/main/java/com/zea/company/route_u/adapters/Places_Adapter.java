package com.zea.company.route_u.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zea.company.route_u.BuildConfig;
import com.zea.company.route_u.R;
import com.zea.company.route_u.model.PlacePhoto;
import com.zea.company.route_u.model.results;

import java.util.List;

import timber.log.Timber;

public class Places_Adapter extends RecyclerView.Adapter<Places_Adapter.ViewHolder> {

    private List<results> results;
    List<PlacePhoto> photoList;
    private Context context;

    public RSClickListener rsClick;
    public RSClickLongListener rsLongClick;

    private String uriFirst = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photo_reference=";
    private String uriSecond = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photo_reference=";

    public Places_Adapter(List<com.zea.company.route_u.model.results> results, Context context, RSClickListener rs, RSClickLongListener rslong) {
        this.results = results;
        this.context = context;
        this.rsClick = rs;
        this.rsLongClick = rslong;
    }

    public interface RSClickListener{
        void selectedPlace(results rs);
    }

    public interface RSClickLongListener{
        void selectedDescriptionPlace(results rs);
    }

    @NonNull
    @Override
    public Places_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_map,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Places_Adapter.ViewHolder holder, int position) {
        Timber.d("QUE HAY: %s", results.get(position));
        Timber.d("QUE ES4: %s", results.get(position).getGeometry());
        if(results.get(position).getPhotos()!=null){
            photoList = results.get(position).getPhotos();
            uriFirst = uriFirst + photoList.get(0).getPhoto_reference() + "&key=" + BuildConfig.MAPS_API_KEY;
            Timber.d("VEMOS: "+uriFirst);
            Glide.with(context).load(uriFirst).into(holder.imageView);
            holder.textView.setText(results.get(position).getName());
        }else {
            Timber.d("NULO");
            Glide.with(context).load(R.drawable.not_found).into(holder.imageView);
            holder.textView.setText(results.get(position).getName());
            holder.textView.setTextColor(Color.BLACK);
        }
        uriFirst = uriSecond;

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rsClick.selectedPlace(results.get(position));
            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                rsLongClick.selectedDescriptionPlace(results.get(position));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
            textView = itemView.findViewById(R.id.namePlace);
        }
    }
}
