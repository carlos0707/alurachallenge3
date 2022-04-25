package br.com.alura.alurachallenge3.repository;

import br.com.alura.alurachallenge3.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query(value = "select * from transacao where arquivo_id = :idarquivo", nativeQuery = true)
    List<Transacao> findByIdArquivo(@Param("idarquivo") Long arquivoid);
}
