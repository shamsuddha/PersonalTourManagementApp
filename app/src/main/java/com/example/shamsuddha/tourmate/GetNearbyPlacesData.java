package com.example.shamsuddha.tourmate;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String>
    {
        private String googlePlaceData, url;
        private GoogleMap mMap;

        @Override
        protected String doInBackground(Object... objects)
        {
            mMap = (GoogleMap) objects[0];
            url = (String) objects[1];

            DownloadUrl downloadUrl = new DownloadUrl();
            try
            {
                googlePlaceData = downloadUrl.ReadTheURL(url);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return googlePlaceData;
        }


        @Override
        protected void onPostExecute(String s)
        {
            List<HashMap<String, String>> nearByPlacesList = null;
            DataParser parser = new DataParser();
            nearByPlacesList = parser.parse(s);

            showNearbyPlaces(nearByPlacesList);


        }


        private void showNearbyPlaces(List<HashMap<String, String>> nearByPlacesList)
        {
            for (int i=0; i<nearByPlacesList.size(); i++)
            {
                MarkerOptions markerOptions = new MarkerOptions();

                HashMap<String, String> googlePlace = nearByPlacesList.get(i);

                String placeName = googlePlace.get("place_name");
                String vicinity = googlePlace.get("vicinity");
                double lat = Double.parseDouble(googlePlace.get("lat"));
                double lng = Double.parseDouble(googlePlace.get("lng"));


                LatLng latLng = new LatLng(lat, lng);
                markerOptions.position(latLng);
                markerOptions.title(placeName + " : " + vicinity);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                mMap.addMarker(markerOptions);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
            }
        }
    }



