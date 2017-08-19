package io.uscool.inboxreader;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FragmentManager fm = getSupportFragmentManager();
    LinearLayout mLinearLayout;
    String[] permission = new String[]{Manifest.permission.READ_SMS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(hasPermissionToGrant()) {
            checkForPermission();
        }



        Button btn = (Button) findViewById(R.id.btn_request_sms);
        mLinearLayout = (LinearLayout) findViewById(R.id.layout_sms);
        final EditText editText = (EditText) findViewById(R.id.input_message);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String keyword = editText.getText().toString();
                if(!TextUtils.isEmpty(keyword)) {

                    attachActivity(editText.getText().toString());
                    editText.setText("");
                }
//                else if(hasPermissionToGrant()) {
//                    checkForPermission();
//                }
                else {
                    editText.setError("Please enter a keyword");
                }

            }
        });
    }

    private void attachActivity(String keyword) {
            Intent intent = ReadSmsActivity.getStartIntent(this, keyword);
            startActivity(intent);

    }



    @Override
    public void onBackPressed() {
        if(fm.getBackStackEntryCount()>0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
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
