package productions.darthplagueis.nasafeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.List;

import productions.darthplagueis.nasafeed.api.MarsRoverGetter;
import productions.darthplagueis.nasafeed.controller.MarsRoverAdapter;
import productions.darthplagueis.nasafeed.model.MarsRover.Photos;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverManifest;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverPhotos;
import productions.darthplagueis.nasafeed.util.DataProvider;
import productions.darthplagueis.nasafeed.util.CustomRequestOptions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MAIN RESULTS";
    public static final String ROVERCHOICE = "ROVER_CHOICE";
    private static final String API_KEY = BuildConfig.API_KEY;
    private ImageView astronomyImageView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        astronomyImageView = (ImageView) findViewById(R.id.main_imageview);
        setAstronomyPhoto();

        MarsRoverGetter marsRoverGetter = getMarsRoverGetter();
        getCuriosityData(marsRoverGetter);
        getOpportunityData(marsRoverGetter);
        getSpiritData(marsRoverGetter);
//        getCuriosityManifest(marsRoverGetter);
//        getOpportunityManifest(marsRoverGetter);
//        getSpiritManifest(marsRoverGetter);

        unlimitedPower();
    }

    private void setAstronomyPhoto() {
        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(CustomRequestOptions.getOtherOptions())
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(DataProvider.getAstronomyPhotos().get("imageUrl"))
                .into(astronomyImageView);
    }

    private MarsRoverGetter getMarsRoverGetter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MarsRoverGetter.class);
    }

    private void getSpiritData(MarsRoverGetter marsRoverGetter) {
        Call<RoverPhotos> spiritCall = marsRoverGetter.getSpiritPhotos(1, 1, API_KEY);
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

    private void getOpportunityData(MarsRoverGetter marsRoverGetter) {
        Call<RoverPhotos> opportunityCall = marsRoverGetter.getOpportunityPhotos(1, 1, API_KEY);
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

    private void getCuriosityData(MarsRoverGetter marsRoverGetter) {
        Call<RoverPhotos> call = marsRoverGetter.getCuriosityPhotos(1, 1, API_KEY);
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
                bundle.putString(ROVERCHOICE, "astronomy");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.curiosity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ROVERCHOICE, "curiosity");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.opportunity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ROVERCHOICE, "opportunity");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.spirit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ROVERCHOICE, "spirit");
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
    }

//    private void getOpportunityManifest(MarsRoverGetter marsRoverGetter) {
//        Call<RoverManifest> call = marsRoverGetter.getOpportunityManifest(API_KEY);
//        call.enqueue(new Callback<RoverManifest>() {
//            @Override
//            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
//                if (response.isSuccessful()) {
//                    RoverManifest roverManifest = response.body();
//
//                    HashMap<String, String> manifestStrings = new HashMap<>();
//                    manifestStrings.put("name", roverManifest.getPhoto_manifest().getName());
//                    manifestStrings.put("lauchDate", roverManifest.getPhoto_manifest().getLaunch_date());
//                    manifestStrings.put("maxSol", String.valueOf(roverManifest.getPhoto_manifest().getMax_sol()));
//                    manifestStrings.put("landingDate", roverManifest.getPhoto_manifest().getLanding_date());
//                    manifestStrings.put("maxDate", roverManifest.getPhoto_manifest().getMax_date());
//                    manifestStrings.put("status", roverManifest.getPhoto_manifest().getStatus());
//                    manifestStrings.put("totalPhotos", String.valueOf(roverManifest.getPhoto_manifest().getTotal_photos()));
//
//                    DataProvider.addOpportunityManifestList(manifestStrings);
//                    Log.d(TAG, "onResponse: " + "Opport manifest: " + manifestStrings.size());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RoverManifest> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
//
//    private void getCuriosityManifest(MarsRoverGetter marsRoverGetter) {
//        Call<RoverManifest> call = marsRoverGetter.getCuriositytManifest(API_KEY);
//        call.enqueue(new Callback<RoverManifest>() {
//            @Override
//            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
//                if (response.isSuccessful()) {
//                    RoverManifest roverManifest = response.body();
//
//                    HashMap<String, String> manifestStrings = new HashMap<>();
//                    manifestStrings.put("name", roverManifest.getPhoto_manifest().getName());
//                    manifestStrings.put("lauchDate", roverManifest.getPhoto_manifest().getLaunch_date());
//                    manifestStrings.put("maxSol", String.valueOf(roverManifest.getPhoto_manifest().getMax_sol()));
//                    manifestStrings.put("landingDate", roverManifest.getPhoto_manifest().getLanding_date());
//                    manifestStrings.put("maxDate", roverManifest.getPhoto_manifest().getMax_date());
//                    manifestStrings.put("status", roverManifest.getPhoto_manifest().getStatus());
//                    manifestStrings.put("totalPhotos", String.valueOf(roverManifest.getPhoto_manifest().getTotal_photos()));
//
//                    DataProvider.addCuriosityManifestList(manifestStrings);
//                    Log.d(TAG, "onResponse: " + "Curiosity manifest: " + manifestStrings.size());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RoverManifest> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
//
//    private void getSpiritManifest(MarsRoverGetter marsRoverGetter) {
//        Call<RoverManifest> call = marsRoverGetter.getSpiritManifest(API_KEY);
//        call.enqueue(new Callback<RoverManifest>() {
//            @Override
//            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
//                if (response.isSuccessful()) {
//                    RoverManifest roverManifest = response.body();
//
//                    HashMap<String, String> manifestStrings = new HashMap<>();
//                    manifestStrings.put("name", roverManifest.getPhoto_manifest().getName());
//                    manifestStrings.put("lauchDate", roverManifest.getPhoto_manifest().getLaunch_date());
//                    manifestStrings.put("maxSol", String.valueOf(roverManifest.getPhoto_manifest().getMax_sol()));
//                    manifestStrings.put("landingDate", roverManifest.getPhoto_manifest().getLanding_date());
//                    manifestStrings.put("maxDate", roverManifest.getPhoto_manifest().getMax_date());
//                    manifestStrings.put("status", roverManifest.getPhoto_manifest().getStatus());
//                    manifestStrings.put("totalPhotos", String.valueOf(roverManifest.getPhoto_manifest().getTotal_photos()));
//
//                    DataProvider.addSpiritManifestList(manifestStrings);
//                    Log.d(TAG, "onResponse: " + "Spirit manifest: " + manifestStrings.size());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RoverManifest> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }

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
