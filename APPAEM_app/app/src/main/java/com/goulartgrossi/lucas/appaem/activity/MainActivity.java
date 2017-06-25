package com.goulartgrossi.lucas.appaem.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.fragment.AboutUsFragment;
import com.goulartgrossi.lucas.appaem.fragment.CircuitFromCatalogFragment;
import com.goulartgrossi.lucas.appaem.fragment.CircuitFromTestsFragment;
import com.goulartgrossi.lucas.appaem.fragment.DefineEquivalentCircuitFragment;
import com.goulartgrossi.lucas.appaem.fragment.IMAddFragment;
import com.goulartgrossi.lucas.appaem.fragment.IMCurvesFragment;
import com.goulartgrossi.lucas.appaem.fragment.IMDetailFragment;
import com.goulartgrossi.lucas.appaem.fragment.IMListFragment;
import com.goulartgrossi.lucas.appaem.other.InductionMachineDao;
import com.goulartgrossi.lucas.appaem.other.LayoutManager;

import java.util.List;

import appaem.BasicCircuit;
import appaem.Graph;
import appaem.IMEquivalentCircuitManager;
import appaem.InductionMachine;
import appaem.Pair;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // tags used to attach the fragments
    public static String CURRENT_TAG = LayoutManager.TAG_IMLIST;
    private Integer circuitType = 0;
    private InductionMachine inductionMachine;

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
                MainActivity.this.sendFeedback();
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fabChangeCurves);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence colors[] = new CharSequence[] {"Torque x Speed Profiling", "Power Factor x Speed Profiling", "Stator Current x Speed Profiling", "Efficiency x Speed Profiling"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose Characteristic Curve");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.circuitType = which;
                        switch (which) {
                            default:
                            case 0:
                                ((IMCurvesFragment) getSupportFragmentManager().findFragmentByTag(LayoutManager.TAG_IMCURVES)).drawGraph(Graph.GraphType.TorqueSpeedProfiling);
                                break;
                            case 1:
                                ((IMCurvesFragment) getSupportFragmentManager().findFragmentByTag(LayoutManager.TAG_IMCURVES)).drawGraph(Graph.GraphType.PowerFactorSpeedProfiling);
                                break;
                            case 2:
                                ((IMCurvesFragment) getSupportFragmentManager().findFragmentByTag(LayoutManager.TAG_IMCURVES)).drawGraph(Graph.GraphType.StatorCurrentSpeedProfiling);
                                break;
                            case 3:
                                ((IMCurvesFragment) getSupportFragmentManager().findFragmentByTag(LayoutManager.TAG_IMCURVES)).drawGraph(Graph.GraphType.EfficiencySpeedProfiling);
                                break;
                        }
                    }
                });
                builder.show();
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            List<Fragment> fragments = getSupportFragmentManager().getFragments();

            for (int i = fragments.size() - 2; i >= 0; i--) {
                Fragment fragment = fragments.get(i);
                if (fragment != null && fragment.isVisible()) {
                    LayoutManager.setFABs(this, fragment.getTag(), fragment);
                    break;
                }
            }
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
            return this.sendFeedback();
        /*} else if (id == R.id.nav_app_settings) {
            fragment = new SettingsFragment();
            CURRENT_TAG = LayoutManager.TAG_SETTINGS;*/
        } else {
                fragment = new IMListFragment();
            CURRENT_TAG = LayoutManager.TAG_IMLIST;
        }

        LayoutManager.changeFragment(fragment, CURRENT_TAG, this);
        return true;
    }

    public void defineEquivalentCircuit (View v){
        CharSequence colors[] = new CharSequence[] {"Insert Circuit", "Get Circuit From Tests", "Get Circuit from Catalog Data"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a method");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.circuitType = which;
                switch (which) {
                    default:
                    case 0:
                        LayoutManager.changeFragment(new DefineEquivalentCircuitFragment(), LayoutManager.TAG_IM_DEC, MainActivity.this);
                        break;
                    case 1:
                        LayoutManager.changeFragment(new CircuitFromTestsFragment(), LayoutManager.TAG_IM_DEC,  MainActivity.this);
                        break;
                    case 2:
                        LayoutManager.changeFragment(new CircuitFromCatalogFragment(), LayoutManager.TAG_IM_DEC,  MainActivity.this);
                        break;
                }
            }
        });
        builder.show();
    }

    public void plotCurves (View v){
        LayoutManager.changeFragment(IMCurvesFragment.newInstance(((IMDetailFragment) getSupportFragmentManager().findFragmentByTag(LayoutManager.TAG_IMDETAIL)).getInductionMachine()), LayoutManager.TAG_IMCURVES, MainActivity.this);
    }

    private Boolean sendFeedback (){
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "AppAEM User Feedback");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.setData(Uri.parse("mailto:lucas.ggrossi@gmail.com")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);
        return true;
    }

    public void setEquivalentCircuit (View v) {
        switch (this.circuitType) {
            default:
            case 0:
                Double v1 = Double.parseDouble(((EditText) findViewById(R.id.defineV1)).getText().toString()),
                        r1 = Double.parseDouble(((EditText) findViewById(R.id.defineR1)).getText().toString()),
                        x1 = Double.parseDouble(((EditText) findViewById(R.id.defineX1)).getText().toString()),
                        r2 = Double.parseDouble(((EditText) findViewById(R.id.defineR2)).getText().toString()),
                        x2 = Double.parseDouble(((EditText) findViewById(R.id.defineX2)).getText().toString()),
                        xm = Double.parseDouble(((EditText) findViewById(R.id.defineXm)).getText().toString());

                inductionMachine.setStator(new BasicCircuit(v1, null, r1, x1));
                inductionMachine.setRotor(new BasicCircuit(null, null, r2, x2));
                inductionMachine.setXMagnetic(xm);
                break;
            case 1:
                Double p0 = Double.parseDouble(((EditText) findViewById(R.id.defineP0)).getText().toString()),
                        i0 = Double.parseDouble(((EditText) findViewById(R.id.defineI0)).getText().toString()),
                        v0 = Double.parseDouble(((EditText) findViewById(R.id.defineV0)).getText().toString()),
                        pBl = Double.parseDouble(((EditText) findViewById(R.id.definePbl)).getText().toString()),
                        iBl = Double.parseDouble(((EditText) findViewById(R.id.defineIbl)).getText().toString()),
                        vBl = Double.parseDouble(((EditText) findViewById(R.id.defineVbl)).getText().toString()),
                        r1_2 = Double.parseDouble(((EditText) findViewById(R.id.defineR1_2)).getText().toString());

                Pair<Double,BasicCircuit> resultsFromTests = new IMEquivalentCircuitManager().calculateEquivalentCircuitFromTests(p0, i0, v0, pBl, iBl, vBl, r1_2);

                inductionMachine.setStator(new BasicCircuit(v0/Math.sqrt(3), null, r1_2, resultsFromTests.getElement1().getReactance()));
                inductionMachine.setRotor(new BasicCircuit(resultsFromTests.getElement1()));
                inductionMachine.setXMagnetic(resultsFromTests.getElement0());
                break;
            case 2:
                Double pF = Double.parseDouble(((EditText) findViewById(R.id.definePF)).getText().toString()),
                        iN = Double.parseDouble(((EditText) findViewById(R.id.defineIn)).getText().toString()),
                        wS = Double.parseDouble(((EditText) findViewById(R.id.defineWs)).getText().toString()),
                        tN = Double.parseDouble(((EditText) findViewById(R.id.defineTn)).getText().toString()),
                        sN = Double.parseDouble(((EditText) findViewById(R.id.defineSn)).getText().toString()),
                        v1_2 = Double.parseDouble(((EditText) findViewById(R.id.defineV1_2)).getText().toString()),
                        tMax = Double.parseDouble(((EditText) findViewById(R.id.defineTmax)).getText().toString());

                Pair<Double, BasicCircuit> resultsFromCatalog = new IMEquivalentCircuitManager().calculateEquivalentCircuitFromCatalog(pF, iN, wS, tN, sN, v1_2, tMax, 1.0);

                inductionMachine.setStator(new BasicCircuit(v1_2, null, 0.0, resultsFromCatalog.getElement1().getReactance()));
                inductionMachine.setRotor(new BasicCircuit(resultsFromCatalog.getElement1()));
                inductionMachine.setXMagnetic(resultsFromCatalog.getElement0());
                break;
        }

        new InductionMachineDao(this).saveInductionMachineToDB(this.inductionMachine);
        LayoutManager.changeFragment(IMDetailFragment.newInstance(this.inductionMachine), LayoutManager.TAG_IMDETAIL, this);
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

        InductionMachine machine = new InductionMachine(Integer.parseInt(frequency), Integer.parseInt(nPoles));
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
            LayoutManager.changeFragment(new IMListFragment(), LayoutManager.TAG_IMLIST, this);
        }
    }

    public void setInductionMachine(InductionMachine machine) {
        this.inductionMachine = machine;
    }
}
