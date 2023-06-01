package mz.ac.isutc.lecc31.fais.Samuel.ui.promotor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentCreateEventBinding;

public class Create_eventFragment extends Fragment {
private FragmentCreateEventBinding createEventBinding;
private  boolean valid=true;
private FirebaseAuth auth;
private FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createEventBinding = FragmentCreateEventBinding.inflate(getLayoutInflater());
        ImageView imgGallery = createEventBinding.imgGallery;

        //Inicializacao
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();


        createEventBinding.criarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pega os dados do formulario
                String nome = createEventBinding.nome.getText().toString();
                String descricao = createEventBinding.descricao.getText().toString();
                String data = createEventBinding.data.getText().toString();
                String hora = createEventBinding.time.getText().toString();
                String local = createEventBinding.local.getText().toString();
                String quant_normal = createEventBinding.quantNormal.getText().toString();
                String quant_vip = createEventBinding.quantVip.getText().toString();
                String preco_normal = createEventBinding.precoNormal.getText().toString();
                String preco_vip = createEventBinding.precoVip.getText().toString();
                String categoria = createEventBinding.categoria.getSelectedItem().toString();

                verificarCampo(createEventBinding.nome);
                verificarCampo(createEventBinding.descricao);
                verificarCampo(createEventBinding.data);
                verificarCampo(createEventBinding.time);
                verificarCampo(createEventBinding.local);
                verificarCampo(createEventBinding.quantNormal);
                verificarCampo(createEventBinding.quantVip);
                verificarCampo(createEventBinding.precoNormal);
                verificarCampo(createEventBinding.precoVip);

                if (valid){
                    String id_evento= UUID.randomUUID().toString();
                    Map<String,Object> eventInfo= new HashMap<>();
                    eventInfo.put("id_evento",id_evento);
                    eventInfo.put("nome",nome);
                    eventInfo.put("descricao",descricao);
                    eventInfo.put("data",data);
                    eventInfo.put("hora",hora);
                    eventInfo.put("local",local);
                    eventInfo.put("quant_normal",quant_normal);
                    eventInfo.put("quant_vip",quant_vip);
                    eventInfo.put("preco_normal",preco_normal);
                    eventInfo.put("preco_vip",preco_vip);
                    eventInfo.put("categoria",categoria);
                    eventInfo.put("id_promotor",auth.getCurrentUser().getUid());

                    firestore.collection("Evento").document(id_evento).set(eventInfo).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(),"Salvo com sucesso!!",Toast.LENGTH_SHORT).show();
                            LimparCampo();
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getContext(),"Ocorreu uma falha!!",Toast.LENGTH_SHORT).show();});
                }

            }
        });

        createEventBinding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, 1000);
            }
        });
        return createEventBinding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 1000) {
                ImageView imgGallery = createEventBinding.imgGallery;
                imgGallery.setImageURI(data.getData());
            }
        }
    }
    private void verificarCampo(@NonNull EditText editText){
        if(editText.getText().toString().isEmpty()){
            editText.setError("Preencha o campo");
            valid=false;
        }else{
            valid=true;
        }
    }
    private void LimparCampo(){
        createEventBinding.nome.setText("");
        createEventBinding.descricao.setText("");
        createEventBinding.data.setText("");
        createEventBinding.time.setText("");
        createEventBinding.local.setText("");
        createEventBinding.quantNormal.setText("");
        createEventBinding.quantVip.setText("");
        createEventBinding.precoNormal.setText("");
        createEventBinding.precoVip.setText("");
    }

}