package layout;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.moviedb.R;

import java.util.ArrayList;


public class MovieReviewsFragment extends DialogFragment {

    private ArrayAdapter<String> mReviewsAdapter;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_movie_reviews, container, false);

        getDialog().setTitle("Reviews");

        mReviewsAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.fragment_movie_review_item, // The name of the layout ID.
                        R.id.fragment_movie_review_item_textView, // The ID of the textview to populate.
                        new ArrayList<String>());

        ListView listView = (ListView) fragment.findViewById(R.id.listview_reviews);
        listView.setAdapter(mReviewsAdapter);
        mReviewsAdapter.add("aaaaaaaaaaaaaaaaaaaaa");
        mReviewsAdapter.add("bbbbbbbbbbbbbbbbbbbbb");
        mReviewsAdapter.add("ccccccccccccccccccccc");
        mReviewsAdapter.add("ddddddddddddddddddddd");
        mReviewsAdapter.add("eeeeeeeeeeeeeeeeeeeee");
        mReviewsAdapter.add("fffffffffffffffffffff");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");
        mReviewsAdapter.add("ggggggggggggggggggggg");



        return fragment;
    }




}
