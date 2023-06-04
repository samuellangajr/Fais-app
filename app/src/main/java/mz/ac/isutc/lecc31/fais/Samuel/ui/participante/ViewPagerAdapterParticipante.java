package mz.ac.isutc.lecc31.fais.Samuel.ui.participante;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapterParticipante extends FragmentStateAdapter {

        public ViewPagerAdapterParticipante(Dashboard_participanteFragment dashboard_participanteFragment) {
            super(dashboard_participanteFragment);
        }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //Permite a troca de fragments no viewpage
        switch (position){
            case 0:
                return new Compras_participanteFragment();
            case 1:
                return new Reservas_participanteFragment();
            case 2:
                return new Movimentos_participanteFragment();
            default:
                return new Compras_participanteFragment();
        }
    }

    //numero de fragmentos
    @Override
    public int getItemCount() {
        return 3;
    }

}
