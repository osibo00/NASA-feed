package productions.darthplagueis.nasafeed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

import productions.darthplagueis.nasafeed.api.MarsRoverGetter;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverManifest;
import productions.darthplagueis.nasafeed.util.DataProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoverActivity extends AppCompatActivity {
    private final String TAG = "Rover Activity";
    private static final String API_KEY = BuildConfig.API_KEY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover);

        MarsRoverGetter marsRoverGetter = getMarsRoverGetter();
        getCuriosityManifest(marsRoverGetter);
        getOpportunityManifest(marsRoverGetter);
        getSpiritManifest(marsRoverGetter);
    }

    private MarsRoverGetter getMarsRoverGetter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MarsRoverGetter.class);
    }

    private void getOpportunityManifest(MarsRoverGetter marsRoverGetter) {
        Call<RoverManifest> call = marsRoverGetter.getOpportunityManifest(API_KEY);
        call.enqueue(new Callback<RoverManifest>() {
            @Override
            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
                if (response.isSuccessful()) {
                    RoverManifest roverManifest = response.body();

                    HashMap<String, String> manifestStrings = new HashMap<>();
                    manifestStrings.put("name", roverManifest.getPhoto_manifest().getName());
                    manifestStrings.put("lauchDate", roverManifest.getPhoto_manifest().getLaunch_date());
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

    private void getCuriosityManifest(MarsRoverGetter marsRoverGetter) {
        Call<RoverManifest> call = marsRoverGetter.getCuriositytManifest(API_KEY);
        call.enqueue(new Callback<RoverManifest>() {
            @Override
            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
                if (response.isSuccessful()) {
                    RoverManifest roverManifest = response.body();

                    HashMap<String, String> manifestStrings = new HashMap<>();
                    manifestStrings.put("name", roverManifest.getPhoto_manifest().getName());
                    manifestStrings.put("lauchDate", roverManifest.getPhoto_manifest().getLaunch_date());
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

    private void getSpiritManifest(MarsRoverGetter marsRoverGetter) {
        Call<RoverManifest> call = marsRoverGetter.getSpiritManifest(API_KEY);
        call.enqueue(new Callback<RoverManifest>() {
            @Override
            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
                if (response.isSuccessful()) {
                    RoverManifest roverManifest = response.body();

                    HashMap<String, String> manifestStrings = new HashMap<>();
                    manifestStrings.put("name", roverManifest.getPhoto_manifest().getName());
                    manifestStrings.put("lauchDate", roverManifest.getPhoto_manifest().getLaunch_date());
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
