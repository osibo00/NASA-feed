package productions.darthplagueis.nasafeed.retrofit;

import productions.darthplagueis.nasafeed.retrofit.api.AstronomyPotdGetter;
import productions.darthplagueis.nasafeed.retrofit.api.MarsRoverGetter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit Singleton
 */

public class NasaRetrofit {

    private static Retrofit retrofit;

    private static NasaRetrofit instanceOfNasaRetrofit;

    private NasaRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NasaRetrofit getInstance() {
        if (instanceOfNasaRetrofit != null) {
            return instanceOfNasaRetrofit;
        }
        instanceOfNasaRetrofit = new NasaRetrofit();
        return instanceOfNasaRetrofit;
    }

    public static AstronomyPotdGetter astronomyPotdGetter() {
        return retrofit.create(AstronomyPotdGetter.class);
    }

    public static MarsRoverGetter marsRoverGetter() {
        return retrofit.create(MarsRoverGetter.class);
    }

}
