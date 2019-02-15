package com.example.jokesapp2.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jokesapp2.R;
import com.example.jokesapp2.jokedetail.JokeActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class CategoryActivity extends DaggerAppCompatActivity implements CategoryContract.View {

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @BindView(R.id.refreshButton)
    public Button refreshButton;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    // Presenter used for handling business logic
    @Inject
    CategoryContract.Presenter presenter;

    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DaggerCategoryComponent.create(this).inject(this);
       // DaggerCategoryComponent.builder().build().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Sets Support action bar to toolbar
        setSupportActionBar(toolbar);
        // Sets recyclerview layout to Gridlayout with 4 col spans

        categoryAdapter = new CategoryAdapter(this, new ArrayList<>(), recyclerItemClickListener);
        recyclerView.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 4));
        recyclerView.setAdapter(categoryAdapter);

        //presenter = new CategoryPresenter(this, new CategoryInteractor());
        presenter.requestDataFromServer();
    }

    /**
     * RecyclerItem click event listener
     */
    private RecyclerItemClickListener recyclerItemClickListener = category -> {
        Intent intent = new Intent(CategoryActivity.this, JokeActivity.class);
        intent.putExtra(JokeActivity.CATEGORY_NAME, category);
        startActivity(intent);
    };

    /*
     * Sets ProgressBar Visibility to VISIBLE
     */
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /*
     * Sets ProgressBar Visibility to INVISIBLE
     */
    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    /*
     * Sets categories data for RecyclerView and instantiates CategoryAdapter
     */
    @Override
    public void setDataToRecyclerView(String[] categories) {
        categoryAdapter.replaceData(categories);
        if (refreshButton.getVisibility() == View.VISIBLE) {
            refreshButton.setVisibility(View.GONE);
            refreshButton.setOnClickListener(null);
        }
    }

    /*
     * Sets RefreshButton Visibility to VISIBLE
     * Adds ClickListener  for RefreshButton
     * Displays Toast with error
     */
    @Override
    public void onResponseFailure(Throwable throwable) {
        refreshButton.setVisibility(View.VISIBLE);
        refreshButton.setOnClickListener(v -> presenter.requestDataFromServer());
        Toast.makeText(CategoryActivity.this,
                R.string.toast_error_text,
                Toast.LENGTH_LONG).show();
    }
}
