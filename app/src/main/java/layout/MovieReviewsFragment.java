package layout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.moviedb.R;

import java.util.ArrayList;


public class MovieReviewsFragment extends DialogFragment {

    private MovieDetailFragment.ReviewsAdapter mReviewsAdapter;

    public MovieReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieReviewsFragment newInstance() {
        MovieReviewsFragment fragment = new MovieReviewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);


    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mReviewsAdapter = (MovieDetailFragment.ReviewsAdapter) getArguments().getSerializable("reviews");
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.CustomDialog));
        builder
                .setTitle("Reviews")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setAdapter(mReviewsAdapter,null);
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
