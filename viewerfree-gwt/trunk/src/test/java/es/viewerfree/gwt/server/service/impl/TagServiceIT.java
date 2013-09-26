package es.viewerfree.gwt.server.service.impl;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.viewerfree.gwt.server.ServiceTestSupport;
import es.viewerfree.gwt.server.service.ITagService;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.service.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ApplicationTestContext.xml"})
public class TagServiceIT extends ServiceTestSupport{

	private static final String USER_2 = "USER_2";
	private static final String TAG1 = "TAG1";
	private static final String TAG2 = "TAG2";


	@Autowired
	private ITagService tagService;

	@Before
	public void setUp() throws ServiceException{
		deleteFromTables(TABLES);
	}


	@Test
	public void addTagOneAlbum() throws Exception {
		UserDto user = insertUser(createUserDto());
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG1));
	}

	@Test
	public void addTagTwoAlbum() throws Exception {
		UserDto user = insertUser(createUserDto());
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG1));

		tagService.addTag(user.getName(), user.getAlbums().get(1), TAG1);
		assertEquals(ALBUMS,tagService.getAlbums(user.getName(), TAG1));
	}

	@Test
	public void addTwoTagsToAlbum() throws Exception {
		UserDto user = insertUser(createUserDto());
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG1));

		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG2);

		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG1));
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG2));
	}

	@Test
	public void addSameTwoTagsToAlbum() throws Exception {
		UserDto user = insertUser(createUserDto());
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG1));

		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);

		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG1));
	}

	@Test
	public void addTagOneAlbumTwoUsers() throws Exception {
		UserDto user = insertUser(createUserDto());
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG1));

		UserDto user2 = insertUser(createUserDto(USER_2));
		tagService.addTag(user2.getName(), user2.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user2.getName(), TAG1));
	}

	@Test
	public void getTagsByAlbum() throws Exception {
		UserDto user = insertUser(createUserDto());
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG2);

		assertEquals(Arrays.asList(TAG1,TAG2),tagService.getTags(user.getName(), user.getAlbums().get(0)));
	}

	@Test
	public void getOtherTagsByAlbum() throws Exception {
		UserDto user = insertUser(createUserDto());
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG2);
		assertEquals(Arrays.asList(TAG1,TAG2),tagService.getTags(user.getName(), user.getAlbums().get(0)));
		

		tagService.addTag(user.getName(), user.getAlbums().get(1), TAG1);
		assertEquals(Arrays.asList(TAG1),tagService.getTags(user.getName(), user.getAlbums().get(1)));
		assertEquals(Arrays.asList(TAG2),tagService.getOtherTags(user.getName(), user.getAlbums().get(1)));
		
		assertEquals(Arrays.asList(TAG2,TAG1),tagService.getTags(user.getName(), user.getAlbums().get(0)));
		assertEquals(Arrays.asList(),tagService.getOtherTags(user.getName(), user.getAlbums().get(0)));
		
		tagService.removeTag(user.getName(), user.getAlbums().get(0), TAG2);
		
		tagService.addTag(user.getName(), user.getAlbums().get(1), TAG2);
		
		assertEquals(Arrays.asList(TAG1),tagService.getTags(user.getName(), user.getAlbums().get(0)));
		assertEquals(Arrays.asList(TAG2),tagService.getOtherTags(user.getName(), user.getAlbums().get(0)));
	}

	@Test
	public void removeTag() throws Exception {
		UserDto user = insertUser(createUserDto());
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG1));
		
		tagService.removeTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(),tagService.getAlbums(user.getName(), TAG1));
	}
	
	@Test
	public void getTagsByUser() throws Exception {
		UserDto user = insertUser(createUserDto());
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG2);
		tagService.addTag(user.getName(), user.getAlbums().get(1), TAG1);

		assertEquals(Arrays.asList(TAG1,TAG2),tagService.getTags(user.getName()));
	}
	
	@Test
	public void addTagAlreadyExist() throws Exception {
		UserDto user = insertUser(createUserDto());
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG1));
		
		tagService.removeTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(),tagService.getAlbums(user.getName(), TAG1));
		
		tagService.addTag(user.getName(), user.getAlbums().get(0), TAG1);
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG1));
	}

}
