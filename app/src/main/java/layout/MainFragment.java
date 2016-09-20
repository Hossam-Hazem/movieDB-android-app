package layout;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.moviedb.MainActivity;
import com.example.android.moviedb.MovieItem;
import com.example.android.moviedb.R;
import com.example.android.moviedb.MoviesListConnector;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    public interface OnFinishAdapterEmpty{
        public void onFinished();
    }
    private MovieAdapter mMoviesAdapter;
    private MoviesListConnector connector;
    private OnFinishAdapterEmpty onFinishAdapterEmpty;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        connector = new MoviesListConnector(getContext(), mMoviesAdapter, new MoviesListConnector.OnFinishCallback() {
            @Override
            public void onFinished(ArrayList<MovieItem> movieItems) {
                if(movieItems.isEmpty()&&onFinishAdapterEmpty!=null) {
                    onFinishAdapterEmpty.onFinished();
                }
                else{
                    ((MainActivity) getActivity()).onAdapterFinish(movieItems);
                }

            }
        });
        getMovies();
    }

    public void getMovies(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int key = Integer.parseInt(prefs.getString(getString(R.string.pref_sort_list_key),"0"));
        switch (key){
            case 0:
                getMostPopularMovies();
                break;
            case 1:
                getTopRatedMovies();
                break;
            case 2:
                getFavoriteMovies();
        }

    }

    public  void getTopRatedMovies(){
        connector.execute("movie/top_rated");
        initOnFinishAdapterEmpty("No movies returned from API");
    }

    public void getMostPopularMovies(){
        connector.execute("movie/popular");
        initOnFinishAdapterEmpty("No movies returned from API");
    }

    public void getFavoriteMovies(){
        connector.execute("favorites");
        initOnFinishAdapterEmpty("You have no favorites");
    }

    public void initOnFinishAdapterEmpty(final String message){
        onFinishAdapterEmpty = new OnFinishAdapterEmpty() {
            @Override
            public void onFinished() {
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
                onFinishAdapterEmpty = null;
            }
        };
    }

    public void addFavoriteToAdapter(MovieItem item){
        mMoviesAdapter.add(item);
    }

    public void removeFavoriteFromAdapter(MovieItem item){
        mMoviesAdapter.remove(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMoviesAdapter = new MovieAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) fragmentView.findViewById(R.id.gird_item_movie);
        gridView.setAdapter(mMoviesAdapter);
        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                String forecast = mForecastAdapter.getItem(position);
//                // Toast.makeText(getActivity(),forecast,Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(),DetailActivity.class)
//                        .putExtra(Intent.EXTRA_TEXT,forecast);
//                startActivity(intent);
                MovieItem item = (MovieItem) mMoviesAdapter.getItem(position);
                Toast.makeText(getActivity(),item.getId()+"",Toast.LENGTH_SHORT).show();

                ((TwoPaneInterface) getActivity()).listItemClickCallback(item);




            }
        });

        return fragmentView;
    }

    public interface TwoPaneInterface{
        void listItemClickCallback(MovieItem movieItem);
    }

    public class MovieAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<MovieItem> moviesList;

        public MovieAdapter(Context c)
        {
            mContext = c;
            moviesList = new ArrayList<>();

        }
        public MovieAdapter(Context c, ArrayList<MovieItem> movies) {
            mContext = c;
            moviesList = movies;

        }
        public int getCount() {
            return moviesList.size();
        }

        public void add(MovieItem item){
            moviesList.add(item);
            super.notifyDataSetChanged();
        }
        public void clear(){
            moviesList.clear();
            super.notifyDataSetChanged();
        }

        public void remove(MovieItem item){
            moviesList.remove(item);
            super.notifyDataSetChanged();
        }

        public Object getItem(int position) {
            return moviesList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            View view;
            LayoutInflater inflater = LayoutInflater.from(mContext);
            if(convertView == null){
                view = inflater.inflate(R.layout.grid_item_movies,parent,false);

            }
            else{
                view = convertView;
            }
            imageView = (ImageView) view.findViewById(R.id.grid_item_movie_imageView);
            Picasso.with(mContext).load(moviesList.get(position).getImageURL()).into(imageView);
            return view;
        }
    }
}
