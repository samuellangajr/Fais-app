package mz.ac.isutc.lecc31.fais.Samuel.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentHomeBinding;
import mz.ac.isutc.lecc31.fais.Samuel.models.Evento;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<Evento> eventList = new ArrayList<>();
    private FirebaseFirestore firestore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        firestore = FirebaseFirestore.getInstance();

        getEventsFromFirestore();

        return root;
    }

    private void getEventsFromFirestore() {
        CollectionReference eventCollection = firestore.collection("Evento");

        eventCollection.orderBy("data").get().addOnSuccessListener(queryDocumentSnapshots -> {
            Log.d("HomeFragment", "Events retrieved successfully");
            eventList.clear();

            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Evento event = document.toObject(Evento.class);
                eventList.add(event);
            }

            updateEventUI();
        }).addOnFailureListener(e -> {
            Log.e("HomeFragment", "Failed to get events from Firestore", e);
            Toast.makeText(getContext(), "Ocorreu uma falha ao obter os eventos.", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateEventUI() {
        LinearLayout eventCardContainer = binding.eventCardContainer;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        eventCardContainer.removeAllViews();

        for (Evento event : eventList) {
            View eventCardView = inflater.inflate(R.layout.event_card, eventCardContainer, false);

            ImageView eventImage = eventCardView.findViewById(R.id.eventImage);
            TextView eventTitle = eventCardView.findViewById(R.id.eventTitle);
            TextView eventPrice = eventCardView.findViewById(R.id.eventPrice);
            TextView eventVipPrice = eventCardView.findViewById(R.id.eventVipPrice);
            TextView eventTickets = eventCardView.findViewById(R.id.eventTickets);
            TextView eventDate = eventCardView.findViewById(R.id.eventDate);

            int quantidadeNormal = Integer.parseInt(event.getQuant_normal());
            int quantidadeVip = Integer.parseInt(event.getQuant_vip());
            int totalQuantidade = quantidadeNormal + quantidadeVip;

            eventTitle.setText(event.getNome());
            eventPrice.setText(event.getPreco_normal()+"MT");
            eventVipPrice.setText("Vip:"+event.getPreco_vip()+"MT");
            eventTickets.setText("Disponivel:"+totalQuantidade);
            eventDate.setText(event.getData());

            // Defina aqui a lógica para carregar a imagem do evento no ImageView eventImage
            String imageUrl = event.getImage_url(); // Supondo que você tenha a URL da imagem no objeto Evento

            Glide.with(getContext())
                    .load(imageUrl)
                    //.placeholder(R.drawable.loading) // Imagem de placeholder enquanto a imagem é carregada
                    //.error(R.drawable.erro) // Imagem de erro, caso ocorra algum problema ao carregar a imagem
                    .into(eventImage);
            eventCardView.setOnClickListener(view -> openEventDetails(event));

            eventCardContainer.addView(eventCardView);
        }
    }
    private void openEventDetails(Evento evento) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("evento", evento);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.nav_event_details, bundle);
    }

}
