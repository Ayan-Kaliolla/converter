package kz.kaliolla.moneyconverter.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

public class DialogUtil {
    private static final AtomicInteger sCountStartDialog = new AtomicInteger(0);
    private static ProgressDialog sDialog;

    public static void showProgressDialog(final Activity activity, final String msg) {
        if (activity != null && !activity.isFinishing() && !activity.isRestricted()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!activity.isFinishing()) {
                        synchronized (sCountStartDialog) {
                            sCountStartDialog.incrementAndGet();
                            if (sDialog == null || sCountStartDialog.get() < 1) {
                                sDialog = new ProgressDialog(activity);
                                sDialog.setIndeterminate(true);
                                sDialog.setCancelable(false);
                                sDialog.setCanceledOnTouchOutside(false);
                                sDialog.setMessage(msg);
                                sDialog.show();
                            }
                        }
                    }
                }
            });
        }
    }

    public static void hideProgressDialog() {
        synchronized (sCountStartDialog) {
            if (sCountStartDialog.get() != 0) {
                sCountStartDialog.decrementAndGet();
            }
            if (sDialog != null && sDialog.isShowing() && sCountStartDialog.get() == 0) {
                try {
                    sDialog.dismiss();
                    sDialog = null;
                } catch (IllegalArgumentException e) {
                    Log.e("hideProgressDialog err", e.getMessage());
                } finally {
                    sDialog = null;
                }
            }
        }
    }
}
