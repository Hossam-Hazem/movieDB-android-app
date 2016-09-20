package layout;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.TypedValue;
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
import java.util.List;


public class MovieTrailersFragment extends DialogFragment {

    MovieDetailFragment.TrailersAdapter trailersAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
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
        trailersAdapter = (MovieDetailFragment.TrailersAdapter) getArguments().getSerializable("trailers");
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.CustomDialog));
        builder
                .setTitle("Trailers")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setAdapter(trailersAdapter,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Trailer trailer = (Trailer) trailersAdapter.getItem(which);
                        Uri  uri = Uri.parse("http://www.youtube.com/watch?v="+trailer.getSource());
                        startActivity(new Intent(Intent.ACTION_VIEW,uri));
                    }
                });
        AlertDialog dialog =  builder.create();
        ListView listView = dialog.getListView();
        listView.setDivider(new ColorDrawable(Color.TRANSPARENT));
        listView.setDividerHeight(this.getDividerValue(10));
        return dialog;
    }


    private int getDividerValue(int dp){
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
