package mz.ac.isutc.lecc31.fais.Samuel.ui.create_account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentCreateAccountBinding;

public class Create_accountFragment extends Fragment {
private FragmentCreateAccountBinding createAccountBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createAccountBinding = FragmentCreateAccountBinding.inflate(getLayoutInflater());

        return createAccountBinding.getRoot();
    }
}