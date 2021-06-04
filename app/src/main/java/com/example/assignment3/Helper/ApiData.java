package com.example.assignment3.Helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

public class ApiData {

    @SuppressLint("StaticFieldLeak")
    static KProgressHUD hud;

    public static void progressDialogKH(Activity activity, boolean status) {
        try {
            if (activity != null && !activity.isFinishing()) {
                if (status) {
                    if (hud != null) {
                        if (!hud.isShowing()) {
                            hud = KProgressHUD.create(activity)
                                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                    .setCancellable(true)
                                    .setAnimationSpeed(3)
                                    .setDimAmount(0.5f)
                                    .show();
                        }
                    } else {
                        hud = KProgressHUD.create(activity)
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setCancellable(true)
                                .setAnimationSpeed(3)
                                .setDimAmount(0.5f)
                                .show();
                    }

                } else {
                    if (hud != null && hud.isShowing()) {
                        hud.dismiss();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void Toast(Activity c, String msg) {
        if (c != null) {
            Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
        }
    }
}


