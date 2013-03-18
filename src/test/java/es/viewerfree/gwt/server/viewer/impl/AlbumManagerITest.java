package es.viewerfree.gwt.server.viewer.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.viewerfree.gwt.server.ServiceTestSupport;
import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.shared.dto.UserDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/ApplicationContext.xml"})
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class AlbumManagerITest extends ServiceTestSupport{

	private static final String USER_2 = "USER_2";
	private static final String TAG1 = "TAG1";
	private static final String TAG2 = "TAG2";
	@Autowired
	private AlbumManager albumManager;
	
	@Test
	public void addTagOneAlbum() throws Exception {
		UserDto user = insertUser(createUserDto());
		albumManager.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),albumManager.getAlbums(user.getName(), TAG1));
	}
	
	@Test
	public void addTagTwoAlbum() throws Exception {
		UserDto user = insertUser(createUserDto());
		albumManager.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),albumManager.getAlbums(user.getName(), TAG1));
		
		albumManager.addTag(user.getName(), user.getAlbums().get(1), TAG1);
		assertEquals(ALBUMS,albumManager.getAlbums(user.getName(), TAG1));
	}
	
	@Test
	public void addTwoTagsToAlbum() throws Exception {
		UserDto user = insertUser(createUserDto());
		albumManager.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),albumManager.getAlbums(user.getName(), TAG1));
		
		albumManager.addTag(user.getName(), user.getAlbums().get(0), TAG2);
		
		assertEquals(Arrays.asList(ALBUMS.get(0)),albumManager.getAlbums(user.getName(), TAG1));
		assertEquals(Arrays.asList(ALBUMS.get(0)),albumManager.getAlbums(user.getName(), TAG2));
	}
	
	@Test
	public void addSameTwoTagsToAlbum() throws Exception {
		UserDto user = insertUser(createUserDto());
		albumManager.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),albumManager.getAlbums(user.getName(), TAG1));
		
		albumManager.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		
		assertEquals(Arrays.asList(ALBUMS.get(0)),albumManager.getAlbums(user.getName(), TAG1));
	}
	
	@Test
	public void addTagOneAlbumTwoUsers() throws Exception {
		UserDto user = insertUser(createUserDto());
		albumManager.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),albumManager.getAlbums(user.getName(), TAG1));
		
		UserDto user2 = insertUser(createUserDto(USER_2));
		albumManager.addTag(user2.getName(), user2.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),albumManager.getAlbums(user2.getName(), TAG1));
	}
	
	
}
