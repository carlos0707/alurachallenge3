package br.com.alura.alurachallenge3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String emailCadastrado, String senhaCadastrada){
        try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mail);
            helper.setTo(emailCadastrado);
            helper.setFrom("josecarlos0707@outlook.com");
            helper.setSubject("Cadastro de nova senha");
            helper.setText("<p>Olá, sua nova senha é: "+senhaCadastrada+" </p>", true);
            mailSender.send(mail);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
