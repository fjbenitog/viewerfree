package es.viewerfree.gwt.client.util;

public class StringComparator  {

	public int compare(String o1, String o2) {
		if (o1 == o2) {
			return 0;
		}

		// Compare the name columns.
		if (o1 != null) {
			return (o2 != null) ? o1.compareToIgnoreCase(o2) : 1;
		}
		return -1;
	}

}
