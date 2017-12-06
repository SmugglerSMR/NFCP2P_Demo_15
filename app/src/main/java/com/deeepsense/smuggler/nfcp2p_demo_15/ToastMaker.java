package com.deeepsense.smuggler.nfcp2p_demo_15;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Smuggler on 6/12/2017.
 */

class ToastMaker {
    /**
     * Displays a Toast notification for a short duration.
     *
     * @param context
     * @param resId
     */
    public static void toast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a Toast notification for a short duration.
     *
     * @param context
     * @param resId
     */
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
