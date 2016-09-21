package layout;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.moviedb.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieImageFragment extends DialogFragment {


    public MovieImageFragment() {
        // Required empty public constructor
    }

    public static MovieImageFragment newInstance() {
        MovieImageFragment fragment = new MovieImageFragment();
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movie_image, container, false);
        String uri = getArguments().getString("uri");
        ImageView imageView = ((ImageView) view.findViewById(R.id.movie_image_view));
        Picasso.with(getContext()).load(uri).into(imageView);

        return view;
    }

}
