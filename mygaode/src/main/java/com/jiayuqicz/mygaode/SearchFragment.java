package com.jiayuqicz.mygaode;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements TextWatcher, Inputtips.InputtipsListener{

    private String city = "北京";

    private ListView inputList;
    private AutoCompleteTextView searchBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        inputList = (ListView) getView().findViewById(R.id.my_search_bar_items);
        searchBar = (AutoCompleteTextView) getView().findViewById(R.id.my_search_bar);
        searchBar.addTextChangedListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        String input = s.toString().trim();
        InputtipsQuery query = new InputtipsQuery(input, city);
        query.setCityLimit(true);
        Inputtips inputtips = new Inputtips(getActivity(), query);
        inputtips.setInputtipsListener(this);
        inputtips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onGetInputtips(List<Tip> list, int rCode) {

        if(rCode == AMapException.CODE_AMAP_SUCCESS) {
            List<HashMap<String, String>> tips = new ArrayList<>();

            for(Tip tip : list) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("name", tip.getName());
                hashMap.put("address", tip.getAddress());
                tips.add(hashMap);
            }

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), tips, R.layout.tips_tems_layout,
                    new String[] {"name", "address"}, new int[] {R.id.tip_name, R.id.tip_address});
            inputList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
