package gdm.android.investorx;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class ListingsHeaderFragment extends Fragment {

    private Button watchingPublicButton;
    private Button allPublicButton;
    private Button watchingInternalButton;
    private Button allInternalButton;
    private OnSecondaryFilterChangeListener filterChangeListener;
    Map<Filter.Main, Button> activatedButtons = new HashMap<>();
    Map<Filter.Main, Filter.Secondary> filterMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_listings_header, container, false);

        watchingPublicButton = (Button) rootView.findViewById(R.id.watching_public_filter_button);
        allPublicButton = (Button) rootView.findViewById(R.id.all_public_filter_button);
        watchingInternalButton = (Button) rootView.findViewById(R.id.watching_internal_filter_button);
        allInternalButton = (Button) rootView.findViewById(R.id.all_internal_filter_button);

        watchingPublicButton.setOnClickListener(new HeaderFilterButtonListener(Filter.Main.PUBLIC, Filter.Secondary.WATCHING));
        allPublicButton.setOnClickListener(new HeaderFilterButtonListener(Filter.Main.PUBLIC, Filter.Secondary.ALL));
        watchingInternalButton.setOnClickListener(new HeaderFilterButtonListener(Filter.Main.INTERNAL, Filter.Secondary.WATCHING));
        allInternalButton.setOnClickListener(new HeaderFilterButtonListener(Filter.Main.INTERNAL, Filter.Secondary.ALL));

        setDefaultSubNavButtons();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        filterChangeListener = (OnSecondaryFilterChangeListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        filterChangeListener = null;
    }

    private void setDefaultSubNavButtons() {
        watchingPublicButton.setActivated(true);
        watchingInternalButton.setActivated(true);
        activatedButtons.put(Filter.Main.PUBLIC, watchingPublicButton);
        activatedButtons.put(Filter.Main.INTERNAL, watchingInternalButton);
        filterMap.put(Filter.Main.PUBLIC, Filter.Secondary.WATCHING);
        filterMap.put(Filter.Main.INTERNAL, Filter.Secondary.WATCHING);
    }

    public Map<Filter.Main, Filter.Secondary> getFilterMap() {
        return this.filterMap;
    }

    protected void updateSubNavButtonVisibility(Filter.Main filter) {

        int publicVisibility = filter == Filter.Main.PUBLIC ? View.VISIBLE : View.GONE;
        int internalVisibility = filter == Filter.Main.INTERNAL ? View.VISIBLE : View.GONE;

        watchingPublicButton.setVisibility(publicVisibility);
        allPublicButton.setVisibility(publicVisibility);
        watchingInternalButton.setVisibility(internalVisibility);
        allInternalButton.setVisibility(internalVisibility);
    }

    public interface OnSecondaryFilterChangeListener {
        void onSecondaryFilterChange(Filter.Main mainFilter, Filter.Secondary filterType);
    }

    class HeaderFilterButtonListener implements View.OnClickListener {

        private final Filter.Main mainFilter;
        private Filter.Secondary secondaryFilter;

        HeaderFilterButtonListener(Filter.Main mainFilter, Filter.Secondary secondaryFilter) {
            this.mainFilter = mainFilter;
            this.secondaryFilter = secondaryFilter;
        }

        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            filterChangeListener.onSecondaryFilterChange(this.mainFilter, this.secondaryFilter);
            activatedButtons.get(this.mainFilter).setActivated(false);
            button.setActivated(true);
            activatedButtons.put(this.mainFilter, button);
            filterMap.put(this.mainFilter, this.secondaryFilter);
        }
    }
}
