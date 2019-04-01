package yellow7918.ajou.ac.michelin_guide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DatabaseAPI {
    @GET("restaurant/{lan}")
    Call<List<Restaurant>> getRestaurant(@Path("lan") String lan);

    @GET("restaurant/{lan}/simple")
    Call<List<Restaurant>> getRestaurantBySimpleQuery(@Path("lan") String lan, @Query("loc") String loc, @Query("cat") String cat, @Query("name") String name);

    @GET("restaurant/{lan}/complex")
    Call<List<Restaurant>> getRestaurantByComplexQuery(@Path("lan") String lan, @Query("loc") String loc, @Query("cat") String cat, @Query("min") String min, @Query("max") String max, @Query("grade") String grade);

    @GET("restaurant/{lan}/grade")
    Call<List<Restaurant>> getGradeDescription(@Path("lan") String lan, @Query("grade") String grade);
}
