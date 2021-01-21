package com.ovi.appolis_server.clerk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ovi.appolis_server.clerk.data.model.Clerk;
import com.ovi.appolis_server.clerk.repository.ClerkRepository;
import com.ovi.appolis_server.security.JwtTokenUtil;
import com.ovi.appolis_server.security.JwtUserDetailsService;
import com.ovi.appolis_server.session.data.dto.LoginForm;

@Service
public class ClerkService {
	@Autowired
	private ClerkRepository clerkRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public Clerk authenticate(LoginForm loginForm) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));

			final UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getEmail());

			final String token = jwtTokenUtil.generateToken(userDetails);

			Clerk clerk = clerkRepository.findByEmailAndCityAndCountry(loginForm.getEmail(), loginForm.getCityName(),
					loginForm.getCountryName());
			clerk.setToken(token);
			return clerk;
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

	}

	public Clerk save(Clerk newClerk) {
		Clerk clerk = clerkRepository.findByEmailAndCityAndCountry(newClerk.getEmail(), newClerk.getCity().getName(),
				newClerk.getCity().getCountry().getName());
		if (clerk != null) {
			clerk.setFirstName(newClerk.getFirstName());
			clerk.setLastName(newClerk.getLastName());
		}

		newClerk.setPassword(bcryptEncoder.encode(newClerk.getPassword()));
		return clerkRepository.save(newClerk);
	}
}
