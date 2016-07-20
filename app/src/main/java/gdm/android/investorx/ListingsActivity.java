package gdm.android.investorx;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

public class ListingsActivity extends AppCompatActivity implements ListingsHeaderFragment.OnSecondaryFilterChangeListener, ListingsFooterFragment.OnMainFilterChangeListener {

    Filter.Main mainFilter;
    protected ListFragment activeListFragment;
    ListingsHeaderFragment headerFragment;
    ListingsFooterFragment footerFragment;
    protected PublicFragment publicFragment;
    protected InternalFragment internalFragment;
    Toolbar toolbar;
    TextView toolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        FragmentManager fm = getFragmentManager();
        headerFragment = (ListingsHeaderFragment) fm.findFragmentById(R.id.fragment_listings_header);
        footerFragment = (ListingsFooterFragment) fm.findFragmentById(R.id.fragment_listings_footer);
        publicFragment = (PublicFragment) fm.findFragmentById(R.id.fragment_publiclist);
        internalFragment = (InternalFragment) fm.findFragmentById(R.id.fragment_internallist);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
    }

    @Override
    public void onMainFilterClick(Filter.Main filterType) {

    }

    @Override
    public void onMainFilterChange(Filter.Main filterType) {
        mainFilter = filterType;

        Map<Filter.Main, Filter.Secondary> filterMap = headerFragment.getFilterMap();
        if (filterType == Filter.Main.PUBLIC) {
            publicFragment.setVisibility(View.VISIBLE);
            activeListFragment = publicFragment;
            internalFragment.setVisibility(View.GONE);
        } else if (filterType == Filter.Main.INTERNAL) {
            internalFragment.setVisibility(View.GONE);
            activeListFragment = internalFragment;
            publicFragment.setVisibility(View.GONE);
        }
        updateHeader(filterType);
    }

    protected void updateHeader(Filter.Main filterType) {
        headerFragment.updateSubNavButtonVisibility(filterType);
    }

    private void setToolbarTitle() {
        if (mainFilter == Filter.Main.PUBLIC) {
            toolbarTitle.setText(getResources().getString(R.string.public_filter_button));
        } else if (mainFilter == Filter.Main.INTERNAL) {
            toolbarTitle.setText(getResources().getString(R.string.internal_filter_button));
        }
    }


    @Override
    public void onSecondaryFilterChange(Filter.Main mainFilter, Filter.Secondary filterType) {

    }
}
