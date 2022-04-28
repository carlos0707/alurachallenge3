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

    @Query(value = "select * from transacao where extract(month from data_transacao) = :opcaomes", nativeQuery = true)
    List<Transacao> findByMes(@Param("opcaomes")  Double opcaomes);

    @Query(value = "SELECT banco_origem, agencia_origem, conta_origem, sum(valor) " +
            "FROM transacao where extract(month from data_transacao) = :opcaomes group by banco_origem," +
            " agencia_origem, conta_origem", nativeQuery = true)
    List<String> findTransacaoGroupedContaOrigem(@Param("opcaomes") Double opcaomes);

    @Query(value = "SELECT banco_destino, agencia_destino, conta_destino, sum(valor) " +
            "FROM transacao where extract(month from data_transacao) = :opcaomes group by banco_destino," +
            " agencia_destino, conta_destino", nativeQuery = true)
    List<String> findTransacaoGroupedContaDestino(@Param("opcaomes") Double opcaomes);


    @Query(value = "select banco_origem, agencia_origem, sum(valor) from transacao where" +
            " extract(month from data_transacao) = :opcaomes group by banco_origem, agencia_origem", nativeQuery = true)
    List<String> findTransacaoGroupedAgenciaOrigem(@Param("opcaomes") Double parseDouble);

    @Query(value = "select banco_destino, agencia_destino, sum(valor) from transacao where" +
            " extract(month from data_transacao) = :opcaomes group by banco_destino, agencia_destino", nativeQuery = true)
    List<String> findTransacaoGroupedAgenciaDestino(@Param("opcaomes") Double parseDouble);
}
