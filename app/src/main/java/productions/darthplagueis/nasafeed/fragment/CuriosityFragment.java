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
 * Created by oleg on 1/7/18.
 */

public class CuriosityFragment extends RoverFragment {

    private MarsRoverAdapter marsRoverAdapter = new MarsRoverAdapter(getActivity());

    public static CuriosityFragment newInstance() {
        CuriosityFragment fragment = new CuriosityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected GridLayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (getAdapter().getItemViewType(position)) {
                    case 1:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        return layoutManager;
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
        return DataProvider.getCuriosityPhotos();
    }

    @Override
    protected String getTitle() {
        return "Curiosity Rover";
    }

    @Override
    protected String getFragmentName() {
        return "Curiosity Fragment";
    }

    @Override
    protected String getRoverName() {
        return "curiosity";
    }
}
