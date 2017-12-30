package productions.darthplagueis.nasafeed.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import productions.darthplagueis.nasafeed.PhotoActivity;
import productions.darthplagueis.nasafeed.R;
import productions.darthplagueis.nasafeed.model.MarsRover.Photos;
import productions.darthplagueis.nasafeed.util.CustomRequestOptions;

/**
 * Created by oleg on 12/25/17.
 */

public class MarsRoverViewHolder extends RecyclerView.ViewHolder {
    private TextView cameraName;
    private ImageView roverPhoto;
    private RelativeLayout layout;
    private Context context;

    public MarsRoverViewHolder(View itemView) {
        super(itemView);
        cameraName = (TextView) itemView.findViewById(R.id.itemview_text);
        roverPhoto = (ImageView) itemView.findViewById(R.id.itemview_image);
        layout = (RelativeLayout) itemView.findViewById(R.id.itemview_layout);
        context = itemView.getContext();
    }

    public void onBind(final Photos photos) {
        cameraName.setText(photos.getCamera().getName());
        Glide.with(context)
                .setDefaultRequestOptions(CustomRequestOptions.getNoCropOptions())
                .load(photos.getImg_src())
                .apply(new RequestOptions().override(450, 450))
                .into(roverPhoto);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = photos.getCamera().getName();
                String fullName = photos.getCamera().getFull_name();
                String roverId = String.valueOf(photos.getCamera().getRover_id());
                String sol = String.valueOf(photos.getSol());
                String earthDate = photos.getEarth_date();
                String imageUrl = photos.getImg_src();

                Bundle bundle = new Bundle();
                bundle.putString("imageUrl", imageUrl);
                bundle.putString("name", name);
                bundle.putString("fullName", fullName);
                bundle.putString("roverId", roverId);
                bundle.putString("sol", sol);
                bundle.putString("earthDate", earthDate);
                Intent intent = new Intent(context, PhotoActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}
