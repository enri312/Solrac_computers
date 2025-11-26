package com.solrac.computers.service;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Implementation of i18n service using ResourceBundle
 */
public class I18nServiceImpl implements II18nService {
    private static final String BUNDLE_BASE_NAME = "i18n.messages";
    private Locale currentLocale;
    private ResourceBundle bundle;
    
    public I18nServiceImpl() {
        this.currentLocale = new Locale("es"); // Default to Spanish
        loadBundle();
    }
    
    @Override
    public void setLocale(Locale locale) {
        this.currentLocale = locale;
        loadBundle();
    }
    
    @Override
    public String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return "???" + key + "???";
        }
    }
    
    @Override
    public Locale getCurrentLocale() {
        return currentLocale;
    }
    
    private void loadBundle() {
        this.bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, currentLocale);
    }
}
