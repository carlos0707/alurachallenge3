package br.com.alura.alurachallenge3.repository;

import br.com.alura.alurachallenge3.model.ArquivoUpload;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ArquivoUploadRepository extends JpaRepository<ArquivoUpload, Long> {


    @Query(value = "select * from arquivo_upload where data_transacoes = :data_transacoes", nativeQuery = true)
    List<ArquivoUpload> findByDataTransacao(@Param("data_transacoes") LocalDate data_transacoes);

}
