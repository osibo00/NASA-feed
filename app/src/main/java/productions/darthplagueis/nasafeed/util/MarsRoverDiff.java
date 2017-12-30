package productions.darthplagueis.nasafeed.util;

import android.support.v7.util.DiffUtil;

import java.util.List;

import productions.darthplagueis.nasafeed.model.MarsRover.Photos;

/**
 * Created by oleg on 12/25/17.
 */

public class MarsRoverDiff extends DiffUtil.Callback {
    private List<Photos> oldPhotosList;
    private List<Photos> newPhotosList;

    public MarsRoverDiff(List<Photos> oldPhotosList, List<Photos> newPhotosList) {
        this.oldPhotosList = oldPhotosList;
        this.newPhotosList = newPhotosList;
    }

    @Override
    public int getOldListSize() {
        return oldPhotosList != null ? oldPhotosList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newPhotosList != null ? newPhotosList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPhotosList.get(oldItemPosition).getId() == newPhotosList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Photos oldPhotos = oldPhotosList.get(oldItemPosition);
        Photos newPhotos = newPhotosList.get(newItemPosition);
        return oldPhotos.getImg_src().equals(newPhotos.getImg_src());
    }
}
