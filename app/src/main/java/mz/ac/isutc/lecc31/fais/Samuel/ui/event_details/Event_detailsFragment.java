package mz.ac.isutc.lecc31.fais.Samuel.ui.event_details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentEventDetailsBinding;
import mz.ac.isutc.lecc31.fais.Samuel.models.Evento;
import mz.ac.isutc.lecc31.fais.Samuel.models.Users;


public class Event_detailsFragment extends Fragment {
private FragmentEventDetailsBinding eventDetailsBinding;
private FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        eventDetailsBinding = FragmentEventDetailsBinding.inflate(getLayoutInflater());
        firestore = FirebaseFirestore.getInstance();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Evento evento = (Evento) bundle.getSerializable("evento");
            if (evento != null) {
                // Use os dados do evento para preencher os elementos da tela
                eventDetailsBinding.eventCategorie.setText("Categoria:"+evento.getCategoria());
                eventDetailsBinding.eventTitle.setText(evento.getNome());
                eventDetailsBinding.eventPrice.setText(evento.getPreco_normal() + "MT");
                eventDetailsBinding.eventVipPrice.setText("Vip: " + evento.getPreco_vip() + "MT");
                eventDetailsBinding.eventDetail.setText(evento.getDescricao());
                // Preencha os outros elementos da tela com os dados do evento
                String imageUrl = evento.getImage_url();
                Glide.with(getContext())
                        .load(imageUrl)
                        //.placeholder(R.drawable.loading) // Imagem de placeholder enquanto a imagem é carregada
                        //.error(R.drawable.erro) // Imagem de erro, caso ocorra algum problema ao carregar a imagem
                        .into(eventDetailsBinding.eventImage);
                getUserDetails(evento.getId_promotor());
            }
        }

        eventDetailsBinding.comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Evento event = (Evento) bundle.getSerializable("evento");
                Bundle bundle = new Bundle();
                bundle.putString("event",event.getNome());
                if(eventDetailsBinding.quantNormal.getText().toString().equals("")){
                    bundle.putString("quant_normal","0");
                    bundle.putString("prec_normal","0");
                }else{
                    bundle.putString("quant_normal",eventDetailsBinding.quantNormal.getText().toString());
                    bundle.putString("prec_normal",event.getPreco_normal());
                }

                if(eventDetailsBinding.quantVip.getText().toString().equals("")) {
                    bundle.putString("quant_vip", "0");
                    bundle.putString("prec_vip","0");
                }else{
                    bundle.putString("quant_vip",eventDetailsBinding.quantVip.getText().toString());
                    bundle.putString("prec_vip",event.getPreco_vip());
                }

                bundle.putString("event_id",event.getId_evento());
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_checkout,bundle);
            }
        });

        return eventDetailsBinding.getRoot();
    }

    private void getUserDetails(String promotorId) {
        CollectionReference usersCollection = firestore.collection("Users");

        usersCollection.document(promotorId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Users user = documentSnapshot.toObject(Users.class);
                eventDetailsBinding.eventPromotor.setText(user.getUsername());
                eventDetailsBinding.eventEmailPromotor.setText(user.getUseremail());
            } else {
                Log.d("Event_detailsFragment", "User not found");
            }
        }).addOnFailureListener(e -> {
            Log.e("Event_detailsFragment", "Failed to get user details", e);
            Toast.makeText(getContext(), "Ocorreu uma falha ao obter os detalhes do usuário.", Toast.LENGTH_SHORT).show();
        });
    }
}