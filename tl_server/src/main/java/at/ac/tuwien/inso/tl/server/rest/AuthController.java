package at.ac.tuwien.inso.tl.server.rest;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.UserStatusDto;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.security.AuthUtil;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	private static final Logger LOG = Logger.getLogger(AuthController.class);
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public UserStatusDto getUserStatus() throws ServiceException {
		LOG.info("getUserStatus called()");
		
		return AuthUtil.getUserStatusDto(SecurityContextHolder.getContext().getAuthentication());
	}
	
	
}
