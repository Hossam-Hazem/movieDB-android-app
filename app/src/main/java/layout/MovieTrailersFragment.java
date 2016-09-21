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

    private TrailersAdapter trailersAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        trailersAdapter = new TrailersAdapter(getActivity());
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
        ArrayList<Trailer> trailers = (ArrayList<Trailer>) getArguments().getSerializable("trailers");
        trailersAdapter.addAll(trailers);
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
                        Uri  uri = Uri.parse(trailer.getURL());
                        startActivity(new Intent(Intent.ACTION_VIEW,uri));
                    }
                });
        AlertDialog dialog =  builder.create();
        if(trailersAdapter.isEmpty()){
            dialog.setMessage("No trailers available");
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
    public class TrailersAdapter extends BaseAdapter {

        ArrayList<Trailer> trailers;
        Context mContext;

        public TrailersAdapter(Context c){
            trailers = new ArrayList<>();
            mContext = c;
        }

        @Override
        public int getCount() {
            return trailers.size();
        }

        @Override
        public Object getItem(int position) {
            return trailers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void add(Trailer item){
            trailers.add(item);
            super.notifyDataSetChanged();
        }

        public void addAll(ArrayList<Trailer> trailersList){
            trailers.addAll(trailersList);
            super.notifyDataSetChanged();
        }

        public void clear(){
            trailers.clear();
            super.notifyDataSetChanged();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            View view;
            LayoutInflater inflater = LayoutInflater.from(mContext);
            if(convertView == null){
                view = inflater.inflate(R.layout.list_item_trailers,parent,false);

            }
            else{
                view = convertView;
            }
            textView = (TextView) view.findViewById(R.id.list_item_trailers_textView);
            Trailer trailer = trailers.get(position);
            textView.setText(trailer.getName());
            return view;
        }
    }
}
