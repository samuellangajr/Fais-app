package mz.ac.isutc.lecc31.fais.Samuel.ui.checkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
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
                Bundle bundle = new Bundle();
                Bundle bundle2 = getArguments();
                bundle.putString("nome", checkoutBinding.nome.getText().toString());
                bundle.putString("email", checkoutBinding.email.getText().toString());
                bundle.putString("celular", checkoutBinding.celular.getText().toString());
                bundle.putString("metodo", checkoutBinding.metodo.getSelectedItem().toString());
                bundle.putString("evento", bundle2.getString("event"));
                bundle.putString("id_evento", bundle2.getString("event_id"));
                bundle.putString("quantidade_normal", bundle2.getString("quant_normal"));
                bundle.putString("quantidade_vip", bundle2.getString("quant_vip"));
                bundle.putString("preco_normal", bundle2.getString("prec_normal"));
                bundle.putString("preco_vip", bundle2.getString("prec_vip"));

                navController.navigate(R.id.nav_payment_confirmation, bundle);

            }
        });

        return checkoutBinding.getRoot();
    }
}