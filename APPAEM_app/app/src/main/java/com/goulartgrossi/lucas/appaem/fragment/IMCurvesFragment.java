package com.goulartgrossi.lucas.appaem.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.other.InductionMachineDao;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import appaem.Graph;
import appaem.Pair;
import appaem.InductionMachine;
import appaem.InductionMachineManager;

public class IMCurvesFragment extends Fragment {

    private InductionMachine inductionMachine;
    private Double initialX = 0.0, finalX, scale = 0.1, maxY;
    private Graph.GraphType currentGraph = Graph.GraphType.TorqueSpeedProfiling;

    public static IMCurvesFragment newInstance (InductionMachine inductionMachine) {
        IMCurvesFragment newFragment = new IMCurvesFragment();
        newFragment.setInductionMachine(inductionMachine);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imcurves, container, false);
        finalX = new InductionMachineManager(inductionMachine).calculateSynchronousSpeed();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        drawGraph(Graph.GraphType.TorqueSpeedProfiling);
        super.onActivityCreated(savedInstanceState);
    }

    public void setInductionMachine(InductionMachine inductionMachine) {
        this.inductionMachine = inductionMachine;
    }

    public void drawGraph (Graph.GraphType type) { drawGraph(type, null); };

    public void drawGraph (Graph.GraphType type, InductionMachine machine) {
        GraphView graph = (GraphView) getView().findViewById(R.id.graph);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(null, nf));
        graph.removeAllSeries();

        InductionMachineManager inductionMachineManager = new InductionMachineManager(machine != null ? machine : this.inductionMachine);
        ArrayList<Pair<Double, Double>> list;
        String title, vertTitle, horTitle = "Speed";
        switch (type) {
            default:
            case TorqueSpeedProfiling:
                title = "Torque x Speed Profiling";
                vertTitle = "Torque";
                list = inductionMachineManager.getTorqueSpeedProfilePoints(new Graph(type, title, horTitle, vertTitle, initialX, finalX, scale));
                currentGraph = Graph.GraphType.TorqueSpeedProfiling;
                break;
            case PowerFactorSpeedProfiling:
                vertTitle = "Power Factor";
                title = "Power Factor x Speed Profiling";
                list = inductionMachineManager.getPowerFactorSpeedProfilePoints(new Graph(type, title, horTitle, vertTitle, initialX, finalX, scale));
                currentGraph = Graph.GraphType.PowerFactorSpeedProfiling;
                break;
            case StatorCurrentSpeedProfiling:
                vertTitle = "Stator Current";
                title = "Stator Current x Speed Profiling";
                list = inductionMachineManager.getStatorCurrentSpeedProfilePoints(new Graph(type, title, horTitle, vertTitle, initialX, finalX, scale));
                currentGraph = Graph.GraphType.StatorCurrentSpeedProfiling;
                break;
            case EfficiencySpeedProfiling:
                vertTitle = "Efficiency";
                title = "Efficiency x Speed Profiling";
                list = inductionMachineManager.getEfficiencySpeedProfilePoints(new Graph(type, title, horTitle, vertTitle, initialX, finalX + 0.1, scale));
                currentGraph = Graph.GraphType.EfficiencySpeedProfiling;
                break;
        }
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
        for (Pair<Double, Double> pair : list) {
            if (pair.getElement0() > inductionMachineManager.calculateSynchronousSpeed()){
                dataPointArrayList.add(new DataPoint(pair.getElement0(), 0));
            } else {
                dataPointArrayList.add(new DataPoint(pair.getElement0(), pair.getElement1()));
            }
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointArrayList.toArray(new DataPoint[]{}));

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                IMCurvesFragment.this.drawGraph(IMCurvesFragment.this.currentGraph,
                        InductionMachineManager.generateAuxMachine(IMCurvesFragment.this.inductionMachine, null, new InductionMachineManager(IMCurvesFragment.this.inductionMachine).calculateFrequencyFromSpeed(dataPoint.getX()).intValue()));
                Toast.makeText(getActivity(), "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        if (machine == null) { maxY = series.getHighestValueY(); }

        graph.setTitle(title);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(initialX);
        graph.getViewport().setMaxX(finalX * 1.2);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(maxY * 1.4);

        graph.getGridLabelRenderer().setVerticalAxisTitle(vertTitle);
        graph.getGridLabelRenderer().setHorizontalAxisTitle(horTitle);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        graph.addSeries(series);
    }
}
