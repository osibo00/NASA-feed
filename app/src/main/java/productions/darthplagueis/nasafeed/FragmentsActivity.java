package productions.darthplagueis.nasafeed;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import productions.darthplagueis.nasafeed.fragment.AstronomyFragment;
import productions.darthplagueis.nasafeed.fragment.CuriosityFragment;
import productions.darthplagueis.nasafeed.fragment.OpportunityFragment;
import productions.darthplagueis.nasafeed.fragment.SpiritFragment;

public class FragmentsActivity extends AppCompatActivity {
    private final static String TAG = "FRAGMENTS ACTIVITY";
    private String roverChoice = "rover_choice";
    public static boolean showTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roverChoice = extras.getString(MainActivity.ROVERCHOICE);
        }

        switch (roverChoice) {
            case "opportunity":
                showTextView = true;
                OpportunityFragment opportunityFragment = new OpportunityFragment();
                FragmentManager fragmentManager00 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction00 = fragmentManager00.beginTransaction();
                fragmentTransaction00.add(R.id.fragment_container, opportunityFragment);
                fragmentTransaction00.commit();
                break;
            case "curiosity":
                showTextView = true;
                CuriosityFragment curiosityFragment = new CuriosityFragment();
                FragmentManager fragmentManager01 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction01 = fragmentManager01.beginTransaction();
                fragmentTransaction01.add(R.id.fragment_container, curiosityFragment);
                fragmentTransaction01.commit();
                break;
            case "spirit":
                showTextView = false;
                SpiritFragment spiritFragment = new SpiritFragment();
                FragmentManager fragmentManager02 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction02 = fragmentManager02.beginTransaction();
                fragmentTransaction02.add(R.id.fragment_container, spiritFragment);
                fragmentTransaction02.commit();
                break;
            case "astronomy":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                AstronomyFragment astronomyFragment = new AstronomyFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, astronomyFragment);
                fragmentTransaction.commit();
            default:
                break;
        }

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