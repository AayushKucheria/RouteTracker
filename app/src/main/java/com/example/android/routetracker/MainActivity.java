package com.example.android.routetracker;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //The current Route name
    static String routeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();
        //The "New Route" button on main page
        Button new_route = (Button) findViewById(R.id.new_route_button);

        //The "Existing Route" button on main page
        Button existing_route = (Button) findViewById(R.id.existing_route_button);

        //Click listener on New Route Button which calls newRouteDialog
        new_route.setOnClickListener(new View.OnClickListener() {
            //This code will execute when the "New Route" button is clicked
            @Override
            public void onClick(View view) {
                newRouteDialog();
            }
        });

        //Click listener on Existing Route Button which calls existingRouteDialog
        existing_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                existingRouteDialog();
            }
        });

    }

    //Called when "New Route" button is clicked
    public void newRouteDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set a new Route");

        //Setting up the EditText view on which RouteName can be entered
        final EditText input = new EditText(this);
        builder.setView(input);

        //Setting up the buttons -

        //Positive Button
        builder.setPositiveButton(R.string.ok_button_text, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                routeName = input.getText().toString();

                //Calling the MainPage when ok button is pressed
                Intent newRoute = new Intent(MainActivity.this, MainPage.class);
                startActivity(newRoute);
            }
        });

        //Negative Button
        builder.setNegativeButton(R.string.cancel_button_text, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    /**
     * Called when user selects "Existing Route" button
     * <p>
     * Creates an object of CSV and calls getDirectory to get the Folder
     * Creates an array of all filenames and prints it.
     */
    public void existingRouteDialog() {

        CSV csv = new CSV();

        ArrayList<String> fileNames = csv.getListOfNames();
        String j[] = fileNames.toArray(new String[fileNames.size()]);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Choose your Route")
                .setSingleChoiceItems(j, 0, null);

        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                ListView lw = ((AlertDialog) dialog).getListView();
                Object selectedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                routeName = selectedItem.toString();
                Intent newRoute = new Intent(MainActivity.this, MainPage.class);
                startActivity(newRoute);
            }
        });

        //Negative Button
        builder.setNegativeButton(R.string.cancel_button_text, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    //ASKING FOR PERMISSIONS


    private static final int REQUEST_INSTALL_PACKAGES = 110, ACCESS_FINE_LOCATION = 111, WRITE_EXTERNAL_STORAGE = 112;
    final String[] permission_list = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    //Method called in onCreate, so it checks for permissions every time the app is opened
    private void checkPermissions() {

        boolean hasPermissionInstallPackage = (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.REQUEST_INSTALL_PACKAGES) == PackageManager.PERMISSION_GRANTED);

        if (!hasPermissionInstallPackage) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, REQUEST_INSTALL_PACKAGES);
        }

        boolean hasPermissionExternal = (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);

        if (!hasPermissionExternal) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
        }

        boolean hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

        if (!hasPermissionLocation) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
        }


    }

    //Called when user Denies/Accepts permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int grantResults[]) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_INSTALL_PACKAGES: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
            }

            //Action when user accepts/denies External Storage Permission Request
            case WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    finish();
                    startActivity(getIntent());
                } else finish();
            }

            //Action when user accepts/denies Location Permission Request
            case ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
            }
        }
    }
}