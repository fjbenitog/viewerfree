package es.viewerfree.gwt.server.filter;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import es.viewerfree.gwt.shared.ParamKey;
import es.viewerfree.gwt.shared.dto.UserDto;



public class LoginFilterTest {

	private static final String PASSWORD = "passwrod";

	private static final String USER = "user";

	private static LoginFilter _filter;

	private  MockHttpServletRequest _httpServletRequestMock;
	private  MockHttpServletResponse _httpServletResponseMock;
	private  FilterChain _filterChainMock;
	private  FilterConfig _filterConfig;
	private  UserDto userDto;
	
	@Before 
	public  void setUp() throws IOException, ServletException{
		_httpServletRequestMock = new MockHttpServletRequest();
		_httpServletRequestMock.setContextPath("TEST");
		_httpServletResponseMock = new MockHttpServletResponse();
		_filterChainMock = Mockito.mock(FilterChain.class);
		_filter = new LoginFilter();
		_filterConfig = Mockito.mock(FilterConfig.class);
		_filter.init(_filterConfig);
		userDto = new UserDto(USER, PASSWORD);
	}
	
	@After public  void tearDown(){
		_filter = null;
		_filterChainMock = null;
		_httpServletResponseMock = null;
		_httpServletRequestMock=null;
	}

	@Test
	public void testDoFilterValidateUser() throws Exception {
		_httpServletRequestMock.getSession().setAttribute(ParamKey.USER.toString(), userDto);
		_filter.doFilter(_httpServletRequestMock, _httpServletResponseMock, _filterChainMock);
		Mockito.verify(_filterChainMock).doFilter(_httpServletRequestMock, _httpServletResponseMock);
	}
	
	@Test
	public void testDoFilterNotValidateUser() throws Exception {
		_filter.doFilter(_httpServletRequestMock, _httpServletResponseMock, _filterChainMock);
		Mockito.verify(_filterChainMock,Mockito.times(0)).doFilter(_httpServletRequestMock, _httpServletResponseMock);
		assertEquals("TEST/",_httpServletResponseMock.getRedirectedUrl());
	}

}
