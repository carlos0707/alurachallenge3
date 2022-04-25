package br.com.alura.alurachallenge3.repository;

import br.com.alura.alurachallenge3.model.ArquivoUpload;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface ArquivoUploadRepository extends JpaRepository<ArquivoUpload, Long> {

    @Override
    List<ArquivoUpload> findAll(Sort sort);

    @Query(value = "select * from arquivo_upload where data_transacoes = :data_transacoes", nativeQuery = true)
    List<ArquivoUpload> findByDataTransacao(@Param("data_transacoes") LocalDate data_transacoes);

}
