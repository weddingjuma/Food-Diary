package com.randomappsinc.foodjournal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.IoniconsIcons;
import com.randomappsinc.foodjournal.Adapters.UserRestaurantsAdapter;
import com.randomappsinc.foodjournal.R;
import com.randomappsinc.foodjournal.Utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RestaurantsActivity extends StandardActivity {

    public static final int ADD_RESTAURANT_CODE = 1;
    public static final int VIEW_RESTAURANT_CODE = 2;

    @BindView(R.id.parent) View mParent;
    @BindView(R.id.search_input) EditText mSearchInput;
    @BindView(R.id.clear_search) View mClearSearch;
    @BindView(R.id.no_results) TextView mNoResults;
    @BindView(R.id.restaurants) ListView mRestaurantsList;
    @BindView(R.id.add_restaurant) FloatingActionButton mAddRestaurant;

    private UserRestaurantsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurants);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddRestaurant.setImageDrawable(new IconDrawable(this, IoniconsIcons.ion_android_add).colorRes(R.color.white));

        mAdapter = new UserRestaurantsAdapter(this, mNoResults);
        mRestaurantsList.setAdapter(mAdapter);
    }

    @OnTextChanged(value = R.id.search_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onSearchTermChanged(Editable input) {
        mAdapter.resyncWithDB(input.toString());
        if (input.length() == 0) {
            mClearSearch.setVisibility(View.GONE);
        } else {
            mClearSearch.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.clear_search)
    public void clearSearch() {
        mSearchInput.setText("");
    }

    @OnClick(R.id.add_restaurant)
    public void addRestaurant() {
        startActivityForResult(new Intent(this, FindRestaurantActivity.class), ADD_RESTAURANT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Restaurant successfully added, refresh list to reflect that
        if (requestCode == ADD_RESTAURANT_CODE && resultCode == RESULT_OK) {
            mAdapter.resyncWithDB(mSearchInput.getText().toString());
            UIUtils.showSnackbar(mParent, getString(R.string.restaurant_added));
        }
    }
}