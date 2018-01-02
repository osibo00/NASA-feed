package productions.darthplagueis.nasafeed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import productions.darthplagueis.nasafeed.util.DataProvider;

public class RoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover);

        Toolbar toolbar = (Toolbar) findViewById(R.id.rover_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setText(R.id.rover_curiosity_rovername, DataProvider.getCuriosityManifest().get("name"), R.id.rover_curiosity_status, DataProvider.getCuriosityManifest().get("status"), R.id.rover_curiosity_maxdate, DataProvider.getCuriosityManifest().get("maxDate"),
                R.id.rover_curiosity_launchdate, DataProvider.getCuriosityManifest().get("launchDate"), R.id.rover_curiosity_landing, DataProvider.getCuriosityManifest().get("landingDate"), R.id.rover_curiosity_maxsol, DataProvider.getCuriosityManifest().get("maxSol"),
                R.id.rover_curiosity_totalphotos, DataProvider.getCuriosityManifest().get("totalPhotos"));

        setText(R.id.rover_opp_rovername, DataProvider.getOpportunityManifest().get("name"), R.id.rover_opp_status, DataProvider.getOpportunityManifest().get("status"), R.id.rover_opp_maxdate, DataProvider.getOpportunityManifest().get("maxDate"),
                R.id.rover_opp_launchdate, DataProvider.getOpportunityManifest().get("launchDate"), R.id.rover_opp_landing, DataProvider.getOpportunityManifest().get("landingDate"), R.id.rover_opp_maxsol, DataProvider.getOpportunityManifest().get("maxSol"),
                R.id.rover_opp_totalphotos, DataProvider.getOpportunityManifest().get("totalPhotos"));

        setText(R.id.rover_spirit_rovername, DataProvider.getSpiritManifest().get("name"), R.id.rover_spirit_status, DataProvider.getSpiritManifest().get("status"), R.id.rover_spirit_maxdate, DataProvider.getSpiritManifest().get("maxDate"),
                R.id.rover_spirit_launchdate, DataProvider.getSpiritManifest().get("launchDate"), R.id.rover_spirit_landing, DataProvider.getSpiritManifest().get("landingDate"), R.id.rover_spirit_maxsol, DataProvider.getSpiritManifest().get("maxSol"),
                R.id.rover_spirit_totalphotos, DataProvider.getSpiritManifest().get("totalPhotos"));
    }

    private void setText(int rover_rovername, String name, int rover_status, String status, int rover_maxdate, String maxDate, int rover_launchdate, String launchDate, int rover_landing, String landingDate,
                         int rover_maxsol, String maxSol, int rover_totalphotos, String totalPhotos) {
        TextView rover = (TextView) findViewById(rover_rovername);
        rover.setText(name);
        TextView roverStatus = (TextView) findViewById(rover_status);
        roverStatus.setText(status);
        TextView roverMaxDate = (TextView) findViewById(rover_maxdate);
        roverMaxDate.setText(maxDate);
        TextView roverLaunchDate = (TextView) findViewById(rover_launchdate);
        roverLaunchDate.setText(launchDate);
        TextView roverLanding = (TextView) findViewById(rover_landing);
        roverLanding.setText(landingDate);
        TextView roverMaxSol = (TextView) findViewById(rover_maxsol);
        roverMaxSol.setText(maxSol);
        TextView roverTotalPhotos = (TextView) findViewById(rover_totalphotos);
        roverTotalPhotos.setText(totalPhotos);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
