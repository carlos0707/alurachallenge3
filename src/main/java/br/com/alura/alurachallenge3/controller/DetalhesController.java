package br.com.alura.alurachallenge3.controller;

import br.com.alura.alurachallenge3.model.ArquivoUpload;
import br.com.alura.alurachallenge3.model.Transacao;
import br.com.alura.alurachallenge3.model.Usuario;
import br.com.alura.alurachallenge3.repository.ArquivoUploadRepository;
import br.com.alura.alurachallenge3.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DetalhesController {

    @Autowired
    private ArquivoUploadRepository arquivoUploadRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @GetMapping("/detalhes/{idarquivo}")
    public ModelAndView detalhes(@PathVariable("idarquivo") Long idarquivo){
        ModelAndView andView = new ModelAndView("detalhesimportacao");

        ArquivoUpload arquivoUpload = arquivoUploadRepository.findById(idarquivo).get();

        List<Transacao> transacoes = transacaoRepository.findByIdArquivo(idarquivo);

        String nome = arquivoUpload.getUsuario().getNome();

        andView.addObject("arquivoUplaod", arquivoUpload);
        andView.addObject("transacoes", transacoes);
        andView.addObject("nomeusuario", arquivoUpload.getUsuario().getNome());

        return andView;
    }

}
