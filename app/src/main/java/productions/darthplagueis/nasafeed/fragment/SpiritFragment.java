package productions.darthplagueis.nasafeed.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import productions.darthplagueis.nasafeed.BuildConfig;
import productions.darthplagueis.nasafeed.R;
import productions.darthplagueis.nasafeed.api.MarsRoverGetter;
import productions.darthplagueis.nasafeed.controller.MarsRoverAdapter;
import productions.darthplagueis.nasafeed.model.MarsRover.Photos;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverManifest;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverPhotos;
import productions.darthplagueis.nasafeed.util.DataProvider;
import productions.darthplagueis.nasafeed.util.recyclerview.EndlessRecyclerViewScrollListener;
import productions.darthplagueis.nasafeed.util.recyclerview.SpacesItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpiritFragment extends Fragment {
    private final String TAG = "SPIRIT FRAGMENT";
    private static final String API_KEY = BuildConfig.API_KEY;
    private View rootView;
    private int pageNumber = 1;
    private int solNumber = 1;
    private int maxSol;
    private int maxResult = 25;
    private boolean useMarsRoverDiff = false;
    private boolean marsDiffRan = false;
    private boolean subtractSol = false;
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private MarsRoverAdapter marsRoverAdapter;
    private GridLayoutManager layoutManager;
    private MarsRoverGetter marsRoverGetter;


    public SpiritFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.frag_activity_recycler);
        layoutManager = new GridLayoutManager(rootView.getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        marsRoverAdapter = new MarsRoverAdapter(rootView.getContext());
        marsRoverAdapter.createList(DataProvider.getSpiritPhotos());
        recyclerView.setAdapter(marsRoverAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        marsRoverGetter = retrofit.create(MarsRoverGetter.class);

        setScrolling(recyclerView);

        getMaxSol();

        clickMeh();

        return rootView;
    }

    private void clickMeh() {
        FabSpeedDial fabSpeedDial = (FabSpeedDial) rootView.findViewById(R.id.fab_speed);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_most_recent:
                        solNumber = maxSol;
                        pageNumber = 1;
                        useMarsRoverDiff = true;
                        marsDiffRan = false;
                        subtractSol = true;
                        getMoreRoverPhotos(solNumber, pageNumber);
                        setScrolling(recyclerView);
                        break;
                    case R.id.action_least_recent:
                        solNumber = 1;
                        pageNumber = 1;
                        useMarsRoverDiff = true;
                        marsDiffRan = false;
                        subtractSol = false;
                        getMoreRoverPhotos(solNumber, pageNumber);
                        setScrolling(recyclerView);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void setScrolling(RecyclerView recyclerView) {
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (subtractSol) {
                    solNumber--;
                } else {
                    solNumber++;
                }
                pageNumber++;
                getMoreRoverPhotos(solNumber, pageNumber);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    private void getMoreRoverPhotos(int sol, int page) {
        Call<RoverPhotos> call = marsRoverGetter.getSpiritPhotos(sol, page, API_KEY);
        call.enqueue(new Callback<RoverPhotos>() {
            @Override
            public void onResponse(Call<RoverPhotos> call, Response<RoverPhotos> response) {
                if (response.isSuccessful()) {
                    RoverPhotos roverPhotos = response.body();
                    List<Photos> photosList = roverPhotos.getPhotos();
                    if (!useMarsRoverDiff) {
                        if (photosList.size() > 0) {
                            marsRoverAdapter.listUpdate(photosList);
                        } else if (subtractSol) {
                            solNumber--;
                            pageNumber = 1;
                            getMoreRoverPhotos(solNumber, pageNumber);
                        } else {
                            solNumber++;
                            pageNumber = 1;
                            getMoreRoverPhotos(solNumber, pageNumber);
                        }
                    } else {
                        Log.d(TAG, "onResponse: " + "Callback userMarsRoverDiff statement ran.");
                        if (photosList.size() > 0 && !marsDiffRan) {
                            marsDiffRan = true;
                            marsRoverAdapter.updateWithDiff(photosList);
                            if (photosList.size() < maxResult && subtractSol) {
                                solNumber--;
                                pageNumber = 1;
                                getMoreRoverPhotos(solNumber, pageNumber);
                            } else if (photosList.size() < maxResult) {
                                solNumber++;
                                pageNumber = 1;
                                getMoreRoverPhotos(solNumber, pageNumber);
                            }
                        } else if (photosList.size() > 0) {
                            if (photosList.size() == maxResult) {
                                useMarsRoverDiff = false;
                                marsRoverAdapter.listUpdate(photosList);
                            } else if (photosList.size() < maxResult && subtractSol) {
                                marsRoverAdapter.listUpdate(photosList);
                                pageNumber++;
                                getMoreRoverPhotos(solNumber, pageNumber);
                            } else if (photosList.size() < maxResult) {
                                marsRoverAdapter.listUpdate(photosList);
                                pageNumber++;
                                getMoreRoverPhotos(solNumber, pageNumber);
                            }
                        } else if (photosList.size() == 0) {
                            if (subtractSol) {
                                solNumber--;
                                pageNumber = 1;
                                getMoreRoverPhotos(solNumber, pageNumber);
                            } else {
                                solNumber++;
                                pageNumber = 1;
                                getMoreRoverPhotos(solNumber, pageNumber);
                            }
                        }
                    }
                    Log.d(TAG, "onResponse: " + "useMarsRoverDiff: " + useMarsRoverDiff);
                    Log.d(TAG, "onResponse: " + "marsDiffRan: " + marsDiffRan);
                    Log.d(TAG, "onResponse: " + "sol: " + solNumber);
                    Log.d(TAG, "onResponse: " + "page: " + pageNumber);
                    Log.d(TAG, "onResponse: " + "Spirit size: " + photosList.size());
                    Log.d(TAG, "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<RoverPhotos> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getMaxSol() {
        Call<RoverManifest> call = marsRoverGetter.getSpiritManifest(API_KEY);
        call.enqueue(new Callback<RoverManifest>() {
            @Override
            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
                if (response.isSuccessful()) {
                    RoverManifest roverManifest = response.body();
                    maxSol = roverManifest.getPhoto_manifest().getMax_sol();
                    Log.d(TAG, "onResponse: " + "MAX sol: " + maxSol);
                }
            }

            @Override
            public void onFailure(Call<RoverManifest> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}