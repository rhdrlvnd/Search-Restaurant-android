package yellow7918.ajou.ac.michelin_guide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class RestaurantDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        Restaurant restaurant = (Restaurant) getArguments().getSerializable("RESTAURANT");

        TextView gradeDesc = view.findViewById(R.id.text_detail_grade);
        ImageView gradeImage = view.findViewById(R.id.text_detail_grade_image);
        new Thread(() -> {
            try {
                List<Restaurant> grade = DatabaseClient.getInstance().getGradeDescription(MainActivity.language, String.valueOf(restaurant.getGrade())).execute().body();
                System.out.println(grade.get(0).getGradeDesc());
                Drawable img;
                switch (restaurant.getGrade()) {
                    case 1:
                        img = getActivity().getDrawable(R.drawable.grade1);
                        break;
                    case 2:
                        img = getActivity().getDrawable(R.drawable.grade2);
                        break;
                    case 3:
                        img = getActivity().getDrawable(R.drawable.grade3);
                        break;
                    case 4:
                        img = getActivity().getDrawable(R.drawable.grade4);
                        break;
                    case 5:
                        img = getActivity().getDrawable(R.drawable.grade5);
                        break;
                    default:
                        img = getActivity().getDrawable(R.drawable.grade4);
                        break;
                }

                Objects.requireNonNull(getActivity()).runOnUiThread(() -> gradeImage.setImageDrawable(img));
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> gradeDesc.setText(grade.get(0).getGradeDesc()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println(restaurant.toString());

        TextView title = view.findViewById(R.id.text_detail_title);
        title.setText(restaurant.getrName());

        TextView category = view.findViewById(R.id.text_detail_category);
        category.setText(restaurant.getCategory());

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        ImageViewPageAdapter adapter = new ImageViewPageAdapter(getLayoutInflater(), new String[]{restaurant.getImgRsc1(), restaurant.getImgRsc2(), restaurant.getImgRsc3()});
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_dots);
        tabLayout.setupWithViewPager(viewPager);


        TextView avgPrice = view.findViewById(R.id.text_detail_avg_price);
        avgPrice.setText(restaurant.getPrice() + "");

        TextView homepage = view.findViewById(R.id.text_detail_homepage);
        homepage.setText(restaurant.getHomepage());

        TextView phoneNum = view.findViewById(R.id.text_detail_phone_number);
        phoneNum.setText(restaurant.getPhoneNumber());

        return view;
    }

    public static RestaurantDetailFragment newInstance(Restaurant restaurant) {
        RestaurantDetailFragment fragment = new RestaurantDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("RESTAURANT", restaurant);

        fragment.setArguments(bundle);
        return fragment;
    }
}
