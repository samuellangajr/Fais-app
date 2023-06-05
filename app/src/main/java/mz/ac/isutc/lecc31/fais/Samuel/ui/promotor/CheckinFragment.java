package mz.ac.isutc.lecc31.fais.Samuel.ui.promotor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentCheckinBinding;
import mz.ac.isutc.lecc31.fais.Samuel.models.Venda;

public class CheckinFragment extends Fragment {
private FragmentCheckinBinding checkinBinding;
private FirebaseFirestore firestore;
private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       checkinBinding = FragmentCheckinBinding.inflate(getLayoutInflater());
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Button btn_scan= checkinBinding.scan;
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               scanCode();
            }
        });
        return checkinBinding.getRoot();
    }
    private  void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan QR Code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);

        barLauncher.launch(options);

    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            firestore.collection("Vendas")
                    .whereEqualTo("id_ticket", result.getContents())
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Venda compra = documentSnapshot.toObject(Venda.class);
                            if (compra.getEstado().equals("não validado")) {
                                // Atualizar o valor do campo "estado" para "validado"
                                documentSnapshot.getReference().update("estado", "validado")
                                        .addOnSuccessListener(aVoid -> {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                                            builder.setTitle("Resultado do Scan");
                                            builder.setMessage("O Ticket é válido![Nome: "+compra.getNome()+", Tipo: "+compra.getTipo_ticket()+"]");
                                            builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                                            builder.setTitle("Resultado do Scan");
                                            builder.setMessage("Erro ao validar o ticket");
                                            Log.e("ComprasFragment", "Failed to update ticket", e);
                                            builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).show();
                                        });
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                                builder.setTitle("Resultado do Scan");
                                builder.setMessage("Ticket já foi validado");
                                builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).show();
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setTitle("Resultado do Scan");
                        builder.setMessage("Ticket Inválido");
                        Log.e("ComprasFragment", "Failed to fetch purchases", e);
                        builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).show();
                    });

        }
    });
}