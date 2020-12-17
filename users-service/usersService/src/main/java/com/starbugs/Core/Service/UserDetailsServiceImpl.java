package com.starbugs.Core.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.starbugs.Core.Model.AppUser;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UsersService userService;
	
	public UserDetailsServiceImpl(UsersService userService) {
		 this.userService = userService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUser user = userService.getUserByUsername(email);
		if(user == null) {
			throw new UsernameNotFoundException("User Not Found with Client-Email: " + email);
		}
		
		
		return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
	}
	

}
