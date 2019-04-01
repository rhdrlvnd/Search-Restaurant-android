package yellow7918.ajou.ac.michelin_guide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.materialrangebar.RangeBar;

public class MainActivity extends AppCompatActivity implements OnRestaurantClickListener {

    public static String language = "ko";

    private DrawerLayout drawerLayout;
    private RestaurantFragment fragment;
    private long pressedTime;

    private RadioButton alphabetOrder;
    private RadioButton priceOrder;
    private RadioButton gradeOrder;

    private ActionBarDrawerToggle toggle;
    private LinearLayout searchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/BMYEONSUNG.otf");
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setTypeface(font);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchLayout = findViewById(R.id.search_layout);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        int bg = (int) (Math.random() * 4);
        Drawable d;
        switch (bg) {
            case 0:
                d = getDrawable(R.drawable.bg1);
                break;
            case 1:
                d = getDrawable(R.drawable.bg2);
                break;
            case 2:
                d = getDrawable(R.drawable.bg3);
                break;
            case 3:
                d = getDrawable(R.drawable.bg4);
                break;
            default:
                d = getDrawable(R.drawable.bg1);
                break;
        }
        navigationView.getHeaderView(0).setBackground(d);

        fragment = new RestaurantFragment();
        fragment.updateListAll(MainActivity.language);
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();

        Drawable menu = getDrawable(R.drawable.ic_menu);
        menu.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        toggle.setHomeAsUpIndicator(menu);
        toggle.syncState();

        CheckBox grade1 = findViewById(R.id.grade_1);
        CheckBox grade2 = findViewById(R.id.grade_2);
        CheckBox grade3 = findViewById(R.id.grade_3);
        CheckBox grade4 = findViewById(R.id.grade_4);
        CheckBox grade5 = findViewById(R.id.grade_5);
        CheckBox[] grades = {grade1, grade2, grade3, grade4, grade5};

        grade1.setOnClickListener(view -> {
            for (int i = 0; i < grades.length; i++)
                if (i != 0)
                    grades[i].setChecked(false);
        });

        grade2.setOnClickListener(view -> {
            for (int i = 0; i < grades.length; i++)
                if (i != 1)
                    grades[i].setChecked(false);
        });

        grade3.setOnClickListener(view -> {
            for (int i = 0; i < grades.length; i++)
                if (i != 2)
                    grades[i].setChecked(false);
        });

        grade4.setOnClickListener(view -> {
            for (int i = 0; i < grades.length; i++)
                if (i != 3)
                    grades[i].setChecked(false);
        });

        grade5.setOnClickListener(view -> {
            for (int i = 0; i < grades.length; i++)
                if (i != 4)
                    grades[i].setChecked(false);
        });


        Button confirm = findViewById(R.id.confirm);

        LinearLayout filters = findViewById(R.id.filter_result);

        EditText location = findViewById(R.id.text_location);
        TextView locationTextView = new TextView(getApplicationContext());
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filters.removeView(locationTextView);
                locationTextView.setText(String.format("%s : %s", getString(R.string.string_simple_category2), charSequence));
                locationTextView.setTextSize(16);
                locationTextView.setTextColor(getColor(R.color.colorPrimary));
                filters.addView(locationTextView);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        EditText category = findViewById(R.id.text_category);
        TextView categoryTextView = new TextView(getApplicationContext());
        category.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filters.removeView(categoryTextView);
                categoryTextView.setText(String.format("%s : %s", getString(R.string.string_simple_category3), charSequence));
                categoryTextView.setTextSize(16);
                categoryTextView.setTextColor(getColor(R.color.colorPrimary));
                filters.addView(categoryTextView);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        RangeBar money = findViewById(R.id.money);
        TextView moneyTextView = new TextView(getApplicationContext());
        money.setOnRangeBarChangeListener((rangeBar, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue) -> {
            filters.removeView(moneyTextView);
            moneyTextView.setText(String.format("%s : %d ~ %d", getString(R.string.string_money2), Integer.parseInt(leftPinValue) * 10000, Integer.parseInt(rightPinValue) * 10000));
            moneyTextView.setTextSize(16);
            moneyTextView.setTextColor(getColor(R.color.colorPrimary));
            filters.addView(moneyTextView);
        });

        String[] spinnerList = {getString(R.string.string_simple_category1), getString(R.string.string_simple_category2), getString(R.string.string_simple_category3)};
        SpinnerAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerList);
        Spinner spinner = findViewById(R.id.spinner_category);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);

        alphabetOrder = findViewById(R.id.filter_character);
        priceOrder = findViewById(R.id.filter_money);
        gradeOrder = findViewById(R.id.filter_grade);

        alphabetOrder.setOnClickListener(v -> {
            alphabetOrder.setTextColor(Color.WHITE);
            priceOrder.setTextColor(Color.BLACK);
            gradeOrder.setTextColor(Color.BLACK);
            fragment.sortItemByAlphabet();
        });

        priceOrder.setOnClickListener(v -> {
            priceOrder.setTextColor(Color.WHITE);
            gradeOrder.setTextColor(Color.BLACK);
            alphabetOrder.setTextColor(Color.BLACK);
            fragment.sortItemByPrice();
        });

        gradeOrder.setOnClickListener(v -> {
            gradeOrder.setTextColor(Color.WHITE);
            priceOrder.setTextColor(Color.BLACK);
            alphabetOrder.setTextColor(Color.BLACK);
            fragment.sortItemByGrade();
        });

        EditText editText = findViewById(R.id.text_search);
        editText.getBackground().setColorFilter(getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


        ImageView searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(view -> {
            String searchData = editText.getText().toString();
            if (searchData.length() <= 0) {
                Snackbar.make(view, getString(R.string.string_input_warning), Snackbar.LENGTH_SHORT).show();
                return;
            }

            String loc = null;
            String cat = null;
            String name = null;
            String type = ((String) spinner.getSelectedItem());

            if (type.contains("요") || type.contains("Dish"))
                cat = searchData;
            else if (type.contains("식") || type.contains("Name"))
                name = searchData;
            else if (type.contains("지") || type.contains("Loc"))
                loc = searchData;

            fragment.updateListBySimpleQuery(MainActivity.language, loc, cat, name);
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        confirm.setOnClickListener(view -> {
            String loc = location.getText().toString();
            String cat = category.getText().toString();
            String min = String.valueOf(Integer.parseInt(money.getLeftPinValue()) * 10000);
            String max = String.valueOf(Integer.parseInt(money.getRightPinValue()) * 10000);
            String grade = "-1";
            for (int i = 0; i < grades.length; i++) {
                if (grades[i].isChecked()) {
                    grade = String.valueOf(i + 1);
                    break;
                }
            }

            fragment.updateListByComplexQuery(MainActivity.language, loc, cat, min, max, grade);
            drawerLayout.closeDrawer(GravityCompat.START);
        });


        Button changeLocale = navigationView.getHeaderView(0).findViewById(R.id.change_locale);
        changeLocale.setOnClickListener(v -> {
            language = language.equals("en") ? "ko" : "en";
            recreate();
            getSupportFragmentManager().beginTransaction().detach(getSupportFragmentManager().getFragments().get(0)).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reset) {
            fragment.updateListAll(MainActivity.language);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                alphabetOrder.performClick();
                showSearch();
                super.onBackPressed();
                return;
            }
            if (pressedTime == 0) {
                Toast.makeText(MainActivity.this, getString(R.string.string_close_warning), Toast.LENGTH_LONG).show();
                pressedTime = System.currentTimeMillis();
            } else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if (seconds > 2000) {
                    Toast.makeText(MainActivity.this, getString(R.string.string_close_warning), Toast.LENGTH_LONG).show();
                    pressedTime = 0;
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    @Override
    public void onClickRestaurant(Restaurant restaurant) {
        hideSearch();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, RestaurantDetailFragment.newInstance(restaurant)).addToBackStack(null).commit();
    }

    private void hideSearch() {
        searchLayout.setVisibility(View.GONE);
        Drawable menu = getDrawable(R.drawable.ic_nothing);
        toggle.setHomeAsUpIndicator(menu);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
    }

    private void showSearch() {
        searchLayout.setVisibility(View.VISIBLE);
        toggle.setDrawerIndicatorEnabled(true);
        Drawable menu = getDrawable(R.drawable.ic_menu);
        menu.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        toggle.setHomeAsUpIndicator(menu);
        toggle.syncState();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase, language));
    }
}
