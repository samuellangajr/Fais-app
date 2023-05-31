package mz.ac.isutc.lecc31.fais.Samuel.ui.payment_confirmation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentPaymentConfirmationBinding;

public class Payment_confirmationFragment extends Fragment {
private FragmentPaymentConfirmationBinding paymentConfirmationBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paymentConfirmationBinding = FragmentPaymentConfirmationBinding.inflate(getLayoutInflater());


        return paymentConfirmationBinding.getRoot();
    }
}