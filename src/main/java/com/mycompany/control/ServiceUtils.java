package com.mycompany.control;

public class ServiceUtils {
	
	protected static String[] splitSearchString(String searchString) {
		String[] searchTerms = new String[] { searchString };
		if (searchString.contains(", ")) {
			searchTerms = searchString.split(", ");
		} else if (searchString.contains("; ")) {
			searchTerms = searchString.split("; ");
		} else if (searchString.contains(",")) {
			searchTerms = searchString.split(",");
		} else if (searchString.contains(";")) {
			searchTerms = searchString.split(";");
		} else if (searchString.contains(" ")) {
			searchTerms = searchString.split(" ");
		}
		return searchTerms;
	}

}
