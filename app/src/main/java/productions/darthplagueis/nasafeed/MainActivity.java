package productions.darthplagueis.nasafeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import productions.darthplagueis.nasafeed.retrofit.NasaRetrofit;
import productions.darthplagueis.nasafeed.retrofit.api.MarsRoverGetter;
import productions.darthplagueis.nasafeed.model.MarsRover.Photos;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverPhotos;
import productions.darthplagueis.nasafeed.util.DataProvider;
import productions.darthplagueis.nasafeed.util.CustomRequestOptions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static productions.darthplagueis.nasafeed.SplashActivity.curiosity;
import static productions.darthplagueis.nasafeed.SplashActivity.opportunity;
import static productions.darthplagueis.nasafeed.SplashActivity.spirit;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MAIN RESULTS";
    public static final String ROVER_CHOICE = "ROVER CHOICE";
    private static final String API_KEY = BuildConfig.API_KEY;
    private MarsRoverGetter marsRoverGetter;
    private ImageView astronomyImageView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRetrofit();
        astronomyImageView = (ImageView) findViewById(R.id.main_imageview);
        setAstronomyPhoto();

        getCuriosityData();
        getOpportunityData();
        getSpiritData();

        unlimitedPower();
    }

    private void setRetrofit() {
        NasaRetrofit nasaRetrofit = NasaRetrofit.getInstance();
        marsRoverGetter = nasaRetrofit.marsRoverGetter();
    }

    private void setAstronomyPhoto() {
        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(CustomRequestOptions.getOtherOptions())
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(DataProvider.getAstronomyPhotos().get("imageUrl"))
                .into(astronomyImageView);
    }

    private void getSpiritData() {
        Call<RoverPhotos> spiritCall = marsRoverGetter.getRoverPhotos(spirit,1, 1, API_KEY);
        spiritCall.enqueue(new Callback<RoverPhotos>() {
            @Override
            public void onResponse(Call<RoverPhotos> call, Response<RoverPhotos> response) {
                if (response.isSuccessful()) {
                    RoverPhotos roverPhotos = response.body();
                    List<Photos> spiritList = roverPhotos.getPhotos();
                    DataProvider.addSpiritList(spiritList);
                    Log.d(TAG, "onResponse Spirit: " + spiritList.size());
                }
            }

            @Override
            public void onFailure(Call<RoverPhotos> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getOpportunityData() {
        Call<RoverPhotos> opportunityCall = marsRoverGetter.getRoverPhotos(opportunity,1, 1, API_KEY);
        opportunityCall.enqueue(new Callback<RoverPhotos>() {
            @Override
            public void onResponse(Call<RoverPhotos> call, Response<RoverPhotos> response) {
                if (response.isSuccessful()) {
                    RoverPhotos roverPhotos = response.body();
                    List<Photos> opportunityList = roverPhotos.getPhotos();
                    DataProvider.addOpportunityList(opportunityList);
                    Log.d(TAG, "onResponse Opportunity: " + opportunityList.size());
                }

            }

            @Override
            public void onFailure(Call<RoverPhotos> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getCuriosityData() {
        Call<RoverPhotos> call = marsRoverGetter.getRoverPhotos(curiosity,1, 1, API_KEY);
        call.enqueue(new Callback<RoverPhotos>() {
            @Override
            public void onResponse(Call<RoverPhotos> call, Response<RoverPhotos> response) {
                if (response.isSuccessful()) {
                    RoverPhotos roverPhotos = response.body();
                    List<Photos> curiosityList = roverPhotos.getPhotos();
                    DataProvider.addCuriosityList(curiosityList);
                    Log.d(TAG, "onResponse Curiosity: " + curiosityList.size());
                }
            }

            @Override
            public void onFailure(Call<RoverPhotos> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void unlimitedPower() {
        intent = new Intent(MainActivity.this, FragmentsActivity.class);

        findViewById(R.id.main_ast_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ROVER_CHOICE, "astronomy");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.curiosity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ROVER_CHOICE, "curiosity");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.opportunity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ROVER_CHOICE, "opportunity");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.spirit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ROVER_CHOICE, "spirit");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.main_rover_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RoverActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.temp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ROVER_CHOICE, "light_grey_line");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MAIN ACTIVITY", "onResume: " + "CALLED");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MAIN ACTIVITY", "onPause: " + "CALLED");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MAIN ACTIVITY", "onStop: " + "CALLED");

    }

}
