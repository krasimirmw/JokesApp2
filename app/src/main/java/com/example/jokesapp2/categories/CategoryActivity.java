package com.example.jokesapp2.categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jokesapp2.R;
import com.example.jokesapp2.jokedetail.JokeActivity;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.View {

    private ProgressBar progressBar;
    private Button refreshButton;
    private RecyclerView recyclerView;

    // Presenter used for handling business logic
    private CategoryContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeToolbar();
        initializeRecyclerView();
        initializeProgressBarAndRefreshButton();

        presenter = new CategoryPresenter(this, new CategoryInteractor());
        presenter.requestDataFromServer();
    }

    /*
     * Initializes the Toolbar
     */
    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /*
     * Initializes the RecyclerView
     */
    public void initializeRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);

        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 4));
    }

    /*
     * Initializes the Progressbar and RefreshButton
     */
    private void initializeProgressBarAndRefreshButton() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout, params);

        refreshButton = findViewById(R.id.refreshButton);
    }

    /**
     * RecyclerItem click event listener
     */
    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(String category) {
            Intent intent = new Intent(CategoryActivity.this, JokeActivity.class);
            intent.putExtra(JokeActivity.CATEGORY_NAME, category);
            startActivity(intent);
        }
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
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories, recyclerItemClickListener);
        recyclerView.setAdapter(categoryAdapter);
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
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.requestDataFromServer();
            }
        });
        Toast.makeText(CategoryActivity.this,
                R.string.toast_error_text,
                Toast.LENGTH_LONG).show();
    }
}
