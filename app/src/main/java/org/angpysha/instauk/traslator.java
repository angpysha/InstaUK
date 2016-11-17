package org.angpysha.instauk;

import android.content.res.XModuleResources;

import java.lang.reflect.Field;
import java.util.Locale;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by angpy on 14.02.2016.
 */
public class traslator implements IXposedHookZygoteInit,IXposedHookInitPackageResources,IXposedHookLoadPackage{
    private static String MODULE_PATH = null;
    public static final String APP = "com.instagram.android";

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam initPackageResourcesParam) throws Throwable {
        if (!initPackageResourcesParam.packageName.equals(APP))
            return;
        if (!Locale.getDefault().getDisplayLanguage().equals("uk"))
            return;
        XposedBridge.log("Instagram opened");
        Field[] fields = null;
        XModuleResources xModuleResources = XModuleResources.createInstance(MODULE_PATH,initPackageResourcesParam.res);
        try
        {
            fields = R.string.class.getFields();
            for (final Field field : fields)
            {
                String name = field.getName();
                try
                {
                    int id = field.getInt(R.string.class);
                    try {
                        initPackageResourcesParam.res.setReplacement(initPackageResourcesParam.packageName,"string",name,xModuleResources.getString(id));
                    } catch (RuntimeException e)
                    {
                        continue;

                    }
                } catch (Exception e)
                {
                    continue;

                }
            }
        } catch (RuntimeException ex)
        {


        }

        try
        {
            fields = R.plurals.class.getFields();
            for (final Field field : fields)
            {
                String name = field.getName();
                try
                {
                    int id = field.getInt(R.plurals.class);
                    try {
                        initPackageResourcesParam.res.setReplacement(initPackageResourcesParam.packageName,"plurals",name,xModuleResources.getString(id));
                    } catch (RuntimeException e)
                    {
                        continue;

                    }
                } catch (Exception e)
                {
                    continue;

                }
            }
        } catch (RuntimeException ex)
        {

        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedBridge.log(loadPackageParam.packageName + "Loaaaadddedd");
    }
}
