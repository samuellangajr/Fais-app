package mz.ac.isutc.lecc31.fais.Samuel.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentLoginBinding;
import mz.ac.isutc.lecc31.fais.Samuel.ui.create_account.Create_accountFragment;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding loginBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginBinding = FragmentLoginBinding.inflate(getLayoutInflater());

        loginBinding.navCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_create_account);
            }
        });
    loginBinding.entrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_home_promotor);
        }
    });
        return loginBinding.getRoot();
    }
}