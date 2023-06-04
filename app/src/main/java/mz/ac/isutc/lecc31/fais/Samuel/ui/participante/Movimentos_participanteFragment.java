package mz.ac.isutc.lecc31.fais.Samuel.ui.participante;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentMovimentosParticipanteBinding;
import mz.ac.isutc.lecc31.fais.Samuel.models.Venda;

public class Movimentos_participanteFragment extends Fragment {
private FragmentMovimentosParticipanteBinding binding;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding  = FragmentMovimentosParticipanteBinding.inflate(getLayoutInflater());

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

        TextView tipoCompraTextView = createTableCell("Compra");
        TextView nomeEventoTextView = createTableCell(compra.getEvento());
        TextView valorTextView = createTableCell(compra.getValor()+"MT");
        TextView dataTextView = createTableCell(compra.getData_compra());
        TextView horaTextView = createTableCell(compra.getHora_compra());
        tableRow.addView(tipoCompraTextView);
        tableRow.addView(nomeEventoTextView);
        tableRow.addView(valorTextView);
        tableRow.addView(dataTextView);
        tableRow.addView(horaTextView);

        tableLayout.addView(tableRow);

    }

    private TextView createTableCell(String text) {
        TextView textView = new TextView(requireContext());
        textView.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        textView.setText(text);
        textView.setPadding(5, 5, 5, 5);
        textView.setBackgroundResource(R.drawable.border);
        return textView;
    }
}