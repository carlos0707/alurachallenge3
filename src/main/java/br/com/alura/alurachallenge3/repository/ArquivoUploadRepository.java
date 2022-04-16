package br.com.alura.alurachallenge3.repository;

import br.com.alura.alurachallenge3.model.ArquivoUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivoUploadRepository extends JpaRepository<ArquivoUpload, Long> {
}
