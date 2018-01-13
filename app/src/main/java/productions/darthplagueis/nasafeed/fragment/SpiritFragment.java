package productions.darthplagueis.nasafeed.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import productions.darthplagueis.nasafeed.R;
import productions.darthplagueis.nasafeed.controller.MarsRoverAdapter;
import productions.darthplagueis.nasafeed.model.MarsRover.Photos;
import productions.darthplagueis.nasafeed.util.DataProvider;
import productions.darthplagueis.nasafeed.util.recyclerview.SpacesItemDecoration;

/**
 * Created by oleg on 1/9/18.
 */

public class SpiritFragment extends RoverFragment {

    private MarsRoverAdapter marsRoverAdapter = new MarsRoverAdapter(getActivity());

    public static SpiritFragment newInstance() {
        SpiritFragment fragment = new SpiritFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected GridLayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 3);
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        return new SpacesItemDecoration(spacingInPixels);
    }

    @Override
    protected MarsRoverAdapter getAdapter() {
        return marsRoverAdapter;
    }

    @Override
    protected List<Photos> getList() {
        return DataProvider.getSpiritPhotos();
    }

    @Override
    protected String getTitle() {
        return "Spirit Rover";
    }

    @Override
    protected String getFragmentName() {
        return "Spirit Fragment";
    }

    @Override
    protected String getRoverName() {
        return "spirit";
    }
}
