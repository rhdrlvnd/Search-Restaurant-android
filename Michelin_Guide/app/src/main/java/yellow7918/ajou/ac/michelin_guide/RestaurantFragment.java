package yellow7918.ajou.ac.michelin_guide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RestaurantFragment extends Fragment {

    private RecyclerView recyclerView;
    private RestaurantListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RestaurantListAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        adapter.setListener((MainActivity) getActivity());
        new Thread(() -> {
            try {
                List<Restaurant> list = DatabaseClient.getInstance().getRestaurant(MainActivity.language).execute().body();
                adapter.setRestaurantList(list);
                getActivity().runOnUiThread(adapter::notifyDataSetChanged);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        return view;
    }

    public void updateListBySimpleQuery(String lan, String loc, String cat, String name) {
        new Thread(() -> {
            try {
                List<Restaurant> list = DatabaseClient.getInstance().getRestaurantBySimpleQuery(lan, loc, cat, name).execute().body();
                adapter.setRestaurantList(list);
                Objects.requireNonNull(getActivity()).runOnUiThread(adapter::notifyDataSetChanged);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void updateListByComplexQuery(String lan, String loc, String cat, String min, String max, String grade) {
        new Thread(() -> {
            try {
                List<Restaurant> list = DatabaseClient.getInstance().getRestaurantByComplexQuery(lan, loc, cat, min, max, grade).execute().body();
                adapter.setRestaurantList(list);
                Objects.requireNonNull(getActivity()).runOnUiThread(adapter::notifyDataSetChanged);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void updateListAll(String lan) {
        new Thread(() -> {
            try {
                List<Restaurant> list = DatabaseClient.getInstance().getRestaurant(lan).execute().body();
                adapter.setRestaurantList(list);
                Objects.requireNonNull(getActivity()).runOnUiThread(adapter::notifyDataSetChanged);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sortItemByAlphabet() {
        List<Restaurant> list = adapter.getRestaurantList();
        Collections.sort(list, (o1, o2) -> o1.getrName().compareTo(o2.getrName()));
        adapter.setRestaurantList(list);
        adapter.notifyDataSetChanged();
    }

    public void sortItemByPrice() {
        List<Restaurant> list = adapter.getRestaurantList();
        Collections.sort(list, (o1, o2) -> Integer.compare(o2.getPrice(), o1.getPrice()));
        adapter.setRestaurantList(list);
        adapter.notifyDataSetChanged();
    }

    public void sortItemByGrade() {
        List<Restaurant> list = adapter.getRestaurantList();
        Collections.sort(list, (o1, o2) -> Integer.compare(o1.getGrade(), o2.getGrade()));
        adapter.setRestaurantList(list);
        adapter.notifyDataSetChanged();
    }
}
