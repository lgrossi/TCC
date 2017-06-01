package com.goulartgrossi.lucas.appaem.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.view.menu.MenuAdapter;
import android.view.View;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.activity.MainActivity;

/**
 * Created by Lucas Goulart Grossi on 5/31/2017.
 */

public class LayoutManager {
    public static final String TAG_IMLIST = "List Induction Machines";
    public static final String TAG_IMADD = "Add new Induction Machine";
    public static final String TAG_ABOUT = "About Us";
    public static final String TAG_FEEDBACK = "Feedback";
    public static final String TAG_SETTINGS = "Settings";
    public static final String TAG_IMDETAIL = "Detail Induction Machine";

    public static void changeFragment (Fragment fragment, String tag, FragmentActivity activity) {
        activity.setTitle(tag);

        if (tag == TAG_IMLIST) {
            setFABVisibility(View.VISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabFeedback);
        } else if (tag == TAG_ABOUT) {
            setFABVisibility(View.INVISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.VISIBLE, activity, R.id.fabFeedback);
        } else if (tag == TAG_IMDETAIL) {
            setFABVisibility(View.INVISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabFeedback);
        } else {
            setFABVisibility(View.INVISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabFeedback);
        }

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment, tag);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawers();

        MainActivity.CURRENT_TAG = tag;
    }

    private static void setFABVisibility (int visibility, FragmentActivity activity, int id) {
        activity.findViewById(id).setVisibility(visibility);
    }
}
