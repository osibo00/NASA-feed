package productions.darthplagueis.nasafeed.api;

import productions.darthplagueis.nasafeed.model.MarsRover.RoverManifest;
import productions.darthplagueis.nasafeed.model.MarsRover.RoverPhotos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by oleg on 12/25/17.
 */

public interface MarsRoverGetter {

    @GET("rovers/curiosity/photos")
    Call<RoverPhotos> getCuriosityPhotos(@Query("sol") int sol, @Query("page") int page, @Query("api_key") String apiKey);

    @GET("rovers/spirit/photos")
    Call<RoverPhotos> getSpiritPhotos(@Query("sol") int sol, @Query("page") int page, @Query("api_key") String apiKey);

    @GET("rovers/opportunity/photos")
    Call<RoverPhotos> getOpportunityPhotos(@Query("sol") int sol, @Query("page") int page, @Query("api_key") String apiKey);

    @GET("manifests/spirit")
    Call<RoverManifest> getSpiritManifest(@Query("api_key") String apiKey);

    @GET("manifests/curiosity")
    Call<RoverManifest> getCuriositytManifest(@Query("api_key") String apiKey);

    @GET("manifests/opportunity")
    Call<RoverManifest> getOpportunityManifest(@Query("api_key") String apiKey);
}
