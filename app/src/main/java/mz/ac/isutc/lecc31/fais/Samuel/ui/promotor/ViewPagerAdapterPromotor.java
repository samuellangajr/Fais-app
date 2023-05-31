package mz.ac.isutc.lecc31.fais.Samuel.ui.promotor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapterPromotor extends FragmentStateAdapter {

        public ViewPagerAdapterPromotor(Dashboard_promotorFragment dashboard_promotorFragment) {
            super(dashboard_promotorFragment);
        }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //Permite a troca de fragments no viewpage
        switch (position){
            case 0:
                return new Compras_promotorFragment();
            case 1:
                return new Participantes_promotorFragment();
            case 2:
                return new Reservados_promotorFragment();
            case 3:
                return new Movimentos_promotorFragment();
            case 4:
                return new CheckinFragment();
            default:
                return new Compras_promotorFragment();
        }
    }

    //numero de fragmentos
    @Override
    public int getItemCount() {
        return 5;
    }

}
