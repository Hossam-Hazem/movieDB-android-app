package layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviedb.MainActivity;
import com.example.android.moviedb.MovieContentConnector;
import com.example.android.moviedb.MovieItem;
import com.example.android.moviedb.MovieParentActivity;
import com.example.android.moviedb.R;
import com.example.android.moviedb.Review;
import com.example.android.moviedb.Trailer;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.android.moviedb.MovieDetailActivity.MOVIE_SERIALIZABLE_KEY;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment implements View.OnClickListener {

    MovieItem movieDetails;
    View fragmentView;
    boolean isFavorite;
    boolean twoPane;
    ArrayList<Trailer> trailersList;
    ArrayList<Review> reviewsList;
    ShareActionProvider mShareActionProvider;


    public MovieDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        trailersList = new ArrayList<>();
        reviewsList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!getArguments().containsKey("movieDetails")) {
            return null;
        }
        twoPane = getArguments().getBoolean("twoPane");
        fragmentView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        AppCompatActivity currentActivity = (AppCompatActivity) getActivity();
        if (!twoPane) {
            Toolbar toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbar);
            currentActivity.setSupportActionBar(toolbar);
            currentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                fragmentView.findViewById(R.id.movie_detail_container).setFitsSystemWindows(true);
            }
        }

        movieDetails = (MovieItem) getArguments().getSerializable("movieDetails");
        isFavorite = movieDetails.isFavorite(getContext());

        MovieContentConnector connector = new MovieContentConnector(trailersList, reviewsList, new MovieContentConnector.CallbackInterface() {
            @Override
            public void getFirstTrailer(Trailer trailer) {
                if(trailer == null){
                    Toast.makeText(getContext(),"No Trailers Found!",Toast.LENGTH_LONG).show();
                }
                else
                    setShareProviderIntent(trailer);
            }
        });
        connector.execute(movieDetails.getId() + "");

//        setToolbar();


        linkMovieDetailsUI();


        ((FloatingActionButton) fragmentView.findViewById(R.id.favorite_button)).setOnClickListener(this);
        ((Button) fragmentView.findViewById(R.id.movie_trailers_button)).setOnClickListener(this);
        ((Button) fragmentView.findViewById(R.id.movie_reviews_button)).setOnClickListener(this);
        ((ImageView) fragmentView.findViewById(R.id.backdrop)).setOnClickListener(this);

        setFavButton(fragmentView);

        return fragmentView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailfragment, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

    }

    public void setShareProviderIntent(Trailer trailer) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(
                    ((MovieParentActivity) getActivity())
                            .createShareMovieIntent(
                                    movieDetails.getName(), trailer.getURL()));

        } else {
            Log.d(MovieDetailFragment.class.getSimpleName(), "Share Action Provider is null?");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favorite_button: {
                if (isFavorite) {
                    removeMovieFavorite(v);
                } else {
                    setMovieFavorite(v);
                }
                break;
            }
            case R.id.movie_reviews_button: {
                Bundle bundle = new Bundle();
                bundle.putSerializable("reviews", reviewsList);
                ((MovieParentActivity) getActivity()).openReviewsFragment(bundle);
                break;
            }
            case R.id.movie_trailers_button: {
                Bundle bundle = new Bundle();
                bundle.putSerializable("trailers", trailersList);
                ((MovieParentActivity) getActivity()).openTrailersFragment(bundle);
                break;
            }
            case R.id.backdrop: {
                Bundle bundle = new Bundle();
                bundle.putString("uri", movieDetails.getImageBackDropURL());
                ((MovieParentActivity) getActivity()).openImageFragment(bundle);
                break;
            }

        }
    }

    private void setToolbar() {
//        final Toolbar toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void linkMovieDetailsUI() {
        if (movieDetails != null) {
            setMovieTitle();
            loadBackdrop();
            setMovieSynopsis();
            setMovieReleaseDate();
            setMovieRating();
        }
    }

    private void setMovieSynopsis() {
        final TextView textView = (TextView) fragmentView.findViewById(R.id.movieDescription);
        textView.setText(movieDetails.getDescription());
    }

    private void setMovieReleaseDate() {
        final TextView textView = (TextView) fragmentView.findViewById(R.id.movieReleaseDate);
        textView.setText(movieDetails.getDate());
    }

    private void setMovieRating() {
        final RatingBar ratingBar = (RatingBar) fragmentView.findViewById(R.id.ratingBar);
        float tenBasedRating = movieDetails.getRating();
        float fiveBasedRating = movieDetails.getRating() / 2;
        ratingBar.setRating(fiveBasedRating);
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) fragmentView.findViewById(R.id.backdrop);
        Picasso.with(getActivity()).load(movieDetails.getImageBackDropURL()).into(imageView);
    }

    private void setMovieTitle() {
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) fragmentView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(movieDetails.getName());
    }


    private void setFavButtonUnFavorite(View v) {
        FloatingActionButton button = (FloatingActionButton) v.findViewById(R.id.favorite_button);

        button.setImageDrawable(getResources().getDrawable(R.drawable.ic_unfavorite));

        setFavButtonBackground(button, 1);
    }

    private void setFavButtonFavorite(View v) {
        FloatingActionButton button = (FloatingActionButton) v.findViewById(R.id.favorite_button);

        button.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));

        setFavButtonBackground(button, 0);
    }

    private void setFavButton(View v, int mode) {
        switch (mode) {
            case 0:
                setFavButtonFavorite(v);
                break;
            case 1:
                setFavButtonUnFavorite(v);
                break;
        }
    }

    private void setFavButtonBackground(FloatingActionButton button, int mode) {
        switch (mode) {
            case 0:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    button.getBackground().setColorFilter(Color.parseColor("#ff80cbc4"), PorterDuff.Mode.MULTIPLY);
                break;
            case 1: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    button.getBackground().setColorFilter(Color.parseColor("#ff009688"), PorterDuff.Mode.MULTIPLY);
                break;
            }

        }
    }

    private void setFavButton(View v) {
        if (isFavorite) {
            setFavButton(v, 1);
        } else {
            setFavButton(v, 0);
        }
    }

    public void setMovieFavorite(View v) {
        boolean isSuccess = movieDetails.setFavorite(getContext());
        if (isSuccess) {
            isFavorite = true;
            if (twoPane)
                ((MainActivity) getActivity()).addMovieToFavorites(movieDetails);
            setFavButton(v);
        } else {
            throw new UnsupportedOperationException("error fel insert favorite");
        }
    }

    public void removeMovieFavorite(View v) {
        boolean isSuccess = movieDetails.removeFavorite(getContext());

        if (isSuccess) {
            isFavorite = false;
            if (twoPane)
                ((MainActivity) getActivity()).removeMovieFromFavorites(movieDetails);
            setFavButton(v);
        } else {
            throw new UnsupportedOperationException("error fel delete favorite");
        }
    }

    public static MovieDetailFragment newFragmentWithBundle(MovieItem movieItem, boolean twoPane) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("twoPane", twoPane);
        bundle.putSerializable(MOVIE_SERIALIZABLE_KEY, movieItem);
        fragment.setArguments(bundle);
        return fragment;
    }


}
