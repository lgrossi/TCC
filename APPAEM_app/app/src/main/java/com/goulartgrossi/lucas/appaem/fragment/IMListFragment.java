package com.goulartgrossi.lucas.appaem.fragment;

import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.goulartgrossi.lucas.appaem.R;
import com.goulartgrossi.lucas.appaem.activity.MainActivity;
import com.goulartgrossi.lucas.appaem.other.InductionMachineDao;
import com.goulartgrossi.lucas.appaem.other.LayoutManager;

import java.util.ArrayList;

import appaem.InductionMachine;

public class IMListFragment extends ListFragment implements OnItemClickListener {

    ArrayList<InductionMachine> imList;
    ArrayList<String> titleList = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imlist, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, titleList);

        //new InductionMachineDao(getActivity()).deleteAll();

        imList = new InductionMachineDao(getActivity()).readInductionMachineFromDB();

        int count = 1;
        titleList.clear();
        if (imList != null)
            for (InductionMachine machine : imList) {
                titleList.add(count + ": " + machine.getName() + " - " + machine.getModel() + " (" + machine.getYear() + ")");
                count++;
            }

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), imList.get(position).getName() + " Clicked!", Toast.LENGTH_SHORT).show();

        ((MainActivity) this.getActivity()).setInductionMachine(this.imList.get(position));
        LayoutManager.changeFragment(IMDetailFragment.newInstance(imList.get(position)), LayoutManager.TAG_IMDETAIL, getActivity());
    }
}
