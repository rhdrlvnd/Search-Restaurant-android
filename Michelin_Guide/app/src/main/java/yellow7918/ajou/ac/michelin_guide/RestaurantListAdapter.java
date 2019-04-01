package yellow7918.ajou.ac.michelin_guide;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {
    private List<Restaurant> restaurantList;
    private OnRestaurantClickListener listener;

    public void setListener(OnRestaurantClickListener listener) {
        this.listener = listener;
    }

    public RestaurantListAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.setProjectView(restaurant);
        holder.itemView.setOnClickListener(view -> listener.onClickRestaurant(restaurantList.get(position)));
    }

    @Override
    public int getItemCount() {
        if (restaurantList != null)
            return restaurantList.size();
        else
            return 0;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }
}
