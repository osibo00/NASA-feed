package productions.darthplagueis.nasafeed.fragment;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import me.toptas.fancyshowcase.FancyShowCaseView;
import productions.darthplagueis.nasafeed.BuildConfig;
import productions.darthplagueis.nasafeed.retrofit.NasaRetrofit;
import productions.darthplagueis.nasafeed.R;
import productions.darthplagueis.nasafeed.retrofit.api.MarsRoverGetter;
import productions.darthplagueis.nasafeed.controller.MarsRoverAdapter;
import productions.darthplagueis.nasafeed.model.MarsRover.Photos;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverManifest;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverPhotos;
import productions.darthplagueis.nasafeed.util.recyclerview.EndlessRecyclerViewScrollListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The base fragment for Curiosity, Opportunity and Spirit fragments.
 */
public abstract class RoverFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String API_KEY = BuildConfig.API_KEY;
    private View rootView;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private MarsRoverGetter marsRoverGetter;
    private int pageNumber = 1;
    private int solNumber = 1;
    private int maxSol;
    private int maxResult = 25;
    private boolean useMarsRoverDiff = false;
    private boolean marsDiffRan = false;
    private boolean subtractSol = false;
    private String landingDate;
    private String maxDate;
    private FabSpeedDial fabSpeedDial;
    private AlertDialog.Builder builder;

    protected abstract GridLayoutManager getLayoutManager();

    protected abstract RecyclerView.ItemDecoration getItemDecoration();

    protected abstract MarsRoverAdapter getAdapter();

    protected abstract List<Photos> getList();

    protected abstract String getTitle();

    protected abstract String getFragmentName();

    protected abstract String getRoverName();


    public RoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);

        setToolbar();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.frag_activity_recycler);
        layoutManager = getLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(getItemDecoration());
        getAdapter().createList(getList());
        recyclerView.setAdapter(getAdapter());

        setRetrofit();
        setScrolling();
        getManifest();
        setFab();
        setShowCaseView();

        builder = new AlertDialog.Builder(rootView.getContext());

        return rootView;
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.frag_recycler_toolbar);
        toolbar.setTitle(getTitle());
        toolbar.setSubtitle("Scroll your way through Mars!");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setRetrofit() {
        NasaRetrofit nasaRetrofit = NasaRetrofit.getInstance();
        marsRoverGetter = nasaRetrofit.marsRoverGetter();
    }

    private void setScrolling() {
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
                Log.d(getFragmentName(), "onLoadMore: Ran");
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    private void getMoreRoverPhotos(int sol, int page) {
        Call<RoverPhotos> call = marsRoverGetter.getRoverPhotos(getRoverName(), sol, page, API_KEY);
        call.enqueue(new Callback<RoverPhotos>() {
            @Override
            public void onResponse(Call<RoverPhotos> call, Response<RoverPhotos> response) {
                if (response.isSuccessful()) {
                    RoverPhotos roverPhotos = response.body();
                    List<Photos> photosList = roverPhotos.getPhotos();
                    if (!useMarsRoverDiff) {
                        if (photosList.size() > 0) {
                            getAdapter().listUpdate(photosList);
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
                        Log.d(getFragmentName(), "onResponse: " + "Callback userMarsRoverDiff statement ran.");
                        if (photosList.size() > 0 && !marsDiffRan) {
                            marsDiffRan = true;
                            getAdapter().updateWithDiff(photosList);
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
                                getAdapter().listUpdate(photosList);
                            } else if (photosList.size() < maxResult && subtractSol) {
                                getAdapter().listUpdate(photosList);
                                pageNumber++;
                                getMoreRoverPhotos(solNumber, pageNumber);
                            } else if (photosList.size() < maxResult) {
                                getAdapter().listUpdate(photosList);
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
                    Log.d(getFragmentName(), "onResponse: " + "useMarsRoverDiff: " + useMarsRoverDiff);
                    Log.d(getFragmentName(), "onResponse: " + "marsDiffRan: " + marsDiffRan);
                    Log.d(getFragmentName(), "onResponse: " + "sol: " + solNumber);
                    Log.d(getFragmentName(), "onResponse: " + "page: " + pageNumber);
                    Log.d(getFragmentName(), "onResponse: Curiosity size: " + photosList.size());
                    Log.d(getFragmentName(), "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<RoverPhotos> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getManifest() {
        Call<RoverManifest> call = marsRoverGetter.getManifest(getRoverName(), API_KEY);
        call.enqueue(new Callback<RoverManifest>() {
            @Override
            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
                if (response.isSuccessful()) {
                    RoverManifest roverManifest = response.body();
                    maxSol = roverManifest.getPhoto_manifest().getMax_sol();
                    landingDate = roverManifest.getPhoto_manifest().getLanding_date();
                    maxDate = roverManifest.getPhoto_manifest().getMax_date();
                }
                Log.d(getFragmentName(), "onResponse: " + "MAX sol: " + maxSol);
                Log.d(getFragmentName(), "onResponse: " + "Launch date: " + landingDate);
                Log.d(getFragmentName(), "onResponse: " + "MAX date " + maxDate);
            }

            @Override
            public void onFailure(Call<RoverManifest> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setFab() {
        fabSpeedDial = (FabSpeedDial) rootView.findViewById(R.id.fab_speed);
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
                        setScrolling();
                        break;
                    case R.id.action_least_recent:
                        solNumber = 1;
                        pageNumber = 1;
                        useMarsRoverDiff = true;
                        marsDiffRan = false;
                        subtractSol = false;
                        getMoreRoverPhotos(solNumber, pageNumber);
                        setScrolling();
                        break;
                    case R.id.action_search:
                        setCalender();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void setShowCaseView() {
        new FancyShowCaseView.Builder(getActivity())
                .focusOn(fabSpeedDial)
                .title("See most recent, least recent or search images by day")
                .focusCircleAtPosition(fabSpeedDial.getScrollX(), fabSpeedDial.getScrollY(), fabSpeedDial.getWidth())
                .backgroundColor(Color.parseColor("#80a7001b"))
                .focusBorderColor(Color.parseColor("#5b8bde"))
                .focusBorderSize(10)
                .delay(1500)
                .showOnce("fancy1")
                .build()
                .show();
    }

    private void setCalender() {
        String[] launchDateStrings = landingDate.split("-");
        String[] maxDateStrings = maxDate.split("-");
        int launchYear = Integer.parseInt(launchDateStrings[0]);
        int launchMonth = Integer.parseInt(launchDateStrings[1]);
        int launchDay = Integer.parseInt(launchDateStrings[2]);
        int maxYear = Integer.parseInt(maxDateStrings[0]);
        int maxMonth = Integer.parseInt(maxDateStrings[1]);
        int maxDay = Integer.parseInt(maxDateStrings[2]);

        new SpinnerDatePickerDialogBuilder()
                .context(rootView.getContext())
                .callback(RoverFragment.this)
                .spinnerTheme(R.style.DatePickerSpinner)
                .defaultDate(launchYear, launchMonth - 1, launchDay)
                .maxDate(maxYear, maxMonth - 1, maxDay)
                .minDate(launchYear, launchMonth - 1, launchDay)
                .build()
                .show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String searchQuery = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
        getSearchQuery(searchQuery);
    }

    private void getSearchQuery(String earthDate) {
        Call<RoverPhotos> call = marsRoverGetter.getSearch(getRoverName(), earthDate, API_KEY);
        call.enqueue(new Callback<RoverPhotos>() {
            @Override
            public void onResponse(Call<RoverPhotos> call, Response<RoverPhotos> response) {
                if (response.isSuccessful()) {
                    RoverPhotos roverPhotos = response.body();
                    List<Photos> photosList = roverPhotos.getPhotos();
                    if (photosList.size() > 0) {
                        setResultsDialog(photosList.size());
                        getAdapter().updateWithDiff(photosList);
                    } else if (photosList.size() == 0) {
                        setSearchAgainDialog();
                    }
                    Log.d(getFragmentName(), "onResponse: " + "Search Query list: " + photosList.size());
                }
            }

            @Override
            public void onFailure(Call<RoverPhotos> call, Throwable t) {
                setSearchAgainDialog();
                t.printStackTrace();
            }
        });
    }

    private void setResultsDialog(int size) {
        builder.setMessage(String.valueOf(size) + " images found")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void setSearchAgainDialog() {
        builder.setMessage("No images found for this day. Search again?")
                .setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setCalender();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }
}
