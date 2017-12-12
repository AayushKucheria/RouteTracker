package com.example.android.routetracker;

import android.content.Context;
import java.util.ArrayList;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Aayush on 01/12/17.
 * This Class handles all the CSV file related work
 */

public class CSV extends MainActivity {

    Context context;
    String lat, lon, time;
    String fileName;
    ArrayList<String> allNames = new ArrayList<String>();

    //Creating the Main Folder of app
    static final File rootFolder = new File(Environment.getExternalStorageDirectory(), "Route Tracker");

    private static final String TAG = "CSV";

    CSV(){

    }

    //Constructor for transferring data from MainPage to CSV
    CSV(String latitude, String longitude, String currentTime, String name,  Context c) {
        lat = latitude;
        lon = longitude;
        time = currentTime;
        context = c;
        fileName = (name.toUpperCase())+".csv";
        Log.i(TAG, "Parametrized Constructor executed");

        if(!rootFolder.exists() && !rootFolder.isDirectory())
            rootFolder.mkdirs();
    }


    public void makeCSV() throws IOException {

        Log.i(TAG, "makeCSV method called");
        if (isExternalStorageWritable()) {
            File f = new File(rootFolder, fileName);
            try {
                if (!f.exists())
                    f.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Writing/editing on the CSV file
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));
            pw.append(lat + "," + lon + "," + time + "\n");
            pw.close();
            Log.i(TAG, "CSV Updated");

            Toast.makeText(context, "File Updated", Toast.LENGTH_SHORT).show();
        }

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else
            return false;
    }

    //Returns list of existing CSV files when called for by the "Existing Route" button
    public ArrayList<String> getListOfNames() {
        File fileList[] = rootFolder.listFiles();

        for(int i=0; i<fileList.length; i++) {
            String tempName = fileList[i].getName();

            //Removing the ".csv" ending from the filenames
            tempName = tempName.substring(0, tempName.length()-4);

            allNames.add(tempName);
        }

        //Sorting Alphabetically
        Collections.sort(allNames);

        return allNames;
    }
}




