package mz.ac.isutc.lecc31.fais.Samuel.ui.promotor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapterPromotor extends FragmentStateAdapter {
    private Bundle bundle; //
    public ViewPagerAdapterPromotor(Dashboard_promotorFragment dashboard_promotorFragment, Bundle bundle) {
        super(dashboard_promotorFragment);
        this.bundle = bundle; // Armazena o Bundle recebido
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Restante do código...

        switch (position) {
            case 0:
                analise_promotorFragment analiseFragment = new analise_promotorFragment();
                analiseFragment.setArguments(bundle); // Define o Bundle nos fragments individualmente
                return analiseFragment;
            case 1:
                Participantes_promotorFragment participantesFragment = new Participantes_promotorFragment();
                participantesFragment.setArguments(bundle); // Define o Bundle nos fragments individualmente
                return participantesFragment;
            case 2:
                CheckinFragment checkinFragment = new CheckinFragment();
                checkinFragment.setArguments(bundle); // Define o Bundle nos fragments individualmente
                return checkinFragment;
            default:
                return new analise_promotorFragment();
        }
    }


    //numero de fragmentos
    @Override
    public int getItemCount() {
        return 3;
    }

}
