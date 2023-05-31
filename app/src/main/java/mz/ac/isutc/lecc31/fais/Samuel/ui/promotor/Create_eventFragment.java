package mz.ac.isutc.lecc31.fais.Samuel.ui.promotor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentCreateEventBinding;

public class Create_eventFragment extends Fragment {
private FragmentCreateEventBinding createEventBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       createEventBinding = FragmentCreateEventBinding.inflate(getLayoutInflater());
        ImageView imgGallery = createEventBinding.imgGallery;

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

}