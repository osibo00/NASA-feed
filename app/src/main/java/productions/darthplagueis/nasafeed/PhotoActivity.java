package productions.darthplagueis.nasafeed;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
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

import productions.darthplagueis.nasafeed.util.CustomRequestOptions;
import productions.darthplagueis.nasafeed.util.DataProvider;

public class PhotoActivity extends AppCompatActivity {
    private String imageUrl, name, fullName, roverId, sol, earthDate, landingDate, maxDate, maxSol, totalPhotos;
    private ImageView photoDetailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imageUrl = extras.getString("imageUrl");
            name = extras.getString("name");
            fullName = extras.getString("fullName");
            roverId = extras.getString("roverId");
            sol = extras.getString("sol");
            earthDate = extras.getString("earthDate");
            landingDate = extras.getString("landingDate");
            maxDate = extras.getString("maxDate");
            maxSol = extras.getString("maxSol");
            totalPhotos = extras.getString("totalPhotos");
        }

        photoDetailImage = (ImageView) findViewById(R.id.photo_act_image);
        TextView nameText = (TextView) findViewById(R.id.photo_act_title);
        TextView fullNameText = (TextView) findViewById(R.id.photo_act_full);
        TextView earthDateText = (TextView) findViewById(R.id.photo_act_earthday);
        TextView solDateText = (TextView) findViewById(R.id.photo_act_sol);
        TextView roverIdText = (TextView) findViewById(R.id.photo_act_rover);
        TextView landingDateText = (TextView) findViewById(R.id.photo_act_landing);
        TextView maxDateText = (TextView) findViewById(R.id.photo_act_maxdate);
        TextView maxSolText = (TextView) findViewById(R.id.photo_act_maxsol);
        TextView totalPhotosText = (TextView) findViewById(R.id.photo_act_totalphotos);

        nameText.setText(name);
        fullNameText.setText(fullName);
        earthDateText.setText(earthDate);
        solDateText.setText(sol);
        roverIdText.setText(roverId);
        landingDateText.setText(landingDate);
        maxDateText.setText(maxDate);
        maxSolText.setText(maxSol);
        totalPhotosText.setText(totalPhotos);
        setImageLayout();

    }

    private void setImageLayout() {
        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(CustomRequestOptions.getOtherOptions())
                .asBitmap()
                .load(imageUrl)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        onPalette(Palette.from(resource).generate());
                        extractTextColor(Palette.from(resource).generate());
                        photoDetailImage.setImageBitmap(resource);
                        return false;
                    }

                    private void onPalette(Palette palette) {
                        if (palette != null) {
                            ViewGroup parent = (ViewGroup) photoDetailImage.getParent().getParent();
                            parent.setBackgroundColor(palette.getDarkMutedColor(getResources().getColor(R.color.colorPrimaryDark)));
                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.photo_act_layout_date);
                            linearLayout.setBackgroundColor(palette.getMutedColor(getResources().getColor(R.color.colorPrimary)));
                            LinearLayout linearLayout01 = (LinearLayout) findViewById(R.id.photo_act_layout_explanation);
                            linearLayout01.setBackgroundColor(palette.getLightMutedColor(getResources().getColor(R.color.colorAccent)));
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.photo_act_top_layout);
                            layout.setBackgroundColor(palette.getLightMutedColor(getResources().getColor(R.color.colorAccent)));
                        }
                    }

                    private void extractTextColor(Palette palette) {
                        Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                        int textColor = Color.parseColor("#ffffff");
                        if (swatch != null) {
                            textColor = swatch.getTitleTextColor();
                        }
                        TextView textView = (TextView) findViewById(R.id.photo_act_colortext);
                        TextView textView01 = (TextView) findViewById(R.id.photo_act_colortext01);
                        TextView textView02 = (TextView) findViewById(R.id.photo_act_colortext02);
                        TextView textView03 = (TextView) findViewById(R.id.photo_act_colortext03);
                        TextView textView04 = (TextView) findViewById(R.id.photo_act_colortext04);
                        TextView textView05 = (TextView) findViewById(R.id.photo_act_colortext05);
                        TextView textView06 = (TextView) findViewById(R.id.photo_act_colortext06);
                        textView.setTextColor(textColor);
                        textView01.setTextColor(textColor);
                        textView02.setTextColor(textColor);
                        textView03.setTextColor(textColor);
                        textView04.setTextColor(textColor);
                        textView05.setTextColor(textColor);
                        textView06.setTextColor(textColor);
                    }
                })
                .into(photoDetailImage);
    }
}
