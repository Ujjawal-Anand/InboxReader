package io.uscool.inboxreader;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ujjawal on 7/8/17.
 */

public class PermissionUtil {
    private static final int PERMISSIONS_REQUEST_CODE = 15621;
    public static boolean hasPermissionsToGrant(String[] permissions, Activity activity) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (isNotNullOrEmpty(permission)) {
                    if (ContextCompat.checkSelfPermission(activity.getApplicationContext().getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean isNotNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    public static void askForPermissions(String[] neededPermissions,  Activity activity) {
        ArrayList<String> notGrantedPermissions = new ArrayList<>();

        if (neededPermissions != null) {
            for (String permission : neededPermissions) {
                if (isNotNullOrEmpty(permission)) {
                    if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                            permission) != PackageManager.PERMISSION_GRANTED) {
                        notGrantedPermissions.add(permission);
                    }
                }
            }
        }
      /*  if (possiblePermissions != null && hasPermissionsToGrant(possiblePermissions, activity.getApplicationContext())) {
            for (String permission : possiblePermissions) {
                if (isNotNullOrEmpty(permission)) {
                    if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                        notGrantedPermissions.add(permission);
                    }
                }
            }
        }*/

        String[] permissionsToGrant = removeEmptyAndNullStrings(notGrantedPermissions);
        ActivityCompat.requestPermissions(activity, permissionsToGrant, PERMISSIONS_REQUEST_CODE);
    }

    private static String[] removeEmptyAndNullStrings(final ArrayList<String> permissions) {
        List<String> list = new ArrayList<>(permissions);
        list.removeAll(Collections.singleton(null));
        return list.toArray(new String[list.size()]);
    }

}

