package productions.darthplagueis.nasafeed.retrofit.api;

import productions.darthplagueis.nasafeed.model.MarsRover.RoverManifest;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverPhotos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by oleg on 12/25/17.
 */

public interface MarsRoverGetter {
    String marsRoverEndPoint = "mars-photos/api/v1/rovers/{name}/photos";
    String manifestEndPoint = "mars-photos/api/v1/manifests/{name}";
    String namePath = "name";

    @GET(marsRoverEndPoint)
    Call<RoverPhotos> getRoverPhotos(@Path(namePath) String roverName, @Query("sol") int sol, @Query("page") int page, @Query("api_key") String apiKey);

    @GET(marsRoverEndPoint)
    Call<RoverPhotos> getSearch(@Path(namePath) String roverName, @Query("earth_date") String earthDate, @Query("api_key") String apiKey);

    @GET(manifestEndPoint)
    Call<RoverManifest> getManifest(@Path(namePath) String roverName, @Query("api_key") String apiKey);
}
