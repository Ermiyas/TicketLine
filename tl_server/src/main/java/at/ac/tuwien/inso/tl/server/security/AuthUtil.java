package at.ac.tuwien.inso.tl.server.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import at.ac.tuwien.inso.tl.dto.UserStatusDto;

public class AuthUtil {

	public static UserStatusDto getUserStatusDto(Authentication authentication) {
		UserStatusDto result = new UserStatusDto();
		
		Object principal = authentication.getPrincipal();
		if (principal instanceof TicketlineUser == false) {
			return result;
		}
		
		TicketlineUser user = (TicketlineUser)principal;
		
		result.setUsername(user.getUsername());
		result.setFirstName(user.getFirstName());
		result.setLastName(user.getLastName());
		
		result.setAnonymous(false);
		
		if (user.getAuthorities().size() > 0) {
			for (GrantedAuthority a : user.getAuthorities()) {
				result.getRoles().add(a.getAuthority());
			}
		}
		
		return result;
	}
}
