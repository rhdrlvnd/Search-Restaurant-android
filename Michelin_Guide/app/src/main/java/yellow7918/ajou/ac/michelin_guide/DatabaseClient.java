package yellow7918.ajou.ac.michelin_guide;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseClient implements DatabaseAPI {
    private DatabaseAPI api;

    private DatabaseClient() {
        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).build();

        Gson gson = new GsonBuilder().create();
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://18.182.5.115:8888/a/").build();

        api = retrofit.create(DatabaseAPI.class);
    }

    @Override
    public Call<List<Restaurant>> getRestaurant(String lan) {
        return api.getRestaurant(lan);
    }

    @Override
    public Call<List<Restaurant>> getRestaurantBySimpleQuery(String lan, String loc, String cat, String name) {
        return api.getRestaurantBySimpleQuery(lan, loc, cat, name);
    }

    @Override
    public Call<List<Restaurant>> getRestaurantByComplexQuery(String lan, String loc, String cat, String min, String max, String grade) {
        return api.getRestaurantByComplexQuery(lan, loc, cat, min, max, grade);
    }

    @Override
    public Call<List<Restaurant>> getGradeDescription(String lan, String grade) {
        return api.getGradeDescription(lan,grade);
    }

    private static class SingleTon {
        private static final DatabaseClient INSTANCE = new DatabaseClient();
    }

    public static DatabaseClient getInstance() {
        return SingleTon.INSTANCE;
    }
}
