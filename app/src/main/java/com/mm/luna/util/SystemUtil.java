package com.mm.luna.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mm.luna.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by ZMM on 2018/8/15 10:16.
 */
public class SystemUtil {
    /**
     * 使用系统发送分享数据
     *
     * @param context
     * @param text
     */
    public static void share(Context context, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, "分享到"));
    }

    /**
     * 使用浏览器打开
     *
     * @param context
     * @param uriStr
     */
    public static void openWithBrowser(Context context, String uriStr) {
        Uri uri = Uri.parse(uriStr);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 复制文字到剪切板
     *
     * @param context
     * @param copyText
     */
    public static void copyText(Context context, String copyText) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newRawUri(
                "LunaUrl", Uri.parse(copyText));
        if (cmb != null) {
            cmb.setPrimaryClip(data);
            Toasty.info(context, context.getString(R.string.copy_success)).show();
        }
    }
}
