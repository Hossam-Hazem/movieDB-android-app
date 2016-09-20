package com.example.android.moviedb.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.android.moviedb.MovieItem;

import java.util.ArrayList;

import layout.MainFragment;

/**
 * Created by pc on 9/4/2016.
 */
public class MovieContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.android.moviedb";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_FAVORITE = "favorite";

    /* Inner class that defines the table contents of the favorite table */
    public static final class FavoriteEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        // Table name
        public static final String TABLE_NAME = "favorite";

        public static final String COLUMN_FAVORITE_ID = "movie_id";

        public static final String COLUMN_FAVORITE_TITLE = "title";

        public static final String COLUMN_FAVORITE_SYNOPSIS = "synopsis";

        public static final String COLUMN_FAVORITE_RATING = "rating";

        public static final String COLUMN_FAVORITE_RELEASE_DATE = "release_date";

        public static final String COLUMN_FAVORITE_IMAGE_PATH = "image_path";


        public static Uri buildFavoriteUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static boolean insert(Context context, MovieItem movieItem) {

            ContentValues movieValues = new ContentValues();
            movieValues.put(COLUMN_FAVORITE_ID, movieItem.getId());
            movieValues.put(COLUMN_FAVORITE_TITLE, movieItem.getName());
            movieValues.put(COLUMN_FAVORITE_SYNOPSIS, movieItem.getDescription());
            movieValues.put(COLUMN_FAVORITE_RATING, movieItem.getRating() + "");
            movieValues.put(COLUMN_FAVORITE_IMAGE_PATH, movieItem.getImagePath());
            movieValues.put(COLUMN_FAVORITE_RELEASE_DATE, movieItem.getDate());
            Uri insertedUri = context.getContentResolver().insert(
                    CONTENT_URI,
                    movieValues
            );

            long movieId = ContentUris.parseId(insertedUri);
            if (movieId > -1)
                return true;
            return false;
        }

        public static boolean checkMovieExistsByName(Context context, long id) {
            Uri uri = CONTENT_URI.buildUpon().appendPath(id + "").build();
            Cursor result = context.getContentResolver().query(
                    uri,
                    new String[]{_ID},
                    COLUMN_FAVORITE_ID + " = ?",
                    new String[]{id + ""},
                    null
            );
            if (result != null && result.moveToFirst()) {
                return true;
            }
            result.close();
            return false;
        }

        public static boolean delete(Context context, long id) {
            int res = context.getContentResolver().delete(
                    CONTENT_URI,
                    FavoriteEntry.COLUMN_FAVORITE_ID + "=?",
                    new String[]{id + ""});
            return res > 0;
        }

        public static ArrayList<MovieItem> getFavorites(Context context) {
            Cursor cursor = context.getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
            ArrayList<MovieItem> result = new ArrayList<>();
            while (cursor.moveToNext()) {

                MovieItem movie = new MovieItem(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_FAVORITE_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITE_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITE_RELEASE_DATE)),
                        cursor.getFloat(cursor.getColumnIndex(COLUMN_FAVORITE_RATING)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITE_SYNOPSIS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITE_IMAGE_PATH))
                );

                result.add(movie);

            }

            return result;
        }
    }
}
