package com.example.hemant.sharedlock;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Hemant on 06-04-2017.
 */

public class Bluetooth_Lock extends Fragment{

    Button lock;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lock, container, false);

        lock=(Button)rootView.findViewById(R.id.lockbutton);

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager manager=getContext().getPackageManager();
                Intent intent=manager.getLaunchIntentForPackage("appinventor.ai_sutharhemant16.Bluetooth_Arduino");
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                getContext().startActivity(intent);

            }
        });

        return rootView;
    }

}
