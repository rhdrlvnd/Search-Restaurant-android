package yellow7918.ajou.ac.michelin_guide;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    private TextView titleText;
    private TextView categoryText;
    private ImageView imageView;

    public RestaurantViewHolder(View itemView) {
        super(itemView);

        titleText = itemView.findViewById(R.id.text_title);
        categoryText = itemView.findViewById(R.id.text_category);
        imageView = itemView.findViewById(R.id.image_project);
    }

    void setProjectView(Restaurant restaurant) {
        titleText.setText(restaurant.getrName());
        categoryText.setText(restaurant.getCategory());

        Glide.with(this.imageView)
                .load(restaurant.getImgRsc1())
                .apply(new RequestOptions()
                        .centerCrop())
                .into(imageView);
    }

}
