package com.qzl.base_tools.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import java.io.File

/**
 * Created by caizhiming on 2016/11/18.
 */

object AppUtil {
    /**
     * 获取SD卡路径
     *
     * @return
     */
    private val sdPath: String?
        get() {
            val sdDir: File
            val sdCardExist = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
            return if (sdCardExist) {
                sdDir = Environment.getExternalStorageDirectory()
                sdDir.toString()
            } else {
                null
            }
        }
    val appDir: String?
        get() {
            var appDir = sdPath
            appDir += "/" + "xc"
            var file = File(appDir)
            if (!file.exists()) {
                file.mkdir()
            }
            appDir += "/" + "videocompress"
            file = File(appDir)
            if (!file.exists()) {
                file.mkdir()
            }
            return appDir
        }

    /**
     * 获取应用程序名称
     */
    @Synchronized
    fun getAppName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0
            )
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    @Synchronized
    fun getVersionName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0
            )
            return packageInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    @Synchronized
    fun getVersionCode(context: Context): Int {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0
            )
            return packageInfo.versionCode
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }


    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    @Synchronized
    fun getPackageName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0
            )
            return packageInfo.packageName
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    /**
     * 获取图标 bitmap
     * @param context
     */
    @Synchronized
    fun getBitmap(context: Context): Bitmap {
        var packageManager: PackageManager? = null
        var applicationInfo: ApplicationInfo? = null
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager?.getApplicationInfo(
                    context.packageName, 0
            )
        } catch (e: PackageManager.NameNotFoundException) {
            applicationInfo = null
        }

        val d =
                applicationInfo?.let { packageManager?.getApplicationIcon(it) } //xxx根据自己的情况获取drawable
        val bd = d as BitmapDrawable
        return bd.bitmap
    }

    //判断当前应用是否是debug状态
    @JvmStatic
    @Synchronized
    fun isApkInDebug(context: Context): Boolean {
        return context.applicationInfo != null &&
                context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }

    /**
     * 跳转至权限设置页面
     */
    fun getAppDetailSettingIntent(activity: Activity?, requestCode: Int) {
        val localIntent = Intent()
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            localIntent.data = Uri.fromParts(
                    "package",
                    activity?.packageName,
                    null
            )
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.action = Intent.ACTION_VIEW
            localIntent.setClassName(
                    "com.android.settings",
                    "com.android.settings.InstalledAppDetails"
            )
            localIntent.putExtra(
                    "com.android.settings.ApplicationPkgName",
                    activity?.packageName
            )
        }
        activity?.startActivityForResult(localIntent, requestCode)
    }
}
