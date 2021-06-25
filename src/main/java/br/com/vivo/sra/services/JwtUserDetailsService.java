package br.com.vivo.sra.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vivo.sra.entities.Usuario;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = usuarioService.findByNome(username);
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		String encodedPw = bpe.encode(user.getsenha());
		if (user.getNome().equals(username)) {
			return new User(user.getNome(), encodedPw,
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
