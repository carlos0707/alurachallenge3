package br.com.alura.alurachallenge3.repository;

import br.com.alura.alurachallenge3.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select * from Usuario where email = :email", nativeQuery = true)
    Usuario findUserByEmail(@Param("email") String email);

    @Modifying
    @Query(value = "update Usuario set ativo = 0 where id = :idusuario", nativeQuery = true)
    void atualizaColunaAtivo(@Param("idusuario") Long idusuario);
}
