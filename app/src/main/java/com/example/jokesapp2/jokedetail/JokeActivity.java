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
import com.example.jokesapp2.model.datasource.local.JokeDatabase;
import com.example.jokesapp2.model.datasource.local.JokesLocalDataSource;
import com.example.jokesapp2.utils.AppExecutors;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    // Button for requesting new Joke from server
    @BindView(R.id.button_nextJoke)
    Button nextJokeButton;

    // CardView for holding Joke server data
    @BindView(R.id.cardview_joke)
    CardView cardView;

    // ImageButton with two graphic modes used for displaying whether a Joke is saved.
    @BindView(R.id.button_favorite)
    ImageButton favoriteButton;

    // View for Jokes From Db
    @BindView(R.id.contentframe_joke)
    View contentFrameJokesDb;

    // RecyclerView for displaying saved joke items from db
    @BindView(R.id.recyclerview_jokesdb)
    RecyclerView recyclerViewDb;

    // ProgressBar for displaying indicator when database loads
    @BindView(R.id.progressBar_db)
    ProgressBar progressBarDb;

    // TextView for displaying category
    @BindView(R.id.text_category_localdb)
    TextView textViewCategoryDb;

    // String for getting Category from Intent extra
    private String category;

    // Object for storing the current joke from Retrofit body instance
    private Joke currentJoke;

    // Helper for Adding favorite jokes to SharedPreferences
    private JokesHelper jokesHelper;

    // RecyclerViewAdapter for Visualizing saved jokes in db
    private JokesAdapter jokesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setUpRecyclerView();

        category = getIntent().getStringExtra(CATEGORY_NAME);

        JokeDatabase jokeDatabase = JokeDatabase.getInstance(getApplicationContext());
        JokesLocalDataSource jokesLocalDataSource = JokesLocalDataSource.getInstance(new AppExecutors(), jokeDatabase.jokeDAO());
        jokesHelper = new JokesHelper(this);

        presenter = new JokePresenter(this, this, jokesLocalDataSource);

        presenter.loadJokesFromDb(category);
        presenter.requestDataFromServer(category);
    }

    // Sets up RecyclerView Adapter and Manager
    private void setUpRecyclerView() {
        jokesAdapter = new JokesAdapter(new ArrayList<>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewDb.setLayoutManager(layoutManager);
        recyclerViewDb.setAdapter(jokesAdapter);
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
    public void setData(String jokeId, String category, String jokeString, String drawableIcon) {
        toolbar.setTitle(category.toUpperCase());
        boolean isFavored = presenter.isJokeFavoredInPreferences(jokeId);
        currentJoke = new Joke(jokeId, category, jokeString, isFavored);
        jokeTextView.setText(jokeString);
        if (!favoriteButton.isClickable()) {
            favoriteButton.setClickable(true);
        }
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
        }
        // Request Options for Glide configuration
        RequestOptions options = new RequestOptions().override(200, 200).centerCrop();
        // Displays error image via Glide
        Glide.with(this).load(R.drawable.ic_sentiment_very_dissatisfied_black_24dp).apply(options).into(icon);

        // Sets favoriteButton ClickListener to null because no joke can be saved when there is no data
        favoriteButton.setClickable(false);

        // Displays toast with error message
        Toast.makeText(JokeActivity.this,
                R.string.toast_error_text + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    /**
     * Sets visibility of database jokes content frame to Visible.
     * Sets the text of the category heading
     * Replaces RecyclerView Adapter data
     * @param jokesData
     */
    @Override
    public void showDbJokes(List<String> jokesData) {
        contentFrameJokesDb.setVisibility(View.VISIBLE);
        textViewCategoryDb.setText(getString(R.string.joke_category_string, category));
        jokesAdapter.replaceData(jokesData);
    }

    @Override
    public void showNoDbJokes() {
        contentFrameJokesDb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showDbProgress() {
        progressBarDb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDbProgress() {
        progressBarDb.setVisibility(View.INVISIBLE);
    }

    /**
     * Button for requesting new data from server on click
     */
    @OnClick(R.id.button_nextJoke)
    public void onNextJokeClicked() {
        jokeTextView.setText("");
        presenter.requestDataFromServer(category);
    }

    /**
     * ImageButton for saving and deleting joke data to SharedPreference and Database
     */
    @OnClick(R.id.button_favorite)
    public void onFavoredButtonClicked() {
        boolean favored = !currentJoke.isFavored();
        favoriteButton.setSelected(favored);
        jokesHelper.setJokeFavored(currentJoke, favored);
        if (favored) {
            presenter.saveJokeToDB(currentJoke);
            presenter.loadJokesFromDb(category);
            Toast.makeText(JokeActivity.this, R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
        } else {
            presenter.deleteJokeFromDB(currentJoke);
            presenter.loadJokesFromDb(category);
            Toast.makeText(JokeActivity.this, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
        }
    }
}
