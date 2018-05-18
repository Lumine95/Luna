package com.mm.luna.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by ZMM on 18/5/18 16:33.
 */

public class SBUtil {

    public static void showLong(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }
}
