package br.com.alura.alurachallenge3.controller;

import br.com.alura.alurachallenge3.model.Transacao;
import br.com.alura.alurachallenge3.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AnaliseDeTransacaoController {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @GetMapping("/analisedetransacao")
    public String analiseDeTransacao(){
        return "analisedetransacao";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/analisedetransacao/analisar")
    public ModelAndView analisar(@RequestParam("opcaomes") String opcaomes){

        ModelAndView andView = new ModelAndView("analisedetransacao");

        List<Transacao> byMes      = transacaoRepository.findByMes(Double.parseDouble(opcaomes));
        List<String> groupedOrigem = transacaoRepository.findTransacaoGroupedContaOrigem(Double.parseDouble(opcaomes));
        List<String> groupedDestino = transacaoRepository.findTransacaoGroupedContaDestino(Double.parseDouble(opcaomes));
        List<String> groupedAgenciaOrigem = transacaoRepository.findTransacaoGroupedAgenciaOrigem(Double.parseDouble(opcaomes));
        List<String> groupedAgenciaDestino = transacaoRepository.findTransacaoGroupedAgenciaDestino(Double.parseDouble(opcaomes));

        List<Transacao> transacoesSuspeitas = new ArrayList<>();
        List<Transacao> contasSuspeitas     = new ArrayList<>();
        List<Transacao> agenciasSuspeitas   = new ArrayList<>();

        BigDecimal milhar = new BigDecimal(5000);
        BigDecimal milhao = new BigDecimal(20000);
        BigDecimal bilhao = new BigDecimal(20000);

        byMes.stream().filter(t -> t.getValor().compareTo(milhao) == 0 || t.getValor().compareTo(milhar) == 1)
                .forEach(t -> transacoesSuspeitas.add(t));

        groupedOrigem.stream().forEach(s -> {
            String[] split = s.split(",");

            if(new BigDecimal(split[3]).compareTo(milhao) == 1 ){
                Transacao transacao = new Transacao();
                transacao.setBancoOrigem(split[0]);
                transacao.setAgenciaOrigem(split[1]);
                transacao.setContaOrigem(split[2]);
                transacao.setValor(new BigDecimal(split[3]));

                contasSuspeitas.add(transacao);
            }
        });

        groupedDestino.stream().forEach(s -> {
            String[] split = s.split(",");

            if(new BigDecimal(split[3]).compareTo(milhao) == 1 ){
                Transacao transacao = new Transacao();
                transacao.setBancoDestino(split[0]);
                transacao.setAgenciaDestino(split[1]);
                transacao.setContaDestino(split[2]);
                transacao.setValor(new BigDecimal(split[3]));

                contasSuspeitas.add(transacao);
            }
        });

        groupedAgenciaOrigem.stream().forEach(s -> {

            String[] split = s.split(",");

            if(new BigDecimal(split[2]).compareTo(bilhao) == 1 ){
                Transacao transacao = new Transacao();
                transacao.setBancoOrigem(split[0]);
                transacao.setAgenciaOrigem(split[1]);
                transacao.setValor(new BigDecimal(split[2]));

                agenciasSuspeitas.add(transacao);
            }

        });

        groupedAgenciaDestino.stream().forEach(s -> {

            String[] split = s.split(",");

            if(new BigDecimal(split[2]).compareTo(bilhao) == 1 ){
                Transacao transacao = new Transacao();
                transacao.setBancoDestino(split[0]);
                transacao.setAgenciaDestino(split[1]);
                transacao.setValor(new BigDecimal(split[2]));

                agenciasSuspeitas.add(transacao);
            }

        });

        andView.addObject("transacoessuspeitas", transacoesSuspeitas);
        andView.addObject("contassuspeitas",contasSuspeitas);
        andView.addObject("agenciassuspeitas", agenciasSuspeitas);

        return andView;

    }
}
