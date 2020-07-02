package com.example.groceryshare;
// Java program to calculate Distance Between Two Points on Earth
import org.json.JSONException;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

class DistanceCalculator {

    private Object Tuple;

    public class Tuple<X, Y> {
        public final X x;
        public final Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return jsonText;
        } finally {
            is.close();
        }
    }

    public static double distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2)
    {
        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return ((c * r) / 1.609344);
    }

    public static double[] addressToLonLat(String address) throws IOException, JSONException {
        double[] ans = new double[2];

        String query = "";
        String[] split = address.split(" ");

        for (int i = 0; i < split.length; i++) {
            query += split[i];
            if (i < (split.length - 1)) {
                query += "+";
            }
        }
        String json = readJsonFromUrl("https://nominatim.openstreetmap.org/search?q=" + query + "&format=json&addressdetails=1");

        int firstIndexLat = json.indexOf("\"lat\":");
        int firstIndexLon = json.indexOf("\"lon\":");
        int firstIndexEnd = json.indexOf("\",\"display_name\"");

        double lat = Double.parseDouble(json.substring(firstIndexLat + 7, firstIndexLon -2));
        double lon = Double.parseDouble(json.substring(firstIndexLon + 7, firstIndexEnd));

        ans[0] = lat;
        ans[1] = lon;

        return ans;
    }

    // driver code
    public static void main(String[] args) throws IOException, JSONException {

        String address1 = "98-30 57 Ave Corona NY 11368";
        String address2 = "Aldi Rego Park";

        double[] ans1 = addressToLonLat(address1);
        double[] ans2 =  addressToLonLat(address2);

        System.out.println("Address 1: " + address1);
        System.out.println("Address 2: " + address2);

        double lat1 = ans1[0];
        double lon1 = ans1[1];
        double lat2 = ans2[0];
        double lon2 = ans2[1];

        System.out.println(distance(lat1, lat2,
                lon1, lon2) + " Miles");
    }
}