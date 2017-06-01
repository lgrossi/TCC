package com.goulartgrossi.lucas.appaem.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.fragment.AboutUsFragment;
import com.goulartgrossi.lucas.appaem.fragment.FeedbackFragment;
import com.goulartgrossi.lucas.appaem.fragment.IMAddFragment;
import com.goulartgrossi.lucas.appaem.fragment.IMDetailFragment;
import com.goulartgrossi.lucas.appaem.fragment.IMListFragment;
import com.goulartgrossi.lucas.appaem.fragment.SettingsFragment;
import com.goulartgrossi.lucas.appaem.other.LayoutManager;

import appaem.InductionMachine;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // tags used to attach the fragments
    public static String CURRENT_TAG = LayoutManager.TAG_IMLIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LayoutManager.changeFragment(new IMListFragment(), CURRENT_TAG, this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment;

        if (id == R.id.nav_im_add) {
            fragment = new IMAddFragment();
        } else if (id == R.id.nav_im_list) {
            fragment = new IMListFragment();
        } else if (id == R.id.nav_app_about) {
            fragment = new AboutUsFragment();
        } else if (id == R.id.nav_app_feedback) {
            fragment = new FeedbackFragment();
        } else if (id == R.id.nav_app_settings) {
            fragment = new SettingsFragment();
        } else {
                fragment = new IMListFragment();
        }

        LayoutManager.changeFragment(fragment, CURRENT_TAG, this);
        return true;
    }

    public void onClick(View v) {
        InductionMachine inductionMachine = ((IMDetailFragment) getSupportFragmentManager().findFragmentByTag(LayoutManager.TAG_IMDETAIL)).getInductionMachine();
        Toast.makeText(this, inductionMachine.getName(), Toast.LENGTH_SHORT).show();
    }
}
 