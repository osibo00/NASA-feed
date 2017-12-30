package productions.darthplagueis.nasafeed.controller;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import productions.darthplagueis.nasafeed.FragmentsActivity;
import productions.darthplagueis.nasafeed.R;
import productions.darthplagueis.nasafeed.model.MarsRover.Photos;
import productions.darthplagueis.nasafeed.util.MarsRoverDiff;
import productions.darthplagueis.nasafeed.view.MarsRoverViewHolder;

/**
 * Created by oleg on 12/25/17.
 */

public class MarsRoverAdapter extends RecyclerView.Adapter<MarsRoverViewHolder> {
    private List<Photos> photosList;
    private Context context;
    private View childView;

    public MarsRoverAdapter(Context context) {
        this.context = context;
        photosList = new ArrayList<>();
    }

    @Override
    public MarsRoverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (FragmentsActivity.showTextView) {
            childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mars_rover_itemview, parent, false);

        } else {
            childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mars_rover_itemview_no_text, parent, false);
        }
        return new MarsRoverViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(MarsRoverViewHolder holder, int position) {
        ((MarsRoverViewHolder) holder).onBind(photosList.get(position));
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position % 3 == 0){
            return 1;
        }
        return super.getItemViewType(position);
    }

    public void createList(List<Photos> newList) {
        photosList.addAll(newList);
    }

    public void listUpdate(List<Photos> newList) {
        photosList.addAll(newList);
        notifyItemRangeInserted(getItemCount(), photosList.size() - 1);
    }

    public void updateList(List<Photos> newList) {
        MarsRoverDiff marsRoverDiff = new MarsRoverDiff(this.photosList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(marsRoverDiff);
        diffResult.dispatchUpdatesTo(this);
        this.photosList.clear();
        this.photosList.addAll(newList);
    }
}
