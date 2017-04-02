package com.example.lastone;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.net.wifi.p2p.nsd.WifiP2pServiceRequest;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.os.Looper.getMainLooper;

/**
 * Created by rinnaris on 2017-03-26.
 */

public class ServiceHandler {
    //**************************************************
    //context from the calling activity
    private Context context;
    //a manager object
    private WifiP2pManager mManager;
    //a channel object gotton from the mmanager
    private WifiP2pManager.Channel mChannel;
    //hashmap containing all nearby phone services
    private HashMap<String, String> buddies = new HashMap<String, String>();
    //used for requesting services from other phones
    private WifiP2pServiceRequest serviceRequest;
    //this is a thing that i don't know, it's from the discoverservice()
    private WifiP2pManager.ActionListener actionListener = new WifiP2pManager.ActionListener() {

        @Override
        public void onSuccess() {
            // There isn't much point to this since it's asynchronous
        }

        @Override
        public void onFailure(int code) {
            // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
            if (code == WifiP2pManager.P2P_UNSUPPORTED) {
                toast("P2P not supported, sorry.");
            }
            else if (code == WifiP2pManager.BUSY){
            }
            else if(code == WifiP2pManager.ERROR){
            }
            else{
                toast("Oops. Something went wrong try rebooting your device.");
            }

        }};
    //string representing user profile(placeholder in use for now)
    private String serviceName;
    //this is just an arbitrary int over 10000
    final int SERVER_PORT = 20000;
    //the user's profile
    private UserProfile mainProfile;
    //delay between searches using searchloop
    final int delay = 1000;
    //list of know nearby users so we don't alert to the same user twice
    private ArrayList<UserProfile> nearby;
    //this one is internal use only don't worry about it
    private ArrayList<UserProfile> nearby2;
    //**************************************************

    //constructor with profile info
    public ServiceHandler(Context contextIn, WifiP2pManager mManagerIn, UserProfile profileIn){
        nearby = new ArrayList<>();
        nearby2 = new ArrayList<>();
        context = contextIn;
        mManager = mManagerIn;
        mChannel = mManager.initialize(context, getMainLooper(), null);
        mainProfile = profileIn;
        serviceName = "SharedInterest" + mainProfile.toString();
        registerService();
    }

    public ServiceHandler(Context contextIn, WifiP2pManager systemService) {
        nearby = new ArrayList<>();
        nearby2 = new ArrayList<>();
        context = contextIn;
        mManager = systemService;
        mChannel = mManager.initialize(context, getMainLooper(), null);
        mainProfile = getMainProfileFromStorage();
        serviceName = "SharedInterest" + mainProfile.toString();
        registerService();
    }

    //call this from the mainactivity's onresume
    public void registerService() {
        //Creates a string map with information about service including serviceName
        Map record = new HashMap();
        record.put("listenport", String.valueOf(SERVER_PORT));
        record.put("buddyname", serviceName);
        record.put("available", "visible");

        //yeah I don't know what this does but it was in the dev page so it probably needs to be here
        WifiP2pDnsSdServiceInfo serviceInfo = WifiP2pDnsSdServiceInfo.newInstance("_test", "_presence._tcp", record);
        mManager.clearLocalServices(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int reason) {
            }
        });
        mManager.addLocalService(mChannel, serviceInfo, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Command successful! Code isn't necessarily needed here,
                // Unless you want to update the UI or add logging statements.
            }

            @Override
            public void onFailure(int arg0) {
                // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
            }
        });
    }

    //this has to be called in creating activity's onDestroy();
    public void unregisterService(){
        mManager.clearLocalServices(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Command successful! Code isn't necessarily needed here,
                // Unless you want to update the UI or add logging statements.
            }

            @Override
            public void onFailure(int arg0) {
                // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
            }
        });
    }

    //call to look for other instances of the app
    public void discoverService() {

        //I think this creates a listener for when services are found
        //probably
        WifiP2pManager.DnsSdTxtRecordListener txtListener = new WifiP2pManager.DnsSdTxtRecordListener() {

            //put stuff to parse service names in this method
            @Override
            public void onDnsSdTxtRecordAvailable(String fullDomainName, Map<String, String> txtRecordMap, WifiP2pDevice srcDevice) {
                buddies.clear();
                buddies.put(srcDevice.deviceAddress, (String) txtRecordMap.get("buddyname"));
                processServiceName(txtRecordMap.get("buddyname"));
                System.out.println(txtRecordMap.get("buddyname"));
            }
        };

        //I honestly don't know what this does but we need it
        WifiP2pManager.DnsSdServiceResponseListener servListener = new WifiP2pManager.DnsSdServiceResponseListener() {
            @Override
            public void onDnsSdServiceAvailable(String instanceName, String registrationType,
                                                WifiP2pDevice resourceType) {

                // Update the device name with the human-friendly version from
                // the DnsTxtRecord, assuming one arrived.
                resourceType.deviceName = buddies
                        .containsKey(resourceType.deviceAddress) ? buddies
                        .get(resourceType.deviceAddress) : resourceType.deviceName;
            }
        };

        //update nearby to contain only nearby phones
        nearby = nearby2;
        nearby2 = new ArrayList<>();
        //I dunno what most of this does
        mManager.setDnsSdResponseListeners(mChannel, servListener, txtListener);
        serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        //I think this will fix the 12 limit issue but i'm not sure, if something breaks
        //just delete the following line of code
        mManager.clearServiceRequests(mChannel, actionListener);
        mManager.addServiceRequest(mChannel, serviceRequest, actionListener);
        mManager.discoverServices(mChannel, actionListener);
    }

    //checks service to see if it is for this app and checks for matches if it is
    private void processServiceName(String otherService){

        //if other service is from our app
        if(otherService.contains("SharedInterest")){
            //remove our app's flag from beginning
            String out = otherService;
            out = out.replace("SharedInterest", "");

            //convert out into a user profile
            UserProfile other = new UserProfile(out);

            //if user is in nearby list
            boolean known = false;
            for (UserProfile up: nearby) {
                if(up.isSame(other)){
                    known = true;
                }
            }
            //if phone is not in nearby do processing
            if(!known){
                //get common interests between two profiles
                ArrayList<String> shared = mainProfile.compare(other);
                System.out.println(shared);

                //if they have at least one thing in common
                if(shared.size()>=1){
                    String toToast = "Someone nearby shares " + shared.size() + " interests with you.";
                    toast(toToast);
                }
            }

            //add to nearby2 regardless of result
            nearby2.add(other);
        }
    }

    //creates a toast message with the string passed to it
    public void toast(String s) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }

    public void toastLong(String s) {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }

    //saves main user's profile to internal storage, call after userprofile has been created or updated
    //may be good practice to call in onDestroy() to be safe
    public void saveMainProfileToStorage(){
        //might be a good idea to use final string but I'm hardcoding it for now
        String filename = "MainProfile";
        //we're saving a string instead of the actual profile since re-creating it from a string is relatively painless
        String string = mainProfile.toString();

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //reads internal storage and returns a newly created UserProfile from the stored data
    public UserProfile getMainProfileFromStorage(){
        //this came from stackoverflow as usual
        //i don't really know what's going on here
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = context.openFileInput ("MainProfile") ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        mainProfile = new UserProfile(datax.toString());
        if(mainProfile.getName().equals("")){
            return null;
        }
        else{
        }
        return mainProfile;
    }

    public void setMainProfile(UserProfile mainProfile) {
        this.mainProfile = mainProfile;
    }
}
