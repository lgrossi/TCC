package com.goulartgrossi.lucas.appaem.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.activity.MainActivity;

/**
 * Created by Lucas Goulart Grossi on 5/31/2017.
 */

public class LayoutManager {
    public static void changeFragment (Fragment fragment, String tag, FragmentActivity activity) {
        activity.setTitle(tag);

        if (tag == MainActivity.TAG_IMLIST) {
            setFABVisibility(View.VISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabFeedback);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabMore);
        } else if (tag == MainActivity.TAG_ABOUT) {
            setFABVisibility(View.INVISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.VISIBLE, activity, R.id.fabFeedback);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabMore);
        } else if (tag == MainActivity.TAG_IMDETAIL) {
            setFABVisibility(View.INVISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabFeedback);
            setFABVisibility(View.VISIBLE, activity, R.id.fabMore);
        } else {
            setFABVisibility(View.INVISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabFeedback);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabMore);
        }

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment, tag);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawers();
    }

    private static void setFABVisibility (int visibility, FragmentActivity activity, int id) {
        activity.findViewById(id).setVisibility(visibility);
    }
}
