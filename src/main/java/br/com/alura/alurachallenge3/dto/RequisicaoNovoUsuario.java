package br.com.alura.alurachallenge3.dto;

import br.com.alura.alurachallenge3.model.Usuario;

import javax.validation.constraints.NotBlank;

public class RequisicaoNovoUsuario {

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    private Long id;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario toUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);

        return usuario;
    }
}
