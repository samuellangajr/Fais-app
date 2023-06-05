package mz.ac.isutc.lecc31.fais.Samuel.ui.promotor;

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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.auth.User;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentParticipantesPromotorBinding;
import mz.ac.isutc.lecc31.fais.Samuel.models.Users;
import mz.ac.isutc.lecc31.fais.Samuel.models.Venda;
import mz.ac.isutc.lecc31.fais.Samuel.ui.participante.QrCodeFragment;


public class Participantes_promotorFragment extends Fragment {
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
private FragmentParticipantesPromotorBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentParticipantesPromotorBinding.inflate(getLayoutInflater());
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        TableLayout tableLayout = binding.tableLayout;

        String eventId="";
        Bundle args = getArguments();
        if (args != null) {
            eventId = args.getString("event_id");
            Toast.makeText(getContext(), "Sucesso ao obter o bundle", Toast.LENGTH_SHORT).show();
        }
        // Obtenha as compras do Firestore e adicione as linhas à tabela
        firestore.collection("Vendas")
                .whereEqualTo("id_evento",eventId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Venda compra = documentSnapshot.toObject(Venda.class);
                        addParticipanteToTable(compra, tableLayout);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("ParticipantesFragment", "Failed to fetch purchases", e);
                });

        return binding.getRoot();
    }

    private void addParticipanteToTable(Venda compra, TableLayout tableLayout) {
        TableRow tableRow = new TableRow(requireContext());
        tableRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView tipoCompraTextView = createTableCell(compra.getTipo_ticket());
        TextView nomeParticpanteTextView = createTableCell(compra.getNome());
        TextView dataTextView = createTableCell(compra.getData_compra());
        TextView estado = createTableCell(compra.getEstado());
        tableRow.addView(tipoCompraTextView);
        tableRow.addView(nomeParticpanteTextView);
        tableRow.addView(dataTextView);
        tableRow.addView(estado);

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