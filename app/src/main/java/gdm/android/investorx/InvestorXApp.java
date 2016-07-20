package gdm.android.investorx;

import android.app.Application;
import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class InvestorXApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "wTszjl9BuUhjP0OeiEQKE2uvliUQGXLob7Ng0V2p", "SXnkLdCjJiUd0uKEYrPbUn2gIRIH3ZNMcfRAtqfy");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        Intent intent = new Intent(this, ListingsActivity.class);
        startActivity(intent);
    }
}
