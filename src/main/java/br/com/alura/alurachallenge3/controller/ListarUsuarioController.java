package br.com.alura.alurachallenge3.controller;

import br.com.alura.alurachallenge3.model.Usuario;
import br.com.alura.alurachallenge3.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ListarUsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/listarusuarios")
    public ModelAndView listarUsuario(){

        ModelAndView andView = new ModelAndView("listarusuarios");
        andView.addObject("usuariosobj", usuarioRepository.findAll());

        return andView;
    }

    @GetMapping("/editarusuario/{idusuario}")
    public ModelAndView editar(@PathVariable("idusuario") Long idusuario){

        Optional<Usuario> usuario = usuarioRepository.findById(idusuario);

        ModelAndView andView = new ModelAndView("usuario/cadastrodeusuario");
        andView.addObject("usuario", usuario.get());

        return andView;
    }

    @GetMapping("/deletarusuario/{idusuario}")
    public ModelAndView deletar(@PathVariable("idusuario") Long idusuario){

        ModelAndView andView = new ModelAndView("listarusuarios");

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuarioParaExcluir = usuarioRepository.findById(idusuario).get();

        if(usuarioParaExcluir.getEmail().equals(email)){
            andView.addObject("msg", "Você não pode excluir você mesmo!");
            andView.addObject("usuariosobj", usuarioRepository.findAll());
            return andView;
        }

        usuarioRepository.atualizaColunaAtivo(idusuario);

        andView.addObject("usuariosobj", usuarioRepository.findAll());

        return andView;
    }

}
