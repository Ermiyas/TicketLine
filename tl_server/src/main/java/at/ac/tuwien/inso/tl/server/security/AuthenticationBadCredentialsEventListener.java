package at.ac.tuwien.inso.tl.server.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.tl.server.service.EmployeeService;

@Component
public class AuthenticationBadCredentialsEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
	private static final Logger LOG = Logger.getLogger(AuthenticationBadCredentialsEventListener.class);
	
	@Autowired
	EmployeeService service;
	
	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent arg0) {
		LOG.info("Invalid Credentials");
		
		if(arg0.getAuthentication().getPrincipal() instanceof String){
			service.increaseWrongPasswordCounter((String)arg0.getAuthentication().getPrincipal());
		}
	}

}
