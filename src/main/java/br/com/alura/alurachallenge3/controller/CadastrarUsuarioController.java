package br.com.alura.alurachallenge3.controller;

import br.com.alura.alurachallenge3.dto.RequisicaoNovoUsuario;
import br.com.alura.alurachallenge3.model.Usuario;
import br.com.alura.alurachallenge3.repository.UsuarioRepository;
import br.com.alura.alurachallenge3.security.ImplementacaoUserDetailsService;
import br.com.alura.alurachallenge3.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Random;

@Controller
public class CadastrarUsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @RequestMapping(method = RequestMethod.GET, value = "/usuario/cadastrarusuario")
    public ModelAndView cadastroDeUsuario(Usuario usuario){

        ModelAndView andView = new ModelAndView("usuario/cadastrodeusuario");
        andView.addObject("usuario", new Usuario());

        return andView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/usuario/salvar")
    public ModelAndView cadastrarUsuario(@Valid Usuario usuario, BindingResult result){

        ModelAndView andView = new ModelAndView("usuario/cadastrodeusuario");

        if(result.hasErrors()){
            andView.addObject("usuario", usuario);
            return andView;
        }

        if(usuario.getId() == null){

            if(usuarioRepository.findUserByEmail(usuario.getEmail()) != null){
                ObjectError error = new ObjectError("globalError","Email já existe!");
                result.addError(error);

                return andView;
            }

            Random gerador = new Random();
            String senha = String.valueOf(gerador.nextInt(999999)+100000);
            System.out.println(senha);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String senhaEncriptada = encoder.encode(senha);

            usuario.setSenha(senhaEncriptada);

            emailService.sendEmail(usuario.getEmail(), senha);

        }

        usuarioRepository.save(usuario);

        andView.addObject("usuario", new Usuario());

        return andView;
    }

}
