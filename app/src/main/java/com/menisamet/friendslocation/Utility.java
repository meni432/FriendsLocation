package com.menisamet.friendslocation;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by meni on 05/12/16.
 */

public class Utility {
    public static final String EXTRA_NEXT_ACTIVITY = "extra_next_activity";
    public static final String EXTRA_LOG_OUT = "extra_log_out";


    public static void checkAuthAndGoToActivity(Context context, Class nextActivity) {
//        Intent intent = new Intent(context, GoogleSignInActivity.class);
//        intent.putExtra(EXTRA_NEXT_ACTIVITY, nextActivity);
//        context.startActivity(intent);
        Intent intent = new Intent(context, nextActivity);
        context.startActivity(intent);

    }

    public static void logOutUser(Context context) {
        Intent intent = new Intent(context, GoogleSignInActivity.class);
        intent.putExtra(EXTRA_NEXT_ACTIVITY, MainActivity.class);
        intent.putExtra(EXTRA_LOG_OUT, true);
        context.startActivity(intent);
    }

    public static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
