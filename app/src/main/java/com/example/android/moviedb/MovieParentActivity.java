package com.example.android.moviedb;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import layout.MovieReviewsFragment;
import layout.MovieTrailersFragment;

/**
 * Created by Hossam on 9/16/2016.
 */

public abstract class MovieParentActivity extends AppCompatActivity {


    public void openReviewsFragment(){
        showDialog(MovieReviewsFragment.newInstance());
    }

    public void openTrailersFragment(){
        showDialog(MovieTrailersFragment.newInstance());
    }

    private void showDialog(DialogFragment newFragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        closeOpenedDialog(ft);

        // Create and show the dialog.
        newFragment.show(ft, "dialog");
    }
    private void closeOpenedDialog(FragmentTransaction ft){
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
    }



    public void MovieImageButtonClick(View v){

    }
}
