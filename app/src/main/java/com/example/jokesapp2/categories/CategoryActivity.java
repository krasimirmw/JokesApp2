package com.example.jokesapp2.categories;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jokesapp2.R;
import com.example.jokesapp2.jokedetail.JokeActivity;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.View {

    @BindView(R.id.progressBar)
    private ProgressBar progressBar;

    @BindView(R.id.refreshButton)
    private Button refreshButton;

    @BindView(R.id.recycler_view)
    private RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    private Toolbar toolbar;

    // Presenter used for handling business logic
    private CategoryContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Sets Support action bar to toolbar
        setSupportActionBar(toolbar);
        // Sets recyclerview layout to Gridlayout with 4 col spans
        recyclerView.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 4));

        presenter = new CategoryPresenter(this, new CategoryInteractor());
        presenter.requestDataFromServer();
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
