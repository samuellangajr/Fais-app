package mz.ac.isutc.lecc31.fais.Samuel.ui.payment_confirmation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            String evento = bundle.getString("evento");
            String quantidade_normal = bundle.getString("quantidade_normal","0");
            String quantidade_vip =  bundle.getString("quantidade_vip","0");
            String nome = bundle.getString("nome");
            String email = bundle.getString("email");
            String metodo = bundle.getString("metodo");
            String celular = bundle.getString("celular");
            String precoN = bundle.getString("preco_normal");
            String precoV = bundle.getString("preco_vip");

            Double precoNormal = Double.parseDouble(precoN);
            Double precoVip = Double.parseDouble(precoV);
            Double total = precoNormal + precoVip;

            paymentConfirmationBinding.evento.setText("Evento: "+evento);
            paymentConfirmationBinding.nome.setText("Nome: "+nome);
            paymentConfirmationBinding.email.setText("Email: "+email);
            paymentConfirmationBinding.metodo.setText("Metódo de pagamento: "+metodo);
            paymentConfirmationBinding.celular.setText("Celular: "+celular);
            paymentConfirmationBinding.quantNormal.setText("Tickets Normal: "+quantidade_normal);
            paymentConfirmationBinding.quantVip.setText("Tickets vip: "+quantidade_vip);
            paymentConfirmationBinding.total.setText("Total: "+total);

            // Use the quantity as needed
        }else{
            Log.d("PaymentFragment", "Data not found");
        }

        return paymentConfirmationBinding.getRoot();
    }
}