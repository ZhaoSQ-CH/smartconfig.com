package example.demosmartconfig.package2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.demosmartconfig.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class setup_Fragment extends Fragment {


    public setup_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setup_, container, false);
    }

}
