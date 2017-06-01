package com.goulartgrossi.lucas.appaem.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.other.InductionMachineDao;
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
        //graph.addSeries(drawSeries(new Graph(Graph.GraphType.TorqueSpeedProfiling, "Torque x Speed Profiling", "Speed", "Torque", initialX, finalX, scale)));
        //graph.addSeries(drawSeries(new Graph(Graph.GraphType.PowerFactorSpeedProfiling, "Power Factor x Speed Profiling", "Speed", "Power Factor", initialX, finalX, scale)));
        //graph.addSeries(drawSeries(new Graph(Graph.GraphType.StatorCurrentSpeedProfiling, "Stator Current x Speed Profiling", "Speed", "Stator Current", initialX, finalX, scale)));
        //graph.addSeries(drawSeries(new Graph(Graph.GraphType.EfficiencySpeedProfiling, "Efficiency x Speed Profiling", "Speed", "Efficiency", initialX, finalX, scale)));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        drawGraph(Graph.GraphType.TorqueSpeedProfiling);
        super.onActivityCreated(savedInstanceState);
    }

    public void setInductionMachineManager(InductionMachineManager inductionMachineManager) {
        this.inductionMachineManager = inductionMachineManager;
    }

    public void drawGraph (Graph.GraphType type) {
        GraphView graph = (GraphView) getView().findViewById(R.id.graph);
        ArrayList<Pair<Double, Double>> list;
        switch (type) {
            default:
            case TorqueSpeedProfiling:
                list = inductionMachineManager.getTorqueSpeedProfilePoints(new Graph(type, "Torque x Speed Profiling", "Speed", "Torque", initialX, finalX, scale));
                break;
            case PowerFactorSpeedProfiling:
                list = inductionMachineManager.getPowerFactorSpeedProfilePoints(new Graph(type, "Power Factor x Speed Profiling", "Speed", "Power Factor", initialX, finalX, scale));
                break;
            case StatorCurrentSpeedProfiling:
                list = inductionMachineManager.getStatorCurrentSpeedProfilePoints(new Graph(type, "Stator Current x Speed Profiling", "Speed", "Stator Current", initialX, finalX, scale));
                break;
            case EfficiencySpeedProfiling:
                list = inductionMachineManager.getEfficiencySpeedProfilePoints(new Graph(type, "Efficiency x Speed Profiling", "Speed", "Efficiency", initialX, finalX, scale));
                break;
        }
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
        for (Pair<Double, Double> pair : list) {
            dataPointArrayList.add(new DataPoint(pair.getElement0(), pair.getElement1()));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointArrayList.toArray(new DataPoint[]{}));

        series.setTitle(graph.getTitle());

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(initialX);
        graph.getViewport().setMaxX(finalX);
        graph.addSeries(series);
    }
}
