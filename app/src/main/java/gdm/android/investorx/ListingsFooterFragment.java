package gdm.android.investorx;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ListingsFooterFragment extends Fragment {
    private OnMainFilterChangeListener filterChangeListener;
    private Button publicButton;
    private Button internalButton;
    private Button currentlyActiveButton;
    private Filter.Main filterType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_listings_footer, container, false);
        publicButton = (Button) rootView.findViewById(R.id.public_filter_button);
        internalButton = (Button) rootView.findViewById(R.id.internal_filter_button);

        publicButton.setOnClickListener(new FilterButtonListener(Filter.Main.PUBLIC));
        internalButton.setOnClickListener(new FilterButtonListener(Filter.Main.INTERNAL));

        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        filterChangeListener = (OnMainFilterChangeListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        filterChangeListener = null;
    }

    public void setActive(Filter.Main filterType) {
        if (currentlyActiveButton != null) {
            currentlyActiveButton.setActivated(false);
        }

        if (Filter.Main.PUBLIC == filterType) {
            currentlyActiveButton = publicButton;
        } else if (Filter.Main.INTERNAL == filterType) {
            currentlyActiveButton = internalButton;
        }
        this.filterType = filterType;
        currentlyActiveButton.setActivated(true);
    }

    public interface OnMainFilterChangeListener {
        public void onMainFilterClick(Filter.Main filterType);

        public void onMainFilterChange(Filter.Main filterType);
    }

    private class FilterButtonListener implements View.OnClickListener {

        Filter.Main filterType;

        FilterButtonListener(Filter.Main filterType) {
            this.filterType = filterType;
        }

        @Override
        public void onClick(View v) {
            filterChangeListener.onMainFilterClick(filterType);

            if (v == currentlyActiveButton) {
                return;
            }

            setActive(filterType);
            filterChangeListener.onMainFilterChange(filterType);
        }
    }
}
