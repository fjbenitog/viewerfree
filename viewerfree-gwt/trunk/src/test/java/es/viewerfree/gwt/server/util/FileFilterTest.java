package es.viewerfree.gwt.server.util;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FileFilterTest {

	private static final String EXCLUDES = ".svn,.bak";
	private static final String INCLUDES = "jpg,gif,bmp";
	private FileFilter fileFilter;
	
	@Before
	public void setUp() throws Exception {
		fileFilter = new FileFilter(INCLUDES, EXCLUDES, false);
	}

	@Test
	public void testAccept() throws Exception {
		assertTrue(fileFilter.accept(null, "ll.jpg"));
		assertFalse(fileFilter.accept(null, "ll.ss"));
		assertFalse(fileFilter.accept(null, ".svn"));
	}
}
