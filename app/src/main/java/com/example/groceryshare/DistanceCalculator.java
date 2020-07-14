package com.example.groceryshare;
// Java program to calculate Distance Between Two Points on Earth

import android.os.StrictMode;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class DistanceCalculator /*extends AsyncTask<Void, Void, Void>*/ {

//    private static String url;
    String address1, address2;
    public DistanceCalculator(String address1, String address2) {
        this.address1 = address1;
        this.address2 = address2;
    }

    public String getAddress1() {
        return address1;
    }
    public String getAddress2() {
        return address2;
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

    public static double distance(double lat1, double lat2, double lon1, double lon2)
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        double[] ans = new double[2];

        String query = "";
        String[] split = address.split(" ");

        for (int i = 0; i < split.length; i++) {
            query += split[i];
            if (i < (split.length - 1)) {
                query += "+";
            }
        }

//        url = "https://nominatim.openstreetmap.org/search?q=" + query + "&format=json&addressdetails=1";
//        String json  =  new DistanceCalculator(address).onPostExecute();
        String json = readJsonFromUrl("https://nominatim.openstreetmap.org/search?q=" + query + "&format=json&addressdetails=1");
        if(json.equals("[]")){
            return null;
        }
        int firstIndexLat = json.indexOf("\"lat\":");
        int firstIndexLon = json.indexOf("\"lon\":");
        int firstIndexEnd = json.indexOf("\",\"display_name\"");

        double lat = Double.parseDouble(json.substring(firstIndexLat + 7, firstIndexLon -2));
        double lon = Double.parseDouble(json.substring(firstIndexLon + 7, firstIndexEnd));

        ans[0] = lat;
        ans[1] = lon;

        return ans;
    }


//    String jsonText = "";
//    @Override
//    protected Void doInBackground(Void... voids) {
//        InputStream is = null;
//        try {
//            is = new URL(url).openStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//            jsonText = readAll(rd);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//    protected String onPostExecute() {
//        String output = jsonText;
//        return output;
//    }

    // driver code
    public String main(String[] args) throws IOException, JSONException {

        String address1 = getAddress1();
        String address2 = getAddress2();

        double[] ans1 = addressToLonLat(address1);
        double[] ans2 =  addressToLonLat(address2);

        System.out.println("Address 1: " + address1);
        System.out.println("Address 2: " + address2);

        double lat1 = ans1[0];
        double lon1 = ans1[1];
        double lat2 = ans2[0];
        double lon2 = ans2[1];

        String result = distance(lat1, lat2,
                lon1, lon2) + " Miles";
        System.out.println(result);

        return result;
    }
}
