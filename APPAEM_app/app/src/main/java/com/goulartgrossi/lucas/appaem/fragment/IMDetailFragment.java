package com.goulartgrossi.lucas.appaem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.other.InductionMachineDao;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import appaem.InductionMachine;

public class IMDetailFragment extends Fragment {

    private InductionMachine inductionMachine;

    public static IMDetailFragment newInstance (InductionMachine inductionMachine) {
        IMDetailFragment newFragment = new IMDetailFragment();
        newFragment.setInductionMachine(inductionMachine);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imdetail, container, false);

        TextView textView = (TextView) view.findViewById(R.id.IMDetailName);
        textView.setText(this.inductionMachine.getName());
        textView = (TextView) view.findViewById(R.id.IMDetailManufacturer);
        textView.setText(this.inductionMachine.getManufacturer());
        textView = (TextView) view.findViewById(R.id.IMDetailModel);
        textView.setText(this.inductionMachine.getModel());
        textView = (TextView) view.findViewById(R.id.IMDetailDescription);
        textView.setText(this.inductionMachine.getDescription());
        textView = (TextView) view.findViewById(R.id.IMDetailPoles);
        textView.setText(this.inductionMachine.getnPoles().toString());
        textView = (TextView) view.findViewById(R.id.IMDetailFrequency);
        textView.setText(this.inductionMachine.getFrequency().toString() + " Hz");
        textView = (TextView) view.findViewById(R.id.IMDetailMReactance);
        textView.setText(this.inductionMachine.getXMagnetic().toString() + " Ω");

        textView = (TextView) view.findViewById(R.id.IMDetailStatorVoltage);
        textView.setText(String.format("%.2f", this.inductionMachine.getStator().getVoltage()) + " V");
        textView = (TextView) view.findViewById(R.id.IMDetailStatorResistance);
        textView.setText(String.format("%.2f", this.inductionMachine.getStator().getResistance()) + " Ω");
        textView = (TextView) view.findViewById(R.id.IMDetailStatorReactance);
        textView.setText(String.format("%.2f", this.inductionMachine.getStator().getReactance()) + " Ω");

        textView = (TextView) view.findViewById(R.id.IMDetailRotorVoltage);
        textView.setText(String.format("%.2f", this.inductionMachine.getRotor().getVoltage()) + " V");
        textView = (TextView) view.findViewById(R.id.IMDetailRotorResistance);
        textView.setText(String.format("%.2f", this.inductionMachine.getRotor().getResistance()) + " Ω");
        textView = (TextView) view.findViewById(R.id.IMDetailRotorReactance);
        textView.setText(String.format("%.2f", this.inductionMachine.getRotor().getReactance()) + " Ω");

        textView = (TextView) view.findViewById(R.id.IMDetailThVoltage);
        textView.setText(String.format("%.2f", this.inductionMachine.getThevenin().getVoltage()) + " V");
        textView = (TextView) view.findViewById(R.id.IMDetailThResistance);
        textView.setText(String.format("%.2f", this.inductionMachine.getThevenin().getResistance()) + " Ω");
        textView = (TextView) view.findViewById(R.id.IMDetailThReactance);
        textView.setText(String.format("%.2f", this.inductionMachine.getThevenin().getReactance()) + " Ω");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        new InductionMachineDao(getActivity()).saveInductionMachineToDB(this.inductionMachine);
        super.onActivityCreated(savedInstanceState);
    }

    public InductionMachine getInductionMachine() {
        return this.inductionMachine;
    }

    public void setInductionMachine(InductionMachine inductionMachine) {
        this.inductionMachine = inductionMachine;
    }
}
