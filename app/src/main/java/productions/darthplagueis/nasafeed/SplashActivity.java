package productions.darthplagueis.nasafeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import productions.darthplagueis.nasafeed.api.AstronomyPotdGetter;
import productions.darthplagueis.nasafeed.model.AstronomyPictureOfTheDay;
import productions.darthplagueis.nasafeed.util.CustomRequestOptions;
import productions.darthplagueis.nasafeed.util.DataProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {
    private static final String API_KEY = BuildConfig.API_KEY;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startAnimation();
        intent = new Intent(getBaseContext(), MainActivity.class);
        getAstronomyPotdData();
    }

    private void startAnimation() {
        final ImageView imageLogo = (ImageView) findViewById(R.id.splash_image);
        final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.antirotate);
        final Animation animation_3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_out);

        imageLogo.startAnimation(animation_2);
        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageLogo.startAnimation(animation_1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageLogo.startAnimation(animation_3);
                finish();
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void getAstronomyPotdData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AstronomyPotdGetter potdGetter = retrofit.create(AstronomyPotdGetter.class);
        Call<AstronomyPictureOfTheDay> call = potdGetter.getAstronomyPotd(API_KEY);
        call.enqueue(new Callback<AstronomyPictureOfTheDay>() {
            @Override
            public void onResponse(Call<AstronomyPictureOfTheDay> call, Response<AstronomyPictureOfTheDay> response) {
                if (response.isSuccessful()) {
                    AstronomyPictureOfTheDay pictureOfTheDay = response.body();

                    HashMap<String, String> astroStrings = new HashMap<>();
                    astroStrings.put("date", pictureOfTheDay.getDate());
                    astroStrings.put("explanation", pictureOfTheDay.getExplanation());
                    astroStrings.put("imageUrl", pictureOfTheDay.getHdurl());
                    astroStrings.put("title", pictureOfTheDay.getTitle());

                    DataProvider.addAstronomyList(astroStrings);

                    Glide.with(getApplicationContext())
                            .setDefaultRequestOptions(CustomRequestOptions.getRequestOptions())
                            .load(pictureOfTheDay.getHdurl())
                            .preload();

                    Log.d("Splash Screen", "onResponse: " + pictureOfTheDay.getHdurl());
                }
            }

            @Override
            public void onFailure(Call<AstronomyPictureOfTheDay> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
