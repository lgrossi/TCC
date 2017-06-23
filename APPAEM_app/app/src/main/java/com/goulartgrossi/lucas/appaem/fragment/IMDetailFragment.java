package com.goulartgrossi.lucas.appaem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.other.InductionMachineDao;

import appaem.InductionMachine;
import appaem.InductionMachineManager;

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
        textView.setText(generateLabelText(this.inductionMachine.getnPoles(), ""));
        textView = (TextView) view.findViewById(R.id.IMDetailFrequency);
        textView.setText(generateLabelText(this.inductionMachine.getFrequency(), "Hz"));
        textView = (TextView) view.findViewById(R.id.IMDetailMReactance);
        Double xM = this.inductionMachine.getXMagnetic();
        textView.setText(generateLabelText(xM == 0.0 ? null : xM, "Ω"));

        if (this.inductionMachine.getStator() == null) {
            view.findViewById(R.id.statorLabel).setVisibility(View.GONE);
            view.findViewById(R.id.statorSection).setVisibility(View.GONE);
            view.findViewById(R.id.rotorLabel).setVisibility(View.GONE);
            view.findViewById(R.id.rotorSection).setVisibility(View.GONE);
            view.findViewById(R.id.thLabel).setVisibility(View.GONE);
            view.findViewById(R.id.thSection).setVisibility(View.GONE);
        } else {
            textView = (TextView) view.findViewById(R.id.IMDetailStatorVoltage);
            textView.setText(generateLabelText(this.inductionMachine.getStator().getVoltage(), "V"));
            textView = (TextView) view.findViewById(R.id.IMDetailStatorResistance);
            textView.setText(generateLabelText(this.inductionMachine.getStator().getResistance(), "Ω"));
            textView = (TextView) view.findViewById(R.id.IMDetailStatorReactance);
            textView.setText(generateLabelText(this.inductionMachine.getStator().getReactance(), "Ω"));

            textView = (TextView) view.findViewById(R.id.IMDetailRotorVoltage);
            textView.setText(generateLabelText(this.inductionMachine.getRotor().getVoltage(), "V"));
            textView = (TextView) view.findViewById(R.id.IMDetailRotorResistance);
            textView.setText(generateLabelText(this.inductionMachine.getRotor().getResistance(), "Ω"));
            textView = (TextView) view.findViewById(R.id.IMDetailRotorReactance);
            textView.setText(generateLabelText(this.inductionMachine.getRotor().getReactance(), "Ω"));

            textView = (TextView) view.findViewById(R.id.IMDetailThVoltage);
            InductionMachineManager manager = new InductionMachineManager(this.inductionMachine);
            textView.setText(generateLabelText(manager.calculateVth(), "V"));
            textView = (TextView) view.findViewById(R.id.IMDetailThResistance);
            textView.setText(generateLabelText(manager.calculateRth(), "Ω"));
            textView = (TextView) view.findViewById(R.id.IMDetailThReactance);
            textView.setText(generateLabelText(manager.calculateXth(), "Ω"));
        }

        return view;
    }

    public String generateLabelText (Integer entrance, String unity) {
        if (entrance == null) return "--------";
        return entrance.toString() + " " + unity;
    }

    public String generateLabelText (Double entrance, String unity) {
        if (entrance == null) return "--------";
        return String.format("%.2f", entrance) + " " + unity;
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
