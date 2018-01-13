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

import productions.darthplagueis.nasafeed.retrofit.NasaRetrofit;
import productions.darthplagueis.nasafeed.retrofit.api.AstronomyPotdGetter;
import productions.darthplagueis.nasafeed.retrofit.api.MarsRoverGetter;
import productions.darthplagueis.nasafeed.model.AstronomyPictureOfTheDay;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverManifest;
import productions.darthplagueis.nasafeed.util.CustomRequestOptions;
import productions.darthplagueis.nasafeed.util.DataProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private final String TAG = "Splash Activity";
    private static final String API_KEY = BuildConfig.API_KEY;
    private Intent intent;
    private AstronomyPotdGetter astronomyPotdGetter;
    private MarsRoverGetter marsRoverGetter;
    public static final String curiosity = "curiosity";
    public static final String opportunity = "opportunity";
    public static final String spirit = "spirit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setRetrofit();
        intent = new Intent(getBaseContext(), MainActivity.class);
        startAnimation();

        getAstronomyPotdData();
        getCuriosityManifest();
        getOpportunityManifest();
        getSpiritManifest();
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

    private void setRetrofit() {
        NasaRetrofit nasaRetrofit = NasaRetrofit.getInstance();
        astronomyPotdGetter = nasaRetrofit.astronomyPotdGetter();
        marsRoverGetter = nasaRetrofit.marsRoverGetter();
    }

    private void getAstronomyPotdData() {
        Call<AstronomyPictureOfTheDay> call = astronomyPotdGetter.getAstronomyPotd(API_KEY);
        call.enqueue(new Callback<AstronomyPictureOfTheDay>() {
            @Override
            public void onResponse(Call<AstronomyPictureOfTheDay> call, Response<AstronomyPictureOfTheDay> response) {
                if (response.isSuccessful()) {
                    AstronomyPictureOfTheDay pictureOfTheDay = response.body();

                    HashMap<String, String> astroStrings = new HashMap<>();
                    astroStrings.put("date", pictureOfTheDay.getDate());
                    astroStrings.put("explanation", pictureOfTheDay.getExplanation());
                    astroStrings.put("imageUrl", pictureOfTheDay.getUrl());
                    astroStrings.put("imageHdurl", pictureOfTheDay.getHdurl());
                    astroStrings.put("title", pictureOfTheDay.getTitle());

                    DataProvider.addAstronomyList(astroStrings);

                    Glide.with(getApplicationContext())
                            .setDefaultRequestOptions(CustomRequestOptions.getRequestOptions())
                            .load(pictureOfTheDay.getUrl())
                            .preload();

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

    private void getOpportunityManifest() {
        Call<RoverManifest> call = marsRoverGetter.getManifest(opportunity, API_KEY);
        call.enqueue(new Callback<RoverManifest>() {
            @Override
            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
                if (response.isSuccessful()) {
                    RoverManifest roverManifest = response.body();

                    HashMap<String, String> manifestStrings = new HashMap<>();
                    manifestStrings.put("name", roverManifest.getPhoto_manifest().getName());
                    manifestStrings.put("launchDate", roverManifest.getPhoto_manifest().getLaunch_date());
                    manifestStrings.put("maxSol", String.valueOf(roverManifest.getPhoto_manifest().getMax_sol()));
                    manifestStrings.put("landingDate", roverManifest.getPhoto_manifest().getLanding_date());
                    manifestStrings.put("maxDate", roverManifest.getPhoto_manifest().getMax_date());
                    manifestStrings.put("status", roverManifest.getPhoto_manifest().getStatus());
                    manifestStrings.put("totalPhotos", String.valueOf(roverManifest.getPhoto_manifest().getTotal_photos()));

                    DataProvider.addOpportunityManifestList(manifestStrings);
                    Log.d(TAG, "onResponse: " + "Opport manifest: " + manifestStrings.size());
                }
            }

            @Override
            public void onFailure(Call<RoverManifest> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getCuriosityManifest() {
        Call<RoverManifest> call = marsRoverGetter.getManifest(curiosity, API_KEY);
        call.enqueue(new Callback<RoverManifest>() {
            @Override
            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
                if (response.isSuccessful()) {
                    RoverManifest roverManifest = response.body();

                    HashMap<String, String> manifestStrings = new HashMap<>();
                    manifestStrings.put("name", roverManifest.getPhoto_manifest().getName());
                    manifestStrings.put("launchDate", roverManifest.getPhoto_manifest().getLaunch_date());
                    manifestStrings.put("maxSol", String.valueOf(roverManifest.getPhoto_manifest().getMax_sol()));
                    manifestStrings.put("landingDate", roverManifest.getPhoto_manifest().getLanding_date());
                    manifestStrings.put("maxDate", roverManifest.getPhoto_manifest().getMax_date());
                    manifestStrings.put("status", roverManifest.getPhoto_manifest().getStatus());
                    manifestStrings.put("totalPhotos", String.valueOf(roverManifest.getPhoto_manifest().getTotal_photos()));

                    DataProvider.addCuriosityManifestList(manifestStrings);
                    Log.d(TAG, "onResponse: " + "Curiosity manifest: " + manifestStrings.size());
                }
            }

            @Override
            public void onFailure(Call<RoverManifest> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getSpiritManifest() {
        Call<RoverManifest> call = marsRoverGetter.getManifest(spirit, API_KEY);
        call.enqueue(new Callback<RoverManifest>() {
            @Override
            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
                if (response.isSuccessful()) {
                    RoverManifest roverManifest = response.body();

                    HashMap<String, String> manifestStrings = new HashMap<>();
                    manifestStrings.put("name", roverManifest.getPhoto_manifest().getName());
                    manifestStrings.put("launchDate", roverManifest.getPhoto_manifest().getLaunch_date());
                    manifestStrings.put("maxSol", String.valueOf(roverManifest.getPhoto_manifest().getMax_sol()));
                    manifestStrings.put("landingDate", roverManifest.getPhoto_manifest().getLanding_date());
                    manifestStrings.put("maxDate", roverManifest.getPhoto_manifest().getMax_date());
                    manifestStrings.put("status", roverManifest.getPhoto_manifest().getStatus());
                    manifestStrings.put("totalPhotos", String.valueOf(roverManifest.getPhoto_manifest().getTotal_photos()));

                    DataProvider.addSpiritManifestList(manifestStrings);
                    Log.d(TAG, "onResponse: " + "Spirit manifest: " + manifestStrings.size());
                }
            }

            @Override
            public void onFailure(Call<RoverManifest> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
