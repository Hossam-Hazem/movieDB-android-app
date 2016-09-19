package layout;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.moviedb.R;
import com.example.android.moviedb.Trailer;

import java.io.Serializable;
import java.util.ArrayList;


public class MovieTrailersFragment extends DialogFragment {

    MovieDetailFragment.TrailersAdapter trailersAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        //this.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        trailersAdapter = (MovieDetailFragment.TrailersAdapter) getArguments().getSerializable("trailers");
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragment =  inflater.inflate(R.layout.fragment_movie_trailers, container, false);
        ListView listView= (ListView) fragment.findViewById(R.id.listView_trailers);
        listView.setAdapter(trailersAdapter);

        return fragment;
    }



}
