package com.oneonetwoone.yass;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oneonetwoone.yass.counter.GameFragment;

public class  MainMenuFragment extends YassBaseFragment implements View.OnClickListener {
    private static final String TAG_FRAGMENT="Tag";

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.fragment_main_menu,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_start).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            startGame();
        }
    }
    public void startGame(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new GameFragment(), TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
}
