package com.goulartgrossi.lucas.appaem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goulartgrossi.lucas.appaem.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;

import appaem.Graph;
import appaem.Pair;
import appaem.InductionMachine;
import appaem.InductionMachineManager;

public class IMCurvesFragment extends Fragment {

    private InductionMachineManager inductionMachineManager;
    private double initialX = 0.0, finalX, scale = 1;
    private Graph graphTS;
    private Graph graphPFS;
    private Graph graphSCS;
    private Graph graphES;

    public static IMCurvesFragment newInstance (InductionMachine inductionMachine) {
        IMCurvesFragment newFragment = new IMCurvesFragment();
        newFragment.setInductionMachineManager(new InductionMachineManager(inductionMachine));
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imcurves, container, false);

        finalX = inductionMachineManager.calculateSynchronousSpeed();

        graphTS = new Graph(Graph.GraphType.TorqueSpeedProfiling, "Torque x Speed Profiling", "Speed", "Torque", initialX, finalX, scale);
        graphPFS = new Graph(Graph.GraphType.PowerFactorSpeedProfiling, "Power Factor x Speed Profiling", "Speed", "Power Factor", initialX, finalX, scale);
        graphSCS = new Graph(Graph.GraphType.StatorCurrentSpeedProfiling, "Stator Current x Speed Profiling", "Speed", "Stator Current", initialX, finalX, scale);
        graphES = new Graph(Graph.GraphType.EfficiencySpeedProfiling, "Efficiency x Speed Profiling", "Speed", "Efficiency", initialX, finalX, scale);

        GraphView graph = (GraphView) view.findViewById(R.id.graph);

        ArrayList<Pair<Double, Double>> list = inductionMachineManager.getTorqueSpeedProfilePoints(graphTS);
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
        for (Pair<Double, Double> pair : list) {
            dataPointArrayList.add(new DataPoint(pair.getElement0(), pair.getElement1()));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointArrayList.toArray(new DataPoint[]{}));

        graph.setTitle(graphTS.getTitle());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(graphTS.getBegin());
        graph.getViewport().setMaxX(graphTS.getEnd());
        graph.addSeries(series);
        return view;
    }

    public void setInductionMachineManager(InductionMachineManager inductionMachineManager) {
        this.inductionMachineManager = inductionMachineManager;
    }
}
