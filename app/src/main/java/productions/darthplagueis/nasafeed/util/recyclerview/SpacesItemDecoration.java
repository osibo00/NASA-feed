package productions.darthplagueis.nasafeed.util.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by oleg on 12/26/17.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        // outRect.right = space;
        // The padding on the right is set in fragment_recycler xml on the recycler-view
        // to avoid double space between items.
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
