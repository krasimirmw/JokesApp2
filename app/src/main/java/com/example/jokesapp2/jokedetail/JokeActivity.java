package com.example.jokesapp2.jokedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jokesapp2.R;

import retrofit2.HttpException;

public class JokeActivity extends AppCompatActivity implements JokeContract.View {

    // String used for Intent purposes
    public static final String CATEGORY_NAME = "category";

    // Presenter for handling business logic
    private JokeContract.Presenter presenter;

    private Toolbar toolbar;

    // Progressbar for displaying
    private ProgressBar progressBar;

    // Textview for displaying the joke
    private TextView jokeTextView;

    // Icon from the Api
    private ImageView icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        String category = getIntent().getStringExtra(CATEGORY_NAME);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        jokeTextView = findViewById(R.id.textView);
        Button nextJokeButton = findViewById(R.id.button);
        icon = findViewById(R.id.imageView);

        nextJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.requestDataFromServer(category);
            }
        });

        presenter = new JokePresenter(this, new JokeInteractor());
        presenter.requestDataFromServer(category);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    /* Sets toolbar title to Category name
    * Updates jokeTextView with Joke text
    * Loads drawable from Api via Glide
    * */
    @Override
    public void setData(String category, String jokeString, String drawableIcon) {
        toolbar.setTitle(category.toUpperCase());
        jokeTextView.setText(jokeString);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_sentiment_satisfied_black_24dp)
                .error(R.drawable.ic_sentiment_very_dissatisfied_black_24dp).override(200, 200).centerCrop();
        Glide.with(this).load(drawableIcon).apply(options).into(icon);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        // In case error is of Http type then return proper Error type and code
        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            switch (exception.code()) {
                case 400:
                    jokeTextView.setText(R.string.error_400 + exception.code());
                    break;
                case 500:
                    jokeTextView.setText(R.string.error_500 + exception.code());
                    break;
                default:
                    jokeTextView.setText(R.string.error);
                    break;
            }
        } else {
            jokeTextView.setText(R.string.error);
            // Request Options for Glide configuration
            RequestOptions options = new RequestOptions().override(200, 200).centerCrop();
            // Displays error image via Glide
            Glide.with(this).load(R.drawable.ic_sentiment_very_dissatisfied_black_24dp).apply(options).into(icon);
        }
        // Displays toast with error message
        Toast.makeText(JokeActivity.this,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }
}
