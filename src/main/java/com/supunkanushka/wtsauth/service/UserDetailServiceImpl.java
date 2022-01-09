package com.supunkanushka.wtsauth.service;

import com.supunkanushka.wtsauth.model.AuthUserDetail;
import com.supunkanushka.wtsauth.model.User;
import com.supunkanushka.wtsauth.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {


	@Autowired
	UserDetailRepository userDetailRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userDetailRepository.findByUsername(username);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("invalid user"));

		UserDetails userDetails = new AuthUserDetail(optionalUser.get());

		// check user status
		new AccountStatusUserDetailsChecker().check(userDetails);

		return userDetails;
	}
}
