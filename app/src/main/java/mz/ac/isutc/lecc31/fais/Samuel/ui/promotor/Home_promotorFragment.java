package mz.ac.isutc.lecc31.fais.Samuel.ui.promotor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentHomePromotorBinding;

public class Home_promotorFragment extends Fragment {
 private FragmentHomePromotorBinding home_promotorBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        home_promotorBinding = FragmentHomePromotorBinding.inflate(getLayoutInflater());
        home_promotorBinding.verEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_dashboard_promotor);
            }
        });
        return home_promotorBinding.getRoot();
    }
}