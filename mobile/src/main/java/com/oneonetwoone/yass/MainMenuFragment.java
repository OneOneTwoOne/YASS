package com.oneonetwoone.yass;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oneonetwoone.yass.counter.GameFragment;

import static android.R.attr.isGame;

public class  MainMenuFragment extends YassBaseFragment implements View.OnClickListener {
    private static final String TAG_FRAGMENT="Tag";
    public static final String PREF_SHOULD_DISPLAY_GAMEPAD_HELP="blah";

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

    @Override
    public void onResume(){
        super.onResume();
        if(isGameControllerConnected() && shouldDisplayGamepadHelp()){
            //displayGamepadHelp();
            //Do not show the dialog again
            PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .edit()
                    .putBoolean(PREF_SHOULD_DISPLAY_GAMEPAD_HELP, false)
                    .commit();
        }
    }

    private boolean shouldDisplayGamepadHelp(){
        return PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getBoolean(PREF_SHOULD_DISPLAY_GAMEPAD_HELP, true);
    }

    private boolean isGameControllerConnected(){
        int[] deviceIds= InputDevice.getDeviceIds();
        for(int deviceId:deviceIds){
            InputDevice dev= InputDevice.getDevice(deviceId);
            int sources = dev.getSources();
            if(((sources & InputDevice.SOURCE_GAMEPAD)==InputDevice.SOURCE_GAMEPAD)
                    ||((sources & InputDevice.SOURCE_JOYSTICK)==InputDevice.SOURCE_JOYSTICK)) {
                return true;
            }
        }
        return false;
    }
}
