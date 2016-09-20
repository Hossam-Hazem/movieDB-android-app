package layout;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.moviedb.R;
import com.example.android.moviedb.Review;

import java.util.ArrayList;


public class MovieReviewsFragment extends DialogFragment {

    private ReviewsAdapter mReviewsAdapter;

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
        mReviewsAdapter = new ReviewsAdapter(getActivity());


    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList<Review> reviewsList = (ArrayList<Review>) getArguments().getSerializable("reviews");
        mReviewsAdapter.addAll(reviewsList);
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
        if(mReviewsAdapter.isEmpty()){
            dialog.setMessage("No reviews available");
        }
        ListView listView = dialog.getListView();
        listView.setDivider(new ColorDrawable(Color.TRANSPARENT));
        listView.setDividerHeight(this.getDividerValue(10));
        return dialog;
    }

    private int getDividerValue(int dp){
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    public class ReviewsAdapter extends BaseAdapter {

        ArrayList<Review> reviews;
        Context mContext;

        public ReviewsAdapter(Context c){
            reviews = new ArrayList<>();
            mContext = c;
        }

        @Override
        public int getCount() {
            return reviews.size();
        }

        @Override
        public Object getItem(int position) {
            return reviews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void add(Review item){
            reviews.add(item);
            super.notifyDataSetChanged();
        }

        public void addAll(ArrayList<Review> reviewsList){
            reviews.addAll(reviewsList);
            super.notifyDataSetChanged();
        }

        public void clear(){
            reviews.clear();
            super.notifyDataSetChanged();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView authorTextView;
            TextView contentTextView;
            View view;
            LayoutInflater inflater = LayoutInflater.from(mContext);
            if(convertView == null){
                view = inflater.inflate(R.layout.list_item_reviews,parent,false);

            }
            else{
                view = convertView;
            }
            authorTextView = (TextView) view.findViewById(R.id.list_item_reviews_author);
            contentTextView = (TextView) view.findViewById(R.id.list_item_reviews_content);
            Review review = reviews.get(position);
            contentTextView.setText(review.getContent());
            authorTextView.setText(review.getAuthor());

            return view;
        }
    }

}
