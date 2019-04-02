package com.example.asus.workercrowdsource;

import android.content.res.Configuration;
import android.content.res.Resources;


import java.util.Locale;

import static android.util.LayoutDirection.LOCALE;

public class LanguageHelper {
    public static void changeLocale(Resources res, Locale locale) {
        Configuration config;
        config = new Configuration(res.getConfiguration());
        String value = locale.toString();
        switch (value){
            case "hi":

                break;
            case "mr":
                config.setLocale(locale);
        }
    }
}