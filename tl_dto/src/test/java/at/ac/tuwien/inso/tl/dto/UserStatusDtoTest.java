package at.ac.tuwien.inso.tl.dto;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserStatusDtoTest {

	@Test
	public void userStatusDtoIsAnonymous() {
		UserStatusDto usd = new UserStatusDto();
		assertTrue(usd.isAnonymous());
	}
}
