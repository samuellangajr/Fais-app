package mz.ac.isutc.lecc31.fais.Samuel.ui.create_account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentCreateAccountBinding;

public class Create_accountFragment extends Fragment {
private FragmentCreateAccountBinding  rbinding;
private  boolean valid=true;
private  boolean senha=true;

private FirebaseAuth auth;
private FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rbinding = FragmentCreateAccountBinding.inflate(getLayoutInflater());

        //Inicializacao
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        //Registro
        rbinding.criarConta.setOnClickListener(v -> {
            String username=rbinding.nome.getText().toString();
            String email=rbinding.email.getText().toString();
            String pass=rbinding.password.getText().toString();
            String pass2=rbinding.repetirPassword.getText().toString();
            String perfil=String.valueOf(rbinding.tipoConta.getCheckedRadioButtonId());

            verificarCampo(rbinding.nome);
            verificarCampo(rbinding.email);
            verificarCampo(rbinding.password);
            verificarCampo(rbinding.repetirPassword);
            verificarSenha(rbinding.password,rbinding.repetirPassword);

            if (valid && senha){
                //Start the user registration process

                auth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(authResult -> {
                    FirebaseUser user=auth.getCurrentUser();
                    Toast.makeText(getContext(),"Conta criada com sucesso",Toast.LENGTH_LONG).show();

                    //Create collections
                    //assert user != null;
                    DocumentReference df=firestore.collection("Users").document(user.getUid());
                    Map<String,Object> userInfo= new HashMap<>();
                    userInfo.put("Username",username);
                    userInfo.put("UserEmail",email);
                    //Specify if the user is Admin
                    if(perfil.equals("0")){
                        userInfo.put("UserLevel","promotor");
                    } else {
                        userInfo.put("UserLevel","comprador");
                    }
                    df.set(userInfo);
                    LimparCampo();

                }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(),"Failed to Create Account",Toast.LENGTH_LONG).show();
                });

            }



        });

        return  rbinding.getRoot();
    }
    private void verificarCampo(@NonNull EditText editText){
        if(editText.getText().toString().isEmpty()){
            editText.setError("Preencha o campo");
            valid=false;
        }else{
            valid=true;
        }
    }
    private void verificarSenha(@NonNull EditText editText1, EditText editText2){
        if(editText1.getText().toString().equals(editText2.getText().toString())){
            senha=true;
        }else{
            editText1.setError("A senha não é igual");
            senha=false;
        }
    }
    private void LimparCampo(){
        rbinding.nome.setText("");
        rbinding.email.setText("");
        rbinding.password.setText("");
        rbinding.repetirPassword.setText("");
    }
}