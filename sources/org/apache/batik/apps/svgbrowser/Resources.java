/*****************************************************************************
 * Copyright (C) The Apache Software Foundation. All rights reserved.        *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the Apache Software License *
 * version 1.1, a copy of which has been included with this distribution in  *
 * the LICENSE file.                                                         *
 *****************************************************************************/

package org.apache.batik.apps.svgbrowser;

import java.util.Locale;
import java.util.MissingResourceException;
import org.apache.batik.i18n.Localizable;
import org.apache.batik.i18n.LocalizableSupport;

/**
 * This class manages the message for the Swing extensions.
 *
 * @author <a href="mailto:vhardy@apache.org">Vincent Hardy</a>
 * @version $Id$
 */
public class Resources {

    /**
     * This class does not need to be instantiated.
     */
    protected Resources() { }

    /**
     * The error messages bundle class name.
     */
    protected final static String RESOURCES =
        "org.apache.batik.apps.svgbrowser.resources.GUI";

    /**
     * The localizable support for the error messages.
     */
    protected static LocalizableSupport localizableSupport =
        new LocalizableSupport(RESOURCES);

    /**
     * Implements {@link org.apache.batik.i18n.Localizable#setLocale(Locale)}.
     */
    public static void setLocale(Locale l) {
        localizableSupport.setLocale(l);
    }

    /**
     * Implements {@link org.apache.batik.i18n.Localizable#getLocale()}.
     */
    public static Locale getLocale() {
        return localizableSupport.getLocale();
    }

    /**
     * Implements {@link
     * org.apache.batik.i18n.Localizable#formatMessage(String,Object[])}.
     */
    public static String formatMessage(String key, Object[] args)
        throws MissingResourceException {
        return localizableSupport.formatMessage(key, args);
    }

    public static String getString(String key)
        throws MissingResourceException {
        return formatMessage(key, null);
    }

    public static int getInteger(String key) 
        throws MissingResourceException {
        return localizableSupport.getResourceManager().getInteger(key);
    }

    public static int getCharacter(String key)
        throws MissingResourceException {
        return localizableSupport.getResourceManager().getCharacter(key);
    }
}