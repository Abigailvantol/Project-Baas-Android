package com.example.projectbaas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.estimote.sdk.Utils.Proximity;

import android.content.Context;
import android.os.RemoteException;

public class Beacon {
	
	private BeaconManager 					_beaconManager;
	private Context							_context;
	private List<com.estimote.sdk.Beacon>	_beacons;
	private Proximity 						_proximity;
	
	private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
	private static final Region ALL_ESTIMOTE_BEACONS 	= new Region("rid", ESTIMOTE_PROXIMITY_UUID, null, null);
	
	public Beacon(Context context) {
		
		_context = context;
		_beacons = new ArrayList<com.estimote.sdk.Beacon>();
		
		MakeBeaconsConnection();
	}

	private void MakeBeaconsConnection() {
		
		// Communiceert met de beacons
	    _beaconManager = new BeaconManager(_context);
	    // Scannen voor 5s wachten voor 25s
	    _beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(1), 0);
	    
	    // Gescande beacons in een lijst
	    _beaconManager.setRangingListener(new BeaconManager.RangingListener() {
	    	// Verteld de listner welke beacons in range zijn 
	    	// De lijst van beacons is gesorteerd op accuracy
	    	@Override public void onBeaconsDiscovered(Region region, final List<com.estimote.sdk.Beacon> beacons) {
		    	  
	    		_beacons = beacons;
		    	 // for(com.estimote.sdk.Beacon beacon : beacons) {
		    	//	  	_proximity = Utils.computeProximity(beacon);
		    		  
		    	//	  	_beacon = beacon;	
		    	//  }
	    	}
	    });
	    
	    // verbinden met de Estimoteservice
	    _beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
		      @Override public void onServiceReady() {
		        try {
		          // begint met ranging, resultaten worden verzonden naar rangingListener
		          _beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
		        } catch (RemoteException e) {
		        	System.out.println("Can not start raging");
		        }
		      }
		    });
	}
	
	public List<com.estimote.sdk.Beacon> getBeacon() {
		
		return _beacons;
	}
	
	public Proximity getProximity() {
		
		return _proximity;
	}
}
	


