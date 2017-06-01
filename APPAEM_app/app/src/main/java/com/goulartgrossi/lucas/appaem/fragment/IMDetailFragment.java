package com.goulartgrossi.lucas.appaem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.other.InductionMachineDao;

import appaem.BasicCircuit;
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
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        new InductionMachineDao(getActivity()).saveInductionMachineToDB(this.inductionMachine);
        super.onActivityCreated(savedInstanceState);
    }

    public InductionMachine getInductionMachine() {
        return inductionMachine;
    }

    public void setInductionMachine(InductionMachine inductionMachine) {
        this.inductionMachine = inductionMachine;
    }
}
