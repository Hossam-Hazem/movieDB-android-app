package layout;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedb.R;


public class MovieTrailersFragment extends DialogFragment {


    public MovieTrailersFragment() {
        // Required empty public constructor
    }

    public static MovieTrailersFragment newInstance() {
        MovieTrailersFragment fragment = new MovieTrailersFragment();
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("My Title");
        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragment =  inflater.inflate(R.layout.fragment_movie_trailers, container, false);

        return fragment;
    }

}
