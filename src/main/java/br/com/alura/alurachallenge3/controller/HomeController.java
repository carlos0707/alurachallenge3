package br.com.alura.alurachallenge3.controller;

import br.com.alura.alurachallenge3.model.ArquivoUpload;
import br.com.alura.alurachallenge3.model.Transacao;
import br.com.alura.alurachallenge3.repository.ArquivoUploadRepository;
import br.com.alura.alurachallenge3.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@Controller
public class HomeController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ArquivoUploadRepository arquivoUploadRepository;

    private ArrayList<Transacao> transacoes = new ArrayList<>();

    private LocalDate dataDaTransacao;

    @RequestMapping(method = RequestMethod.GET, value = "/index")
    public ModelAndView home(){

        ModelAndView andView = new ModelAndView("home");
        andView.addObject("arquivoUpload", arquivoUploadRepository.findAll());

        return andView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/index", consumes = {"multipart/form-data"})
    public ModelAndView importar(ArquivoUpload arquivoUpload, final MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();

        ModelAndView andView = new ModelAndView("home");

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        try {
            if (file.getSize() > 0) {
                for (String line = br.readLine(); line != null; line = br.readLine()) {

                    String[] arrayTratamento = line.split(",");
                    String[] dataTratamento = arrayTratamento[7].split("T");

                    Transacao transacao = new Transacao();

                    if (arrayTratamento.length != 8) {
                        continue;
                    }

                    if(this.dataDaTransacao != null && LocalDate.parse(dataTratamento[0], formatter) != this.dataDaTransacao){
                        continue;
                    }

                    LocalDate data = LocalDate.parse(dataTratamento[0]);

                    if (arquivoUploadRepository.findByDataTransacao(data).size() > 0) {

                        andView.addObject("msg", "Data de transação já foi realizada");
                        throw new Exception("Data de transação já foi realizada");
                    }

                    transacao.setBancoOrigem(arrayTratamento[0]);
                    transacao.setAgenciaOrigem(arrayTratamento[1]);
                    transacao.setContaOrigem(arrayTratamento[2]);
                    transacao.setBancoDestino(arrayTratamento[3]);
                    transacao.setAgenciaDestino(arrayTratamento[4]);
                    transacao.setContaDestino(arrayTratamento[5]);
                    transacao.setValor(new BigDecimal(arrayTratamento[6]));
                    transacao.setDataTransacao(LocalDate.parse(dataTratamento[0], formatter));
                    transacao.setHoraTransacao(dataTratamento[1]);

                    this.transacoes.add(transacao);

                    this.dataDaTransacao = this.transacoes.get(0).getDataTransacao();

                    transacaoRepository.save(transacao);

                }

                arquivoUpload.setDataTransacoes(this.dataDaTransacao);
                arquivoUpload.setDataImportacao(LocalDate.now());

                arquivoUploadRepository.save(arquivoUpload);

                andView.addObject("arquivoUpload", arquivoUploadRepository.findAll());
            } else {
                andView.addObject("msg", "Arquivo não pode ser vazio!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    return andView;
}

}
