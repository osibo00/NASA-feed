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
    private final static String TAG = "DATA_PROVIDER";
    private static HashMap<String, String> astronomyPhotos = new HashMap<>();
    private static HashMap<String, String> curiosityManifest = new HashMap<>();
    private static HashMap<String, String> spiritManifest = new HashMap<>();
    private static HashMap<String, String> opportunityManifest = new HashMap<>();
    private static List<Photos> curiosityPhotos = new ArrayList<>();
    private static List<Photos> spiritPhotos = new ArrayList<>();
    private static List<Photos> opportunityPhotos = new ArrayList<>();

    public static HashMap<String, String> addAstronomyList(HashMap<String, String> stringHashMap) {
        astronomyPhotos.putAll(stringHashMap);
        return astronomyPhotos;
    }

    public static HashMap<String, String> addCuriosityManifestList(HashMap<String, String> stringHashMap) {
        curiosityManifest.putAll(stringHashMap);
        return curiosityManifest;
    }

    public static HashMap<String, String> addOpportunityManifestList(HashMap<String, String> stringHashMap) {
        opportunityManifest.putAll(stringHashMap);
        return opportunityManifest;
    }

    public static HashMap<String, String> addSpiritManifestList(HashMap<String, String> stringHashMap) {
        spiritManifest.putAll(stringHashMap);
        return spiritManifest;
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

    public static HashMap<String, String> getCuriosityManifest() {
        return curiosityManifest;
    }

    public static HashMap<String, String> getSpiritManifest() {
        return spiritManifest;
    }

    public static HashMap<String, String> getOpportunityManifest() {
        return opportunityManifest;
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
