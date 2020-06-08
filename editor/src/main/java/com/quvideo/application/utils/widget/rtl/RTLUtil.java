package com.quvideo.application.utils.widget.rtl;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import com.quvideo.application.EditorApp;
import java.util.Locale;

import static android.content.pm.ApplicationInfo.FLAG_SUPPORTS_RTL;

/**
 * @author wangjieming
 * @date 2019-05-17.
 */
public class RTLUtil {

  public static boolean isRTL() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
      return false;
    }

    //Androidmanifest.xml中的supportsRtl
    Application ins = EditorApp.Companion.getInstance().getApp();
    ApplicationInfo applicationInfo = ins.getApplicationInfo();
    boolean hasRtlSupport = (applicationInfo.flags & FLAG_SUPPORTS_RTL) == FLAG_SUPPORTS_RTL;
    if (!hasRtlSupport) {
      return false;
    }
    return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
  }
}
