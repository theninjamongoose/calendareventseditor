package com.example.tank.mygooogleeventeditor;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

/**
 * Created by tank on 6/30/16.
 */

public class EventLocationMapFragment extends Fragment implements OnMapReadyCallback {

    private View mView;
    private MapFragment mMapFragment;
    private GoogleMap map;
    private double mLat;
    private double mLng;
    private String mLocation;

    public static EventLocationMapFragment newInstance(String location) {
        Bundle bundle = new Bundle();
        bundle.putString("eventLocation", location);
        EventLocationMapFragment eventLocationMapFragment = new EventLocationMapFragment();
        eventLocationMapFragment.setArguments(bundle);
        return eventLocationMapFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.location_map_fragment, container, false);
        mLocation = getArguments().getString("eventLocation");
        return mView;
    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        FragmentManager fm = getFragmentManager();
//        mMapFragment = (MapFragment) fm.findFragmentById(R.id.map);
//        if (mMapFragment == null) {
//            mMapFragment = MapFragment.newInstance();
//            fm.beginTransaction().replace(R.id.map, mMapFragment).commit();
//        }
//        getMap();
//
//        map = mMapFragment.getMap();
//        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
//        mMapFragment.getMapAsync(thisis);
//        FragmentManager fm = getChildFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.map);
//        if (fragment == null) {
//            fragment = MapFragment.newInstance();
//            fm.beginTransaction().replace(R.id.my_events_fragment_holder, fragment).commit();
//        }


    //        mMapFragment = (MapFragment) getChildFragmentManager()
//                .findFragmentById(R.id.map);
//        mMapFragment.getMapAsync(this);
//    }
//
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        map = getMap();
        FragmentManager fm = getChildFragmentManager();
        mMapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mMapFragment == null) {
            mMapFragment = MapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mMapFragment).commit();
        }
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng markerPosition = getLocationFromAddress(getActivity(), mLocation);
        map.addMarker(new MarkerOptions().position(markerPosition));
        moveToCurrentLocation(markerPosition);
    }

    private void moveToCurrentLocation(LatLng currentLocation)
    {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        if(p1 == null){
            return getLocationFromAddress(context, "15220 NW Greenbrier Pkwy #380, Beaverton, OR 97006, USA");
        }

        return p1;
    }


//    private LatLng getLatLongFromAddress(String address)
//    {
//        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
//        try
//        {
//            List<Address> addresses = geoCoder.getFromLocationName(address , 1);
//            if (addresses.size() > 0)ivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//            {
//                return new LatLng(
//                        (int) (addresses.get(0).getLatitude()),
//                        (int) (addresses.get(0).getLongitude()));
//            }
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (map == null) {
//            map = mMapFragment.getMap();
//            map.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
        }
    }
}
