package at.ac.tuwien.inso.tl.server.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import at.ac.tuwien.inso.tl.server.service.EmployeeService;

public class AuthenticationSuccessEventListener implements
		ApplicationListener<AuthenticationSuccessEvent> {
private static final Logger LOG = Logger.getLogger(AuthenticationSuccessEventListener.class);
	
	@Autowired
	EmployeeService empDao;
	
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent arg0) {
		LOG.info("Valid Credentials");
		
		if (arg0.getAuthentication().getPrincipal() instanceof TicketlineUser) {
			TicketlineUser user = (TicketlineUser)arg0.getAuthentication().getPrincipal();
			empDao.resetWrongPasswordCounter(user.getUsername());
			LOG.debug("Reset wrongPasswordCounter to 0");
		}
	}
}
