package at.ac.tuwien.inso.tl.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.MimeTypeUtils;

import at.ac.tuwien.inso.tl.dto.UserEvent;
import at.ac.tuwien.inso.tl.dto.UserStatusDto;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler  {
	
	private ObjectMapper mapper;

	public AuthenticationHandler() {
		this.mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(Feature.ESCAPE_NON_ASCII, true);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		UserStatusDto usd = AuthUtil.getUserStatusDto(authentication);
		usd.setEvent(UserEvent.AUTH_SUCCESS);
		
		this.printUserStatusDto(usd, response);
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		UserStatusDto usd = new UserStatusDto();
		usd.setEvent(UserEvent.WRONG_CREDENTIALS);
		
		if(exception.getExtraInformation() instanceof UserDetails){
			UserDetails ud = (UserDetails)exception.getExtraInformation();
			if(!ud.isAccountNonLocked()){
				usd.setEvent(UserEvent.USER_LOCKED);
			}
		}

		this.printUserStatusDto(usd, response);
	}
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		UserStatusDto usd = new UserStatusDto();
		usd.setEvent(UserEvent.LOGOUT);

		this.printUserStatusDto(usd, response);
	}
	
	private void printUserStatusDto(UserStatusDto usd, HttpServletResponse response) 
			throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
		
		String result = null;
		try {
			result = this.mapper.writeValueAsString(usd);
		} catch (JsonProcessingException e) {
			throw new ServletException(e);
		}
		
		response.getOutputStream().print(result);
	}

}
