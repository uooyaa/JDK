/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package jdk.xml.internal;

import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
import com.sun.org.apache.xerces.internal.util.ParserConfigurationSettings;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
import javax.xml.XMLConstants;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogFeatures.Feature;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/**
 * Constants for use across JAXP processors.
 */
public class JdkXmlUtils {

    /**
     * Catalog features
     */
    public final static String USE_CATALOG = XMLConstants.USE_CATALOG;
    public final static String SP_USE_CATALOG = "javax.xml.useCatalog";
    public final static String CATALOG_FILES = CatalogFeatures.Feature.FILES.getPropertyName();
    public final static String CATALOG_DEFER = CatalogFeatures.Feature.DEFER.getPropertyName();
    public final static String CATALOG_PREFER = CatalogFeatures.Feature.PREFER.getPropertyName();
    public final static String CATALOG_RESOLVE = CatalogFeatures.Feature.RESOLVE.getPropertyName();

    /**
     * Reset SymbolTable feature
     * System property name is identical to feature name
     */
    public final static String RESET_SYMBOL_TABLE = "jdk.xml.resetSymbolTable";

    /**
     * Values for a feature
     */
    public static final String FEATURE_TRUE = "true";
    public static final String FEATURE_FALSE = "false";

    /**
     * Default value of USE_CATALOG. This will read the System property
     */
    public static final boolean USE_CATALOG_DEFAULT
            = SecuritySupport.getJAXPSystemProperty(Boolean.class, SP_USE_CATALOG, "true");

    /**
     * Default value of RESET_SYMBOL_TABLE. This will read the System property
     */
    public static final boolean RESET_SYMBOL_TABLE_DEFAULT
            = SecuritySupport.getJAXPSystemProperty(Boolean.class, RESET_SYMBOL_TABLE, "false");


    /**
     * JDK features (will be consolidated in the next major feature revamp
     */
    public final static String CDATA_CHUNK_SIZE = "jdk.xml.cdataChunkSize";
    public static final int CDATA_CHUNK_SIZE_DEFAULT
            = SecuritySupport.getJAXPSystemProperty(Integer.class, CDATA_CHUNK_SIZE, "0");

    /**
     * Returns the value.
     *
     * @param value the specified value
     * @param defValue the default value
     * @return the value, or the default value if the value is null
     */
    public static int getValue(Object value, int defValue) {
        if (value == null) {
            return defValue;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            return Integer.parseInt(String.valueOf(value));
        } else {
            throw new IllegalArgumentException("Unexpected class: "
                    + value.getClass());
        }
    }

    /**
     * Sets the XMLReader instance with the specified property if the the
     * property is supported, ignores error if not, issues a warning if so
     * requested.
     *
     * @param reader an XMLReader instance
     * @param property the name of the property
     * @param value the value of the property
     * @param warn a flag indicating whether a warning should be issued
     */
    public static void setXMLReaderPropertyIfSupport(XMLReader reader, String property,
            Object value, boolean warn) {
        try {
            reader.setProperty(property, value);
        } catch (SAXNotRecognizedException | SAXNotSupportedException e) {
            if (warn) {
                XMLSecurityManager.printWarning(reader.getClass().getName(),
                        property, e);
            }
        }
    }

    /**
     * Returns the value of a Catalog feature by the property name.
     *
     * @param features a CatalogFeatures instance
     * @param name the name of a Catalog feature
     * @return the value of a Catalog feature, null if the name does not match
     * any feature supported by the Catalog.
     */
    public static String getCatalogFeature(CatalogFeatures features, String name) {
        for (Feature feature : Feature.values()) {
            if (feature.getPropertyName().equals(name)) {
                return features.get(feature);
            }
        }
        return null;
    }

    /**
     * Creates an instance of a CatalogFeatures.
     *
     * @param defer the defer property defined in CatalogFeatures
     * @param file the file path to a catalog
     * @param prefer the prefer property defined in CatalogFeatures
     * @param resolve the resolve property defined in CatalogFeatures
     * @return a {@link javax.xml.transform.Source} object
     */
    public static CatalogFeatures getCatalogFeatures(String defer, String file,
            String prefer, String resolve) {

        CatalogFeatures.Builder builder = CatalogFeatures.builder();
        if (file != null) {
            builder = builder.with(CatalogFeatures.Feature.FILES, file);
        }
        if (prefer != null) {
            builder = builder.with(CatalogFeatures.Feature.PREFER, prefer);
        }
        if (defer != null) {
            builder = builder.with(CatalogFeatures.Feature.DEFER, defer);
        }
        if (resolve != null) {
            builder = builder.with(CatalogFeatures.Feature.RESOLVE, resolve);
        }

        return builder.build();
    }

    /**
     * Passing on the CatalogFeatures settings from one Xerces configuration
     * object to another.
     *
     * @param config1 a Xerces configuration object
     * @param config2 a Xerces configuration object
     */
    public static void catalogFeaturesConfig2Config(XMLComponentManager config1,
            ParserConfigurationSettings config2) {
        boolean supportCatalog = true;
        boolean useCatalog = config1.getFeature(XMLConstants.USE_CATALOG);
        try {
            config2.setFeature(JdkXmlUtils.USE_CATALOG, useCatalog);
        } catch (XMLConfigurationException e) {
            supportCatalog = false;
        }

        if (supportCatalog && useCatalog) {
            try {
                for (CatalogFeatures.Feature f : CatalogFeatures.Feature.values()) {
                    config2.setProperty(f.getPropertyName(), config1.getProperty(f.getPropertyName()));
                }
            } catch (XMLConfigurationException e) {
                //shall not happen for internal settings
            }
        }
    }

    /**
     * Passing on the CatalogFeatures settings from a Xerces configuration
     * object to an XMLReader.
     *
     * @param config a Xerces configuration object
     * @param reader an XMLReader
     */
    public static void catalogFeaturesConfig2Reader(XMLComponentManager config, XMLReader reader) {
        boolean supportCatalog = true;
        boolean useCatalog = config.getFeature(XMLConstants.USE_CATALOG);
        try {
            reader.setFeature(JdkXmlUtils.USE_CATALOG, useCatalog);
        } catch (SAXNotRecognizedException | SAXNotSupportedException e) {
            supportCatalog = false;
        }

        if (supportCatalog && useCatalog) {
            try {
                for (CatalogFeatures.Feature f : CatalogFeatures.Feature.values()) {
                    reader.setProperty(f.getPropertyName(), config.getProperty(f.getPropertyName()));
                }
            } catch (SAXNotRecognizedException | SAXNotSupportedException e) {
                //shall not happen for internal settings
            }
        }
    }
}
