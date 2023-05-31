package mz.ac.isutc.lecc31.fais.Samuel.ui.checkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentCheckoutBinding;

public class CheckoutFragment extends Fragment {
private FragmentCheckoutBinding checkoutBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checkoutBinding = FragmentCheckoutBinding.inflate(getLayoutInflater());

        checkoutBinding.seguinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_payment_confirmation);
            }
        });
        return checkoutBinding.getRoot();
    }
}