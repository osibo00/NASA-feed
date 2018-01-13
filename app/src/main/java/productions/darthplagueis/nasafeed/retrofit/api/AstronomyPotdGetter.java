package productions.darthplagueis.nasafeed.retrofit.api;

import productions.darthplagueis.nasafeed.model.AstronomyPictureOfTheDay;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by oleg on 12/23/17.
 */

public interface AstronomyPotdGetter {
    String astronomyEndPoint = "planetary/apod";

    @GET(astronomyEndPoint)
    Call<AstronomyPictureOfTheDay> getAstronomyPotd(@Query("api_key") String api_key);

    @GET(astronomyEndPoint)
    Call<AstronomyPictureOfTheDay> getSpecificApod(@Query("date") String date, @Query("api_key") String api_key);
}
