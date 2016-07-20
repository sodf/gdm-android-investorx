package gdm.android.investorx;

import android.app.ListFragment;
import android.view.View;

public class InternalFragment extends ListFragment {

    public void setVisibility(int visibility) {
        View view = getView();
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

}
