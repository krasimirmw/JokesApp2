package com.example.jokesapp2.jokedetail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jokesapp2.R;
import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.model.local.JokeDatabase;
import com.example.jokesapp2.model.local.JokesLocalDataSource;
import com.example.jokesapp2.utils.AppExecutors;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.HttpException;

public class JokeActivity extends AppCompatActivity implements JokeContract.View {

    // String used for Intent purposes
    public static final String CATEGORY_NAME = "category";

    // Presenter for handling business logic
    private JokeContract.Presenter presenter;

    // JokeActivity toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    // Progressbar for displaying
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    // Textview for displaying the joke
    @BindView(R.id.text_joke)
    TextView jokeTextView;

    // Icon from the Api
    @BindView(R.id.image_joke)
    ImageView icon;

    @BindView(R.id.button_nextJoke)
    Button nextJokeButton;

    @BindView(R.id.cardview_joke)
    CardView cardView;

    @BindView(R.id.button_favorite)
    ImageButton favoriteButton;

    private String category;
    private Joke currentJoke;
    private JokesHelper jokesHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        category = getIntent().getStringExtra(CATEGORY_NAME);

        JokeDatabase jokeDatabase = JokeDatabase.getInstance(getApplicationContext());
        JokesLocalDataSource jokesLocalDataSource = JokesLocalDataSource.getInstance(new AppExecutors(), jokeDatabase.jokeDAO());
        jokesHelper = new JokesHelper(this, jokesLocalDataSource);

        presenter = new JokePresenter(this, jokesLocalDataSource);

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
    public void setData(String jokeId, String category, String jokeString, String drawableIcon, boolean isFavored) {
        toolbar.setTitle(category.toUpperCase());
        currentJoke = new Joke(jokeId, jokeString, isFavored);
        jokeTextView.setText(jokeString);
        favoriteButton.setSelected(isFavored);
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
                R.string.toast_error_text + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.button_nextJoke)
    public void onNextJokeClicked() {
        jokeTextView.setText("");
        presenter.requestDataFromServer(category);
    }

    @OnClick(R.id.button_favorite)
    public void onFavoredButtonClicked() {
        boolean favored = !currentJoke.isFavored();
        favoriteButton.setSelected(favored);
        jokesHelper.setJokeFavored(currentJoke, favored);
        if (favored) {
            presenter.saveJokeToDB(currentJoke);
            Toast.makeText(JokeActivity.this, R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
        } else {
            presenter.deleteJokeFromDB(currentJoke);
            Toast.makeText(JokeActivity.this, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
        }
    }
}
