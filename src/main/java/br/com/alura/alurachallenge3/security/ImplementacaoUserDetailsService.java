package br.com.alura.alurachallenge3.security;

import br.com.alura.alurachallenge3.model.Usuario;
import br.com.alura.alurachallenge3.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findUserByEmail(email);

        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não foi encontrado");
        }

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), true, true, true, usuario.getAuthorities());
    }
}
