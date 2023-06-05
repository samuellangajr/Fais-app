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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentHomePromotorBinding;
import mz.ac.isutc.lecc31.fais.Samuel.models.Evento;
import mz.ac.isutc.lecc31.fais.Samuel.models.Venda;
import mz.ac.isutc.lecc31.fais.Samuel.ui.participante.QrCodeFragment;

public class Home_promotorFragment extends Fragment {
 private FragmentHomePromotorBinding binding;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomePromotorBinding.inflate(getLayoutInflater());

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        TableLayout tableLayout = binding.tableLayout;

        // Obtenha as compras do Firestore e adicione as linhas à tabela
        firestore.collection("Evento")
                .whereEqualTo("id_promotor", auth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Evento evento = documentSnapshot.toObject(Evento.class);
                        addEventToTable(evento, tableLayout);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("HomePromotorFragment", "Failed to fetch purchases", e);
                });


        return binding.getRoot();
    }

    private void addEventToTable(Evento evento, TableLayout tableLayout) {
        TableRow tableRow = new TableRow(requireContext());
        tableRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView CategoriaEventoTextView = createTableCell(evento.getCategoria());
        TextView nomeEventoTextView = createTableCell(evento.getNome());
        TextView dataTextView = createTableCell(evento.getData());
        tableRow.addView(CategoriaEventoTextView);
        tableRow.addView(nomeEventoTextView);
        tableRow.addView(dataTextView);


        tableLayout.addView(tableRow);
        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("event_id", evento.getId_evento());
                bundle.putString("event_priceN", evento.getPreco_normal());
                bundle.putString("event_priceV", evento.getPreco_vip());
                bundle.putString("event_quantN", evento.getQuant_normal());
                bundle.putString("event_quantV", evento.getQuant_vip());
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_dashboard_promotor, bundle);
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