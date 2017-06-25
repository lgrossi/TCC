package com.goulartgrossi.lucas.appaem.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.view.menu.MenuAdapter;
import android.view.View;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.activity.MainActivity;
import com.goulartgrossi.lucas.appaem.fragment.IMDetailFragment;

/**
 * Created by Lucas Goulart Grossi on 5/31/2017.
 */

public class LayoutManager {
    public static final String TAG_IMLIST = "List Induction Machines";
    public static final String TAG_IMADD = "Add new Induction Machine";
    public static final String TAG_ABOUT = "About Us";
    public static final String TAG_FEEDBACK = "Feedback";
    public static final String TAG_SETTINGS = "Settings";
    public static final String TAG_IM_DEC = "IM Define Equivalent Circuit";
    public static final String TAG_IMDETAIL = "Detail Induction Machine";
    public static final String TAG_IMCURVES = "IM Characteristic Curves";

    public static void changeFragment (Fragment fragment, String tag, FragmentActivity activity) {
        LayoutManager.changeFragment(fragment, tag, activity, false);
    }

    public static void changeFragment (Fragment fragment, String tag, FragmentActivity activity, Boolean avoidBackStack) {
        activity.setTitle(tag);

        LayoutManager.setFABs(activity, tag, fragment);

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment, tag);
        if (!avoidBackStack)
            transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawers();

        MainActivity.CURRENT_TAG = tag;
    }

    public static void setFABs(FragmentActivity activity, String tag, Fragment fragment) {
        if (tag == TAG_IMLIST) {
            setFABVisibility(View.VISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabFeedback);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabChangeCurves);
        } else if (tag == TAG_ABOUT) {
            setFABVisibility(View.INVISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.VISIBLE, activity, R.id.fabFeedback);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabChangeCurves);
        } else if (tag == TAG_IMCURVES) {
            setFABVisibility(View.INVISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabFeedback);
            setFABVisibility(View.VISIBLE, activity, R.id.fabChangeCurves);
        } else {
            setFABVisibility(View.INVISIBLE, activity, R.id.fabAdd);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabFeedback);
            setFABVisibility(View.INVISIBLE, activity, R.id.fabChangeCurves);
        }
    }

    private static void setFABVisibility (int visibility, FragmentActivity activity, int id) {
        activity.findViewById(id).setVisibility(visibility);
    }
}
