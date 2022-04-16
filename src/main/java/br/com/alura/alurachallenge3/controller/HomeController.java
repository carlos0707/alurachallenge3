package br.com.alura.alurachallenge3.controller;

import br.com.alura.alurachallenge3.model.ArquivoUpload;
import br.com.alura.alurachallenge3.model.Transacao;
import br.com.alura.alurachallenge3.repository.ArquivoUploadRepository;
import br.com.alura.alurachallenge3.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ArquivoUploadRepository arquivoUploadRepository;

    private ArrayList<Transacao> transacoes = new ArrayList<>();

    private LocalDate dataDaTransacao;

    @GetMapping("/index")
    public String home(){
        return "home";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/index", consumes = {"multipart/form-data"})
    public ModelAndView importar(ArquivoUpload arquivoUpload, final MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();

        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .forEach(t -> {
                    Transacao transacao = this.tratarDados(t);
                    transacaoRepository.save(transacao);
                });

        arquivoUpload.setDataTransacoes(this.dataDaTransacao);
        arquivoUpload.setDataImportacao(LocalDate.now());

        ModelAndView andView = new ModelAndView("home");

        arquivoUploadRepository.save(arquivoUpload);

        andView.addObject("arquivoUpload", arquivoUploadRepository.findAll());

        return andView;
    }

    private Transacao tratarDados(String linha){

        String[] arrayTratamento = linha.split(",");

        String [] dataTratamento = arrayTratamento[7].split("T");

        Transacao transacao = new Transacao();

        transacao.setBancoOrigem(arrayTratamento[0]);
        transacao.setAgenciaOrigem(arrayTratamento[1]);
        transacao.setContaOrigem(arrayTratamento[2]);
        transacao.setBancoDestino(arrayTratamento[3]);
        transacao.setAgenciaDestino(arrayTratamento[4]);
        transacao.setContaDestino(arrayTratamento[5]);
        transacao.setValor(new BigDecimal(arrayTratamento[6]));
        transacao.setDataTransacao(LocalDate.parse(dataTratamento[0], formatter));
        transacao.setHoraTransacao(dataTratamento[1]);

        this.dataDaTransacao = transacao.getDataTransacao();

        this.transacoes.add(transacao);

        return transacao;
    }

}
