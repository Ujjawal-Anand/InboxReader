package io.uscool.inboxreader;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import java.util.List;

/**
 * Created by ujjawal on 6/8/17.
 *
 */

public class ReadSmsActivity extends AppCompatActivity {

    private static final String KEYWORD = "keyword";
    String[] permission = new String[]{Manifest.permission.READ_SMS};
    String keyword;


    public static Intent getStartIntent(Context context, String keyword) {
        Intent starter = new Intent(context, ReadSmsActivity.class);
        starter.putExtra(KEYWORD, keyword);
        return starter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recycle_view);
        keyword= getIntent().getStringExtra(KEYWORD).toLowerCase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }


   public List<Sms> getAllSms() {
       List<Sms> lstSms = new ArrayList<Sms>();
       Sms objSms = new Sms();
       Uri message = Uri.parse("content://sms/");
       ContentResolver cr = getContentResolver();

       Cursor c = cr.query(message, null, null, null, null);
       startManagingCursor(c);
       int totalSMS = c.getCount();

       if (c.moveToFirst()) {
           for (int i = 0; i < totalSMS; i++) {

               objSms = new Sms();

               objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
               objSms.setAddress(c.getString(c
                       .getColumnIndexOrThrow("address")));
               String msg = c.getString(c.getColumnIndexOrThrow("body"));
               objSms.setMsg(msg);
               objSms.setReadState(c.getString(c.getColumnIndex("read")));
               objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
               if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                   objSms.setFolderName("inbox");
               } else {
                   objSms.setFolderName("sent");
               }
               if(msg.toLowerCase().contains(keyword.toLowerCase())) {
                   lstSms.add(objSms);
               }
               c.moveToNext();
           }
       }
       // else {
       // throw new RuntimeException("You have no SMS");
       // }
       c.close();

       return lstSms;
   }

    private void setRecyclerView() {
        if(!hasPermissionToGrant()) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            SmsAdapter adapter = new SmsAdapter(this, getAllSms());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        }
        else {
            checkForPermission();
        }
    }

    private boolean hasPermissionToGrant() {
        return PermissionUtil.hasPermissionsToGrant(permission, this);
    }

    private void checkForPermission() {
        boolean hasPermissionToGrant = PermissionUtil.hasPermissionsToGrant(permission, this);
        if(hasPermissionToGrant) {
            PermissionUtil.askForPermissions(permission, this);
        }

//        if(hasPermissionToGrant) {
//            Toast.makeText(this, "Please Grant the required permissions", Toast.LENGTH_LONG).show();
//        }
    }
}