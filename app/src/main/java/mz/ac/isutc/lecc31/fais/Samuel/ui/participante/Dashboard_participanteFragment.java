package mz.ac.isutc.lecc31.fais.Samuel.ui.participante;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentDashboardParticipanteBinding;

public class Dashboard_participanteFragment extends Fragment {
    ViewPagerAdapterParticipante adapter;
private FragmentDashboardParticipanteBinding dashboard_participanteBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashboard_participanteBinding = FragmentDashboardParticipanteBinding.inflate(getLayoutInflater());


        //Instanciar objecto viewPageAdapter
        adapter = new ViewPagerAdapterParticipante(this);

        //Set adapter ao ViewPage do Activity main
        dashboard_participanteBinding.viewPage.setAdapter(adapter);

        //Event Listner ao selecionar um item do tabLayout
        dashboard_participanteBinding.tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        //Devolve o item selecionado
                        dashboard_participanteBinding.viewPage.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {}
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {}
                }
        );

        //Adicionar um pageSelected Callback metodo
        dashboard_participanteBinding.viewPage.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);

                        //vamos fazer o set da posição no tab layout
                        dashboard_participanteBinding.tabLayout.getTabAt(position).select();
                    }
                }
        );
        return dashboard_participanteBinding.getRoot();
    }
}