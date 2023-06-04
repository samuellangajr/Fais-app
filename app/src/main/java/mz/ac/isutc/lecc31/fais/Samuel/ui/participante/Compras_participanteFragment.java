package mz.ac.isutc.lecc31.fais.Samuel.ui.participante;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentComprasParticipanteBinding;
import mz.ac.isutc.lecc31.fais.Samuel.models.Venda;


public class Compras_participanteFragment extends Fragment {
private FragmentComprasParticipanteBinding binding;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentComprasParticipanteBinding.inflate(getLayoutInflater());
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        TableLayout tableLayout = binding.tableLayout;

        // Obtenha as compras do Firestore e adicione as linhas à tabela
        firestore.collection("Vendas")
                .whereEqualTo("id_comprador", auth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Venda compra = documentSnapshot.toObject(Venda.class);
                        addCompraToTable(compra, tableLayout);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("ComprasFragment", "Failed to fetch purchases", e);
                });

        return binding.getRoot();
    }

    private void addCompraToTable(Venda compra, TableLayout tableLayout) {
        TableRow tableRow = new TableRow(requireContext());
        tableRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView tipoCompraTextView = createTableCell(compra.getTipo_ticket());
        TextView nomeEventoTextView = createTableCell(compra.getEvento());
        TextView dataTextView = createTableCell(compra.getData_compra());
        TextView horaTextView = createTableCell(compra.getHora_compra());
        tableRow.addView(tipoCompraTextView);
        tableRow.addView(nomeEventoTextView);
        tableRow.addView(dataTextView);
        tableRow.addView(horaTextView);

        tableLayout.addView(tableRow);
        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the QR code fragment and pass the sale ID as an argument
                QrCodeFragment qrCodeFragment = new QrCodeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ticket_id", compra.getId_ticket());
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_qr_code, bundle);
            }
        });
    }

    private TextView createTableCell(String text) {
        TextView textView = new TextView(requireContext());
        textView.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        textView.setText(text);
        textView.setPadding(10, 10, 10, 10);
        textView.setBackgroundResource(R.drawable.border);
        return textView;
    }

}