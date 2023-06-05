package mz.ac.isutc.lecc31.fais.Samuel.ui.payment_confirmation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentPaymentConfirmationBinding;
import android.text.format.DateFormat;
import java.util.Date;
public class Payment_confirmationFragment extends Fragment {
private FragmentPaymentConfirmationBinding paymentConfirmationBinding;
private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paymentConfirmationBinding = FragmentPaymentConfirmationBinding.inflate(getLayoutInflater());
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String id_evento = bundle.getString("id_evento");
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

        paymentConfirmationBinding.seguinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_evento = bundle.getString("id_evento");
                String evento = bundle.getString("evento");
                String nome = bundle.getString("nome");
                String email = bundle.getString("email");
                String metodo = bundle.getString("metodo");
                String celular = bundle.getString("celular");
                int quantidade_normal = Integer.parseInt(bundle.getString("quantidade_normal","0"));
                int quantidade_vip = Integer.parseInt(bundle.getString("quantidade_vip","0"));
                String precoN = bundle.getString("preco_normal");
                String precoV = bundle.getString("preco_vip");
                String currentDate = DateFormat.format("dd/MM/yyyy", new Date()).toString();
                String currentTime = DateFormat.format("HH:mm", new Date()).toString();

                // Crie um mapa com os dados da venda
                Map<String, Object> saleData = new HashMap<>();

                // Inserir tickets normais
                for(int i = 0; i < quantidade_normal; i++) {
                    String id_ticket = UUID.randomUUID().toString();
                    saleData.put("id_ticket", id_ticket);
                    saleData.put("id_evento", id_evento);
                    saleData.put("id_comprador", auth.getCurrentUser().getUid());
                    saleData.put("tipo_ticket", "normal");
                    saleData.put("evento", evento);
                    saleData.put("nome", nome);
                    saleData.put("valor", precoN);
                    saleData.put("email", email);
                    saleData.put("metodo", metodo);
                    saleData.put("celular", celular);
                    saleData.put("data_compra", currentDate);
                    saleData.put("hora_compra", currentTime);
                    saleData.put("estado", "não validado");

                    // Insira a venda na coleção "sales" do Firestore
                    firestore.collection("Vendas")
                            .document(id_ticket)
                            .set(saleData)
                            .addOnSuccessListener(aVoid -> {
                                // O ticket normal foi registrado com sucesso
                                Log.d("Payment_confirmationFrag", "Normal ticket registered");
                                Toast.makeText(getContext(), "Comprado com sucesso!", Toast.LENGTH_SHORT).show();
                                // Faça qualquer ação adicional necessária

                            })
                            .addOnFailureListener(e -> {
                                // Ocorreu um erro ao registrar o ticket normal
                                Log.e("Payment_confirmationFrag", "Failed to register normal ticket", e);

                                // Exiba uma mensagem de erro ao usuário ou tome alguma outra ação

                            });
                }

                // Inserir tickets VIP
                for(int i = 0; i < quantidade_vip; i++) {
                    String id_ticket = UUID.randomUUID().toString();
                    saleData.put("id_ticket", id_ticket);
                    saleData.put("id_evento", id_evento);
                    saleData.put("id_comprador", auth.getCurrentUser().getUid());
                    saleData.put("tipo_ticket", "vip");
                    saleData.put("evento", evento);
                    saleData.put("nome", nome);
                    saleData.put("valor", precoV);
                    saleData.put("email", email);
                    saleData.put("metodo", metodo);
                    saleData.put("celular", celular);
                    saleData.put("data_compra", currentDate);
                    saleData.put("hora_compra", currentTime);
                    saleData.put("estado", "não validado");

                    // Insira a venda na coleção "sales" do Firestore
                    firestore.collection("Vendas")
                            .document(id_ticket)
                            .set(saleData)
                            .addOnSuccessListener(aVoid -> {
                                // O ticket VIP foi registrado com sucesso
                                Log.d("Payment_confirmationFrag", "VIP ticket registered");
                                Toast.makeText(getContext(), "Comprado com sucesso!", Toast.LENGTH_SHORT).show();
                                // Faça qualquer ação adicional necessária

                            })
                            .addOnFailureListener(e -> {
                                // Ocorreu um erro ao registrar o ticket VIP
                                Log.e("Payment_confirmationFrag", "Failed to register VIP ticket", e);

                                // Exiba uma mensagem de erro ao usuário ou tome alguma outra ação

                            });
                }

                // ...

            }
        });
        return paymentConfirmationBinding.getRoot();
    }
}