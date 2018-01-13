package productions.darthplagueis.nasafeed;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import io.github.yavski.fabspeeddial.FabSpeedDial;
import me.toptas.fancyshowcase.FancyShowCaseView;
import productions.darthplagueis.nasafeed.fragment.AstronomyFragment;
import productions.darthplagueis.nasafeed.fragment.OpportunityFragment;
import productions.darthplagueis.nasafeed.fragment.SpiritFragment;
import productions.darthplagueis.nasafeed.fragment.CuriosityFragment;

public class FragmentsActivity extends AppCompatActivity {
    private final static String TAG = "FRAGMENTS ACTIVITY";
    private String roverChoice = "rover choice";
    public static boolean showTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roverChoice = extras.getString(MainActivity.ROVER_CHOICE);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (roverChoice) {
            case "opportunity":
                showTextView = true;
                fragmentTransaction.add(R.id.fragment_container, OpportunityFragment.newInstance());
                fragmentTransaction.commit();
                break;
            case "curiosity":
                showTextView = true;
                fragmentTransaction.add(R.id.fragment_container, CuriosityFragment.newInstance());
                fragmentTransaction.commit();
                break;
            case "spirit":
                showTextView = false;
                fragmentTransaction.add(R.id.fragment_container, SpiritFragment.newInstance());
                fragmentTransaction.commit();
                break;
            case "astronomy":
                AstronomyFragment astronomyFragment = new AstronomyFragment();
                fragmentTransaction.add(R.id.fragment_container, astronomyFragment);
                fragmentTransaction.commit();
                break;
            default:
                break;
        }

    }

    public static void setShowCaseView(Activity activity, FabSpeedDial fabSpeedDial) {
        new FancyShowCaseView.Builder(activity)
                .focusOn(fabSpeedDial)
                .title("See most recent, least recent or search images by day")
                .focusCircleAtPosition(fabSpeedDial.getScrollX(), fabSpeedDial.getScrollY(), fabSpeedDial.getWidth())
                .backgroundColor(Color.parseColor("#80a7001b"))
                .focusBorderColor(Color.parseColor("#5b8bde"))
                .focusBorderSize(10)
                .delay(1500)
                //.showOnce("fancy1")
                .build()
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + "CALLED");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + "CALLED");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + "CALLED");

    }
}