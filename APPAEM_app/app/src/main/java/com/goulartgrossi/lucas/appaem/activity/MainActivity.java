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
import android.widget.EditText;
import android.widget.Toast;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.fragment.AboutUsFragment;
import com.goulartgrossi.lucas.appaem.fragment.CircuitFromCatalogFragment;
import com.goulartgrossi.lucas.appaem.fragment.CircuitFromTestsFragment;
import com.goulartgrossi.lucas.appaem.fragment.DefineEquivalentCircuitFragment;
import com.goulartgrossi.lucas.appaem.fragment.FeedbackFragment;
import com.goulartgrossi.lucas.appaem.fragment.IMAddFragment;
import com.goulartgrossi.lucas.appaem.fragment.IMCurvesFragment;
import com.goulartgrossi.lucas.appaem.fragment.IMDetailFragment;
import com.goulartgrossi.lucas.appaem.fragment.IMListFragment;
import com.goulartgrossi.lucas.appaem.fragment.SettingsFragment;
import com.goulartgrossi.lucas.appaem.other.InductionMachineDao;
import com.goulartgrossi.lucas.appaem.other.LayoutManager;

import java.util.List;

import appaem.ElectricalMachine;
import appaem.Graph;
import appaem.InductionMachine;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // tags used to attach the fragments
    public static String CURRENT_TAG = LayoutManager.TAG_IMLIST;
    private Integer circuitType = 0;

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
                LayoutManager.changeFragment(new IMAddFragment(), LayoutManager.TAG_IMADD, MainActivity.this);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fabFeedback);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutManager.changeFragment(new FeedbackFragment(), LayoutManager.TAG_FEEDBACK, MainActivity.this);

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fabGraphs);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutManager.changeFragment(IMCurvesFragment.newInstance(((IMDetailFragment) getSupportFragmentManager().findFragmentByTag(LayoutManager.TAG_IMDETAIL)).getInductionMachine()), LayoutManager.TAG_IMCURVES, MainActivity.this);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LayoutManager.changeFragment(new IMListFragment(), CURRENT_TAG, this, true);
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() > 1) {
            Fragment fragment = fragments.get(getSupportFragmentManager().getFragments().size() - 2);
            LayoutManager.setFABs(this, fragment.getTag(), fragment);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            CURRENT_TAG = LayoutManager.TAG_IMADD;
        } else if (id == R.id.nav_im_list) {
            fragment = new IMListFragment();
            CURRENT_TAG = LayoutManager.TAG_IMLIST;
        } else if (id == R.id.nav_app_about) {
            fragment = new AboutUsFragment();
            CURRENT_TAG = LayoutManager.TAG_ABOUT;
        } else if (id == R.id.nav_app_feedback) {
            fragment = new FeedbackFragment();
            CURRENT_TAG = LayoutManager.TAG_FEEDBACK;
        } else if (id == R.id.nav_app_settings) {
            fragment = new SettingsFragment();
            CURRENT_TAG = LayoutManager.TAG_SETTINGS;
        } else {
                fragment = new IMListFragment();
            CURRENT_TAG = LayoutManager.TAG_IMLIST;
        }

        LayoutManager.changeFragment(fragment, CURRENT_TAG, this);
        return true;
    }

    public void setToTorqueSpeedProfilingGraph (View v){
        ((IMCurvesFragment) getSupportFragmentManager().findFragmentByTag(LayoutManager.TAG_IMCURVES)).drawGraph(Graph.GraphType.TorqueSpeedProfiling);
    }

    public void setToPowerFactorSpeedProfilingGraph (View v){
        ((IMCurvesFragment) getSupportFragmentManager().findFragmentByTag(LayoutManager.TAG_IMCURVES)).drawGraph(Graph.GraphType.PowerFactorSpeedProfiling);
    }

    public void setToStatorCurrentSpeedProfilingGraph (View v){
        ((IMCurvesFragment) getSupportFragmentManager().findFragmentByTag(LayoutManager.TAG_IMCURVES)).drawGraph(Graph.GraphType.StatorCurrentSpeedProfiling);
    }

    public void setToEfficiencySpeedProfilingGraph (View v){
        ((IMCurvesFragment) getSupportFragmentManager().findFragmentByTag(LayoutManager.TAG_IMCURVES)).drawGraph(Graph.GraphType.EfficiencySpeedProfiling);
    }

    public void defineEquivalentCircuit (View v){
        Toast.makeText(this, circuitType, Toast.LENGTH_LONG).show();
    }

    public void getCircuitFromTests (View v){
        LayoutManager.changeFragment(new CircuitFromTestsFragment(), LayoutManager.TAG_IM_DEC, MainActivity.this);
        circuitType = 1;
    }

    public void getCircuitFromCatalog (View v){
        LayoutManager.changeFragment(new CircuitFromCatalogFragment(), LayoutManager.TAG_IM_DEC, MainActivity.this);
        circuitType = 2;
    }

    public void insertCircuits (View v){
        LayoutManager.changeFragment(new DefineEquivalentCircuitFragment(), LayoutManager.TAG_IM_DEC, MainActivity.this);
        circuitType = 0;
    }

    public void createNewMachine(View view) {
        String nPoles = ((EditText) findViewById(R.id.addNpoles)).getText().toString(),
                frequency = ((EditText) findViewById(R.id.addFrequency)).getText().toString(),
                name = ((EditText) findViewById(R.id.addName)).getText().toString(),
                year = ((EditText) findViewById(R.id.addYear)).getText().toString(),
                manufacturer = ((EditText) findViewById(R.id.addManufacturer)).getText().toString(),
                model = ((EditText) findViewById(R.id.addModel)).getText().toString(),
                description = ((EditText) findViewById(R.id.addDescription)).getText().toString();

        if (name.equals("")) {
            Toast.makeText(this, "You must insert 'Name' value to continue!", Toast.LENGTH_LONG).show();
            return;
        }
        if (year.equals("")) {
            Toast.makeText(this, "You must insert 'Year' value to continue!", Toast.LENGTH_LONG).show();
            return;
        }
        if (manufacturer.equals("")) {
            Toast.makeText(this, "You must insert 'Manufacturer' value to continue!", Toast.LENGTH_LONG).show();
            return;
        }
        if (model.equals("")) {
            Toast.makeText(this, "You must insert 'Model' value to continue!", Toast.LENGTH_LONG).show();
            return;
        }
        if (nPoles.equals("")) {
            Toast.makeText(this, "You must insert 'Number Of Poles' value to continue!", Toast.LENGTH_LONG).show();
            return;
        }
        if (frequency.equals("")) {
            Toast.makeText(this, "You must insert 'Frequency' value to continue!", Toast.LENGTH_LONG).show();
            return;
        }
        if (description.equals("")) {
            Toast.makeText(this, "You must insert 'Description' value to continue!", Toast.LENGTH_LONG).show();
            return;
        }

        InductionMachine machine = new InductionMachine(Integer.parseInt(nPoles), Integer.parseInt(frequency));
        machine.setName(name);
        machine.setYear(year);
        machine.setManufacturer(manufacturer);
        machine.setModel(model);
        machine.setDescription(description);
        machine.setTypeId("INDUCTION_MACHINE");

        long id = new InductionMachineDao(this).saveInductionMachineToDB(machine);
        if (id == -1) {
            Toast.makeText(this, "Error on create Induction Machine!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Machine Successfully created!", Toast.LENGTH_LONG).show();
            LayoutManager.changeFragment(new IMListFragment(), CURRENT_TAG, this, true);
        }
    }
}
