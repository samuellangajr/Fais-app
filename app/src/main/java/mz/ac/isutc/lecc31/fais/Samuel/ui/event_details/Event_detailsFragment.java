package mz.ac.isutc.lecc31.fais.Samuel.ui.event_details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentEventDetailsBinding;


public class Event_detailsFragment extends Fragment {
private FragmentEventDetailsBinding eventDetailsBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        eventDetailsBinding = FragmentEventDetailsBinding.inflate(getLayoutInflater());

        eventDetailsBinding.comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_checkout);
            }
        });
        eventDetailsBinding.reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_checkout);
            }
        });
        return eventDetailsBinding.getRoot();
    }
}