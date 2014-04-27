package at.ac.tuwien.inso.tl.client.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class BundleManager {
	private static final String BASENAME = "localization.tl_client";
	private static final String EXCEPTION_BASENAME = "localization.tl_client_exceptions";
	
	private static final ResourceBundle BUNDLE_DE = ResourceBundle.getBundle(BASENAME, Locale.GERMAN);
	private static final ResourceBundle BUNDLE_EN = ResourceBundle.getBundle(BASENAME, Locale.ENGLISH);
	
	private static final ResourceBundle EXCEPTION_BUNDLE_DE = ResourceBundle.getBundle(EXCEPTION_BASENAME, Locale.GERMAN);
	private static final ResourceBundle EXCEPTION_BUNDLE_EN = ResourceBundle.getBundle(EXCEPTION_BASENAME, Locale.ENGLISH);

	private static ResourceBundle BUNDLE = BUNDLE_DE;
	private static ResourceBundle EXCEPTION_BUNDLE = EXCEPTION_BUNDLE_DE;
	
	
	public static ResourceBundle getBundle(){
		return BUNDLE;
	}
	
	public static ResourceBundle getExceptionBundle(){
		return EXCEPTION_BUNDLE;
	}
	
	public static void changeLocale(Locale locale){
		if (Locale.GERMAN.equals(locale)) {
			BUNDLE = BUNDLE_DE;
			EXCEPTION_BUNDLE = EXCEPTION_BUNDLE_DE;
			return;
		}
		if (Locale.ENGLISH.equals(locale)) {
			BUNDLE = BUNDLE_EN;
			EXCEPTION_BUNDLE = EXCEPTION_BUNDLE_EN;
			return;
		}
	}
	
}
