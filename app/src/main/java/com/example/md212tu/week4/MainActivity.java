package com.example.md212tu.week4;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    // ArrayList
    ArrayList<SelectContact> selectUsers;
    List<SelectContact> temp;
    // Contact List
    ListView listView;
    // Cursor to load contacts list
    Cursor phones, email;

    // Pop up
    ContentResolver resolver;
    SearchView search;
    SelectContactAdapter adapter;


    public void clickedOnAvea(View view) {
        listView = (ListView) findViewById(R.id.contacts_list);
        selectUsers = new ArrayList<>();
        fetchContentsForOperator("(501)");
        fetchContentsForOperator("(505)");
        fetchContentsForOperator("(506)");
        fetchContentsForOperator("(507)");
        fetchContentsForOperator("(551)");
        fetchContentsForOperator("(552)");
        fetchContentsForOperator("(553)");
        fetchContentsForOperator("(554)");
        fetchContentsForOperator("(555)");
        fetchContentsForOperator("(559)");



    }

    public void clickedOnTurkcell(View view) {
        listView = (ListView) findViewById(R.id.contacts_list);
        selectUsers = new ArrayList<>();
        fetchContentsForOperator("(530)");
        fetchContentsForOperator("(531)");
        fetchContentsForOperator("(532)");
        fetchContentsForOperator("(533)");
        fetchContentsForOperator("(534)");
        fetchContentsForOperator("(535)");
        fetchContentsForOperator("(536)");
        fetchContentsForOperator("(537)");
        fetchContentsForOperator("(538)");
        fetchContentsForOperator("(539)");


    }

    public void clickedOnVodafone(View view) {
        listView = (ListView) findViewById(R.id.contacts_list);
        selectUsers = new ArrayList<>();
        fetchContentsForOperator("(540)");
        fetchContentsForOperator("(541)");
        fetchContentsForOperator("(542)");
        fetchContentsForOperator("(543)");
        fetchContentsForOperator("(544)");
        fetchContentsForOperator("(545)");
        fetchContentsForOperator("(546)");
        fetchContentsForOperator("(547)");
        fetchContentsForOperator("(548)");
        fetchContentsForOperator("(549)");

    }

    public void clickedOnAll(View view) {
        listView = (ListView) findViewById(R.id.contacts_list);
        fetchContacts();
    }

    public void backUpClicked(View view) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();

        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            SelectContact item = (SelectContact) listView.getAdapter().getItem(i);
            edit.putString(Integer.toString(i), item.getThumb() + "," + item.getName() + "," + item.getPhone());
        }
        edit.commit();
    }

    public void recoveryClicked(View view) {
        SharedPreferences myPref = PreferenceManager.getDefaultSharedPreferences(this);
        String item = myPref.getString(Integer.toString(0), "empty");
        int i = 0;
        do {
            String[] result = item.split(",");
            SelectContact listItem = new SelectContact();
            listItem.thumb = BitmapFactory.decodeByteArray(result[0].getBytes(), 0, result[0].getBytes().length);
            listItem.name = result[1];
            listItem.phone = result[2];
            selectUsers.add(listItem);
            i++;
            item = myPref.getString(Integer.toString(i), "empty");
        } while (!item.equals("empty"));
    }

    public void fetchContentsForOperator(String argOperator) {
        if (phones != null) {

            Log.e("count", "" + phones.getCount());
            if (phones.getCount() == 0) {
                Toast.makeText(MainActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
            }
            phones.moveToFirst();

            Bitmap bit_thumb = null;
            String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (phoneNumber.startsWith(argOperator)) {
                String EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                try {
                    if (image_thumb != null) {
                        bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                    } else {
                        Log.e("No Image Thumb", "--------------");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                SelectContact selectUser = new SelectContact();
                selectUser.setThumb(bit_thumb);
                selectUser.setName(name);
                selectUser.setPhone(phoneNumber);
                selectUser.setEmail(id);
                selectUsers.add(selectUser);
            }

            while (phones.moveToNext()) {
                bit_thumb = null;
                id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (phoneNumber.startsWith(argOperator)) {
                    String EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                    String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    try {
                        if (image_thumb != null) {
                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                        } else {
                            Log.e("No Image Thumb", "--------------");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SelectContact selectUser = new SelectContact();
                    selectUser.setThumb(bit_thumb);
                    selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    selectUser.setEmail(id);
                    selectUsers.add(selectUser);
                }

            }
        } else {
            Log.e("Cursor close 1", "----------------");
        }
        adapter = new SelectContactAdapter(selectUsers, MainActivity.this);
        listView.setAdapter(adapter);

    }

    public void fetchContacts() {
        selectUsers = new ArrayList<>();
        if (phones != null) {
            phones.moveToFirst();
            Bitmap bit_thumb = null;
            String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
            String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
            try {
                if (image_thumb != null) {
                    bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                } else {
                    Log.e("No Image Thumb", "--------------");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            SelectContact selectUser = new SelectContact();
            selectUser.setThumb(bit_thumb);
            selectUser.setName(name);
            selectUser.setPhone(phoneNumber);
            selectUser.setEmail(id);
            selectUsers.add(selectUser);
            Log.e("count", "" + phones.getCount());
            if (phones.getCount() == 0) {
                Toast.makeText(MainActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
            }

            while (phones.moveToNext()) {
                bit_thumb = null;
                id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                try {
                    if (image_thumb != null) {
                        bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                    } else {
                        Log.e("No Image Thumb", "--------------");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                selectUser = new SelectContact();
                selectUser.setThumb(bit_thumb);
                selectUser.setName(name);
                selectUser.setPhone(phoneNumber);
                selectUser.setEmail(id);
                selectUsers.add(selectUser);
            }
        } else {
            Log.e("Cursor close 1", "----------------");
        }
        //phones.close();
        adapter = new SelectContactAdapter(selectUsers, MainActivity.this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectUsers = new ArrayList<SelectContact>();
        resolver = this.getContentResolver();
        listView = (ListView) findViewById(R.id.contacts_list);
        getPermissionToReadUserContacts();
        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        LoadContact loadContact = new LoadContact();
        loadContact.execute();
    }

    // Identifier for the permission request
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

    // Called when the user is performing an action which requires the app to read the
    // user's contacts
    public void getPermissionToReadUserContacts() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_CONTACTS)) {
                // Show our own UI to explain to the user why we need to read the contacts
                // before actually requesting the permission and showing the default UI
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    READ_CONTACTS_PERMISSIONS_REQUEST);
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // Load data on background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                    String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    try {
                        if (image_thumb != null) {
                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                        } else {
                            Log.e("No Image Thumb", "--------------");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SelectContact selectUser = new SelectContact();
                    selectUser.setThumb(bit_thumb);
                    selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    selectUser.setEmail(id);
                    selectUsers.add(selectUser);
                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }
            //phones.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new SelectContactAdapter(selectUsers, MainActivity.this);
            listView.setAdapter(adapter);

            // Select item on listclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.e("search", "here---------------- listener");

                    SelectContact data = selectUsers.get(i);

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    String phoneNumber = "+90" + data.getPhone().replace("(","").replace(")", "");
                    callIntent.setData(Uri.parse("tel:" + phoneNumber));
                    if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);
                }
            });

            listView.setFastScrollEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        phones.close();
    }
}
