package productions.darthplagueis.nasafeed.fragment;


import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import productions.darthplagueis.nasafeed.MainActivity;
import productions.darthplagueis.nasafeed.R;
import productions.darthplagueis.nasafeed.util.CustomRequestOptions;
import productions.darthplagueis.nasafeed.util.DataProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class AstronomyFragment extends Fragment {

    public AstronomyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.photo_view, container, false);

        final ImageView astronomyImageView = (ImageView) rootView.findViewById(R.id.photo_detail_image);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.photo_detail_title);
        TextView dateTextView = (TextView) rootView.findViewById(R.id.photo_detail_date);
        TextView explanationTextView = (TextView) rootView.findViewById(R.id.photo_detail_explanation);

        titleTextView.setText(DataProvider.getAstronomyPhotos().get("title"));
        dateTextView.setText(DataProvider.getAstronomyPhotos().get("date"));
        explanationTextView.setText(DataProvider.getAstronomyPhotos().get("explanation"));

        setImageLayout(rootView, astronomyImageView);

        return rootView;
    }

    private void setImageLayout(final View rootView, final ImageView astronomyImageView) {
        Glide.with(rootView)
                .setDefaultRequestOptions(CustomRequestOptions.getOtherOptions())
                .asBitmap()
                .load(DataProvider.getAstronomyPhotos().get("imageHdurl"))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        onPalette(Palette.from(resource).generate());
                        extractTextColor(Palette.from(resource).generate());
                        astronomyImageView.setImageBitmap(resource);
                        return false;
                    }

                    private void onPalette(Palette palette) {
                        if (palette != null) {
                            ViewGroup parent = (ViewGroup) astronomyImageView.getParent().getParent();
                            parent.setBackgroundColor(palette.getDarkMutedColor(getResources().getColor(R.color.colorPrimaryDark)));
                            LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.photo_layout_date);
                            linearLayout.setBackgroundColor(palette.getMutedColor(getResources().getColor(R.color.colorPrimary)));
                            LinearLayout linearLayout01 = (LinearLayout) rootView.findViewById(R.id.photo_layout_explanation);
                            linearLayout01.setBackgroundColor(palette.getLightMutedColor(getResources().getColor(R.color.colorAccent)));
                            RelativeLayout layout = (RelativeLayout) rootView.findViewById(R.id.photo_detail_layout);
                            layout.setBackgroundColor(palette.getLightMutedColor(getResources().getColor(R.color.colorAccent)));
                        }
                    }

                    private void extractTextColor(Palette palette) {
                        Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                        int textColor = Color.parseColor("#ffffff");
                        if (swatch != null) {
                            textColor = swatch.getTitleTextColor();
                        }
                        TextView textView = (TextView) rootView.findViewById(R.id.photo_detail_explanation);
                        //textView.setTextColor(textColor);
                    }
                })
                .into(astronomyImageView);
    }
}
