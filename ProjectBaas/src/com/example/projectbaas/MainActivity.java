package com.example.projectbaas;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.estimote.sdk.Utils.Proximity;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private BeaconManager 	beaconManager;
	
	private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
	private static final Region ALL_ESTIMOTE_BEACONS 	= new Region("rid", ESTIMOTE_PROXIMITY_UUID, null, null);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        // Communiceert met de beacons
	    beaconManager = new BeaconManager(this);
	    // Scannen voor 5s wachten voor 25s
	    beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(1), 0);
	    // Gescande beacons in een lijst
	    beaconManager.setRangingListener(new BeaconManager.RangingListener() {
	    	// Verteld de listner welke beacons in range zijn 
	    	// De lijst van beacons is gesorteerd op accuracy
	    	@Override public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
		    	  
		    	  for(Beacon beacon : beacons) {
		    		  Proximity proximity = Utils.computeProximity(beacon);

		    		  if (proximity == Utils.Proximity.IMMEDIATE) {
		    			  System.out.println("Beacon minor" + beacon.getMinor());
		    			  
		    			  if(beacon.getMinor() == 35734) { // groen 
		    				  
		    				  System.out.println("Groene beacon!!!");
		    	              
		    			  } else if(beacon.getMinor() == 32491) { // paars
		    				  
		    				  System.out.println("Paarse beacon!!!");
		    				  
		    			  } else if(beacon.getMinor() == 9876) { // blauw
		    				  
		    				  
		    			  } else { 
		    				  
		    			  }
		    		  }
		    	  }
		      }
		    });
	    // verbinden met de Estimoteservice
	    beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
		      @Override public void onServiceReady() {
		        try {
		          // begint met ranging, resultaten worden verzonden naar rangingListener
		          beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
		        } catch (RemoteException e) {
		        	System.out.println("Can not start raging");
		        }
		      }
		    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
