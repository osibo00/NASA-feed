package productions.darthplagueis.nasafeed.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import productions.darthplagueis.nasafeed.model.MarsRover.Photos;

/**
 * Created by oleg on 12/25/17.
 */

public class DataProvider {
    private final static String TAG = "DATAPROVIDER";
    private static HashMap<String, String> astronomyPhotos = new HashMap<>();
    private static List<Photos> curiosityPhotos = new ArrayList<>();
    private static List<Photos> spiritPhotos = new ArrayList<>();
    private static List<Photos> opportunityPhotos = new ArrayList<>();

    public static HashMap<String, String> addAstronomyList(HashMap<String, String> stringHashMap) {
        astronomyPhotos.putAll(stringHashMap);
        return astronomyPhotos;
    }

    public static List<Photos> addCuriosityList(List<Photos> photosList) {
        curiosityPhotos.addAll(photosList);
        return curiosityPhotos;
    }

    public static List<Photos> addSpiritList(List<Photos> photosList) {
        spiritPhotos.addAll(photosList);
        return spiritPhotos;
    }

    public static List<Photos> addOpportunityList(List<Photos> photosList) {
        opportunityPhotos.addAll(photosList);
        return opportunityPhotos;
    }

    public static HashMap<String, String> getAstronomyPhotos() {
        Log.d(TAG, "Astronomy List size: " + astronomyPhotos.size());
        return astronomyPhotos;
    }

    public static List<Photos> getCuriosityPhotos() {
        Log.d(TAG, "Curiosity List size: " + curiosityPhotos.size());
        return curiosityPhotos;
    }

    public static List<Photos> getSpiritPhotos() {
        Log.d(TAG, "Spirit List size: " + curiosityPhotos.size());
        return spiritPhotos;
    }

    public static List<Photos> getOpportunityPhotos() {
        Log.d(TAG, "Opportunity List size: " + curiosityPhotos.size());
        return opportunityPhotos;
    }
}
