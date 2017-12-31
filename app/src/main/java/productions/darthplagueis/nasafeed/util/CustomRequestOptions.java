package productions.darthplagueis.nasafeed.util;

import android.view.WindowManager;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import productions.darthplagueis.nasafeed.R;

/**
 * Created by oleg on 12/25/17.
 */

public class CustomRequestOptions {

    public static RequestOptions getRequestOptions() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.nasalogo);
        //requestOptions.centerCrop();
        return requestOptions;
    }

    public static RequestOptions getNoCropOptions() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.nasalogo);
        requestOptions.fitCenter();
        return requestOptions;
    }

    public static RequestOptions getOtherOptions() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.nasalogo);
        requestOptions.fitCenter();
        requestOptions.override(WindowManager.LayoutParams.MATCH_PARENT);
        return requestOptions;
    }
}
