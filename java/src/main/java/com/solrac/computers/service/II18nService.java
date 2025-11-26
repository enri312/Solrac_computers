package com.solrac.computers.service;

import java.util.Locale;

/**
 * Service interface for internationalization (i18n)
 */
public interface II18nService {
    void setLocale(Locale locale);
    String getString(String key);
    Locale getCurrentLocale();
}
