package productions.darthplagueis.nasafeed.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import productions.darthplagueis.nasafeed.BuildConfig;
import productions.darthplagueis.nasafeed.R;
import productions.darthplagueis.nasafeed.api.MarsRoverGetter;
import productions.darthplagueis.nasafeed.controller.MarsRoverAdapter;
import productions.darthplagueis.nasafeed.model.MarsRover.Photos;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverPhotos;
import productions.darthplagueis.nasafeed.util.recyclerview.EndlessRecyclerViewScrollListener;
import productions.darthplagueis.nasafeed.util.DataProvider;
import productions.darthplagueis.nasafeed.util.recyclerview.SpacesItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class CuriosityFragment extends Fragment {
    private final String TAG = "CURIOSITY FRAGMENT";
    private static final String API_KEY = BuildConfig.API_KEY;
    private View rootView;
    private int pageNumber = 1;
    private int solNumber = 1;
    private Retrofit retrofit;
    private MarsRoverAdapter marsRoverAdapter;
    private GridLayoutManager layoutManager;
    private MarsRoverGetter marsRoverGetter;


    public CuriosityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);

        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.frag_activity_recycler);
        layoutManager = new GridLayoutManager(rootView.getContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(marsRoverAdapter.getItemViewType(position)) {
                    case 1:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        marsRoverAdapter = new MarsRoverAdapter(rootView.getContext());
        marsRoverAdapter.createList(DataProvider.getCuriosityPhotos());
        recyclerView.setAdapter(marsRoverAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        marsRoverGetter = retrofit.create(MarsRoverGetter.class);

        setScrolling(recyclerView);

//        rootView.findViewById(R.id.tempbtnrec).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                layoutManager.setSpanCount(1);
//                recyclerView.setLayoutManager(layoutManager);
//
//            }
//        });

        return rootView;
    }

    private void setScrolling(RecyclerView recyclerView) {
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pageNumber++;
                getMoreRoverPhotos(solNumber, pageNumber);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    private void getMoreRoverPhotos(int sol, int page) {
        Call<RoverPhotos> call = marsRoverGetter.getCuriosityPhotos(sol, page, API_KEY);
        call.enqueue(new Callback<RoverPhotos>() {
            @Override
            public void onResponse(Call<RoverPhotos> call, Response<RoverPhotos> response) {
                if (response.isSuccessful()) {
                    RoverPhotos roverPhotos = response.body();
                    List<Photos> photosList = roverPhotos.getPhotos();
                    if (photosList.size() > 0) {
                        marsRoverAdapter.listUpdate(photosList);
                    } else {
                        solNumber++;
                        pageNumber = 1;
                        getMoreRoverPhotos(solNumber, pageNumber);
                    }
                    Log.d(TAG, "onResponse: " + "sol: " + solNumber);
                    Log.d(TAG, "onResponse: " + "page: " + pageNumber);
                    Log.d(TAG, "onResponse: Curiosity size: " + photosList.size());
                    Log.d(TAG, "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<RoverPhotos> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
