package mz.ac.isutc.lecc31.fais.Samuel.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import mz.ac.isutc.lecc31.fais.Samuel.MainActivity;
import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding lbinding;
    private boolean valid=true;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lbinding = FragmentLoginBinding.inflate(getLayoutInflater());
        //Inicializando
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        lbinding.navCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_create_account);
            }
        });
    lbinding.entrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email=lbinding.email.getText().toString();
            String pass=lbinding.password.getText().toString();
            verificarCampo(lbinding.email);
            verificarCampo(lbinding.password);

            if (valid){
                auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(authResult -> {
                    Toast.makeText(getContext(),"Logged Successfully",Toast.LENGTH_SHORT).show();
                    checkUserAccessLevel(Objects.requireNonNull(authResult.getUser()).getUid());

                }).addOnFailureListener(e -> Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show());


            }
        }
    });
        return lbinding.getRoot();
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df =firestore.collection("Users").document(uid);
        //Extract Data from the document
        df.get().addOnSuccessListener(documentSnapshot -> {
            //Log.d("TAG","Sucesso"+documentSnapshot.getData());
            //Identificando o perfil
            if (Objects.equals(documentSnapshot.getString("UserLevel"), "promotor")){
                // user is promotor
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_home_promotor);

            }else if (Objects.equals(documentSnapshot.getString("UserLevel"), "comprador")){
                // user is comprador
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_dashboard_participante);;

            }

        });

    }

    private void verificarCampo(@NonNull EditText editText){
        if(editText.getText().toString().isEmpty()){
            editText.setError("Preenca o campo");
            valid=false;
        }else{
            valid=true;
        }
    }

}