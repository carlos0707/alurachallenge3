package br.com.alura.alurachallenge3.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;

    @Column(unique = true)
    private String email;
    private String senha;
    private Integer ativo;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_role",
                        joinColumns = @JoinColumn(name = "usuario_id",
                        referencedColumnName = "id",
                        table = "usuario"),

                        inverseJoinColumns = @JoinColumn(name = "role_id",
                        referencedColumnName = "id",
                        table = "role"))
    private List<Role> roles;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<ArquivoUpload> arquivos;

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public List<ArquivoUpload> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<ArquivoUpload> arquivos) {
        this.arquivos = arquivos;
    }

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    private Boolean parseAtivo(Integer ativo){
        if(ativo == 1){
            return true;
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return String.valueOf(senha);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return parseAtivo(ativo);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", roles=" + roles +
                '}';
    }
}
