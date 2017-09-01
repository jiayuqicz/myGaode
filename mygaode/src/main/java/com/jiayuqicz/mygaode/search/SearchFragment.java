package com.jiayuqicz.mygaode.search;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements TextWatcher, Inputtips.InputtipsListener,
        AdapterView.OnItemClickListener {


    private String city = "北京";

    private ListView inputList;
    private AutoCompleteTextView searchBar;

    private MySimpleAdapter adapter;
    private MyItemClickedListener mCallback = null;

    public interface MyItemClickedListener {
        void Locate(LatLonPoint point);
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCallback = (MyItemClickedListener) getActivity();

        inputList = (ListView) getView().findViewById(R.id.my_search_bar_items);
        searchBar = (AutoCompleteTextView) getView().findViewById(R.id.my_search_bar);
        searchBar.addTextChangedListener(this);
        inputList.setOnItemClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        String input = s.toString().trim();
        InputtipsQuery query = new InputtipsQuery(input, city);
        query.setCityLimit(false);
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
            List<HashMap<String, Object>> tips = new ArrayList<>();

            for(Tip tip : list) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", tip.getName());
                hashMap.put("address", tip.getDistrict());
                hashMap.put("point", tip.getPoint());
                tips.add(hashMap);
            }

            adapter = new MySimpleAdapter(getActivity(), tips, R.layout.main_search_tips,
                    new String[] {"name", "address"}, new int[] {R.id.tip_name, R.id.tip_address});
            inputList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        LatLonPoint point = (LatLonPoint) view.getTag();
        mCallback.Locate(point);
        MainActivity activity = (MainActivity) mCallback;
        activity.selectMapFragment(null);

    }

    public void setCity(String city) {
        this.city = city;
    }

    public MySimpleAdapter getAdapter() {
        return adapter;
    }
}
