package com.ovi.appolis_server.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.citizen.repository.CitizenRepository;
import com.ovi.appolis_server.clerk.data.model.Clerk;
import com.ovi.appolis_server.clerk.repository.ClerkRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private ClerkRepository clerkRepository;

	@Autowired
	private CitizenRepository citizenRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Clerk clerk = clerkRepository.findByEmail(email);

		String userEmail = null;
		String userPassword = null;

		if (clerk == null) {
			Citizen citizen = citizenRepository.findByEmail(email);
			if (citizen == null) {
				throw new UsernameNotFoundException("User not found with email: " + email);
			}

			userEmail = citizen.getEmail();
			userPassword = citizen.getPassword();
		} else {
			userEmail = clerk.getEmail();
			userPassword = clerk.getPassword();
		}
		return new User(userEmail, userPassword, new ArrayList<>());

	}
}
