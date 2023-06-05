package mz.ac.isutc.lecc31.fais.Samuel.ui.promotor;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentAnalisePromotorBinding;
import mz.ac.isutc.lecc31.fais.Samuel.models.Venda;


public class analise_promotorFragment extends Fragment {
private FragmentAnalisePromotorBinding binding;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnalisePromotorBinding.inflate(getLayoutInflater());
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Bundle args = getArguments();
        if (args != null) {
            String eventId = args.getString("event_id");
            populateCards(eventId);
        }else{
            Toast.makeText(getContext(), "Falha ao buscar o bundle", Toast.LENGTH_SHORT).show();
        }
        return binding.getRoot();
    }
    private void populateCards(String eventId) {
        Bundle args = getArguments();
        firestore.collection("Vendas")
                .whereEqualTo("id_evento", eventId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Toast.makeText(getContext(), "Sucesso ao buscar as analises!", Toast.LENGTH_SHORT).show();

                    Double valor_receber=0.0;
                    Double total_vendido=0.0;
                    int tickets_normal_vendidos=0;
                    int tickets_vip_vendidos=0;
                    Double preco_normal=Double.parseDouble(args.getString("event_priceN"));
                    Double preco_vip=Double.parseDouble(args.getString("event_priceN"));

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            Venda venda = documentSnapshot.toObject(Venda.class);
                            if (venda.getTipo_ticket().equals("normal")) {
                                tickets_normal_vendidos++;
                            } else {
                                tickets_vip_vendidos++;
                            }
                            total_vendido= (tickets_normal_vendidos*preco_normal)+(tickets_vip_vendidos*preco_vip);
                            valor_receber=total_vendido*0.7;
                        }
                    }
                    binding.totalVendido.setText(String.valueOf(total_vendido)+"MT");
                    binding.valorReceber.setText(String.valueOf(valor_receber)+"MT");
                    binding.normalVendido.setText(String.valueOf(tickets_normal_vendidos)+"/"+args.getString("event_quantN"));
                    binding.vipVendido.setText(String.valueOf(tickets_vip_vendidos)+"/"+args.getString("event_quantV"));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Falha ao buscar as analises!", Toast.LENGTH_SHORT).show();
                    Log.e("AnaliseFragment", "Failed to fetch purchases", e);
                });


    }
}