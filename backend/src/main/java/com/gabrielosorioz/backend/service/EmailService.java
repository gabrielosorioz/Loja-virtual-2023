package com.gabrielosorioz.backend.service;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarEmailTexto(String destinatario, String titulo, String mensagem){
        //Implementar envio de email com anexo
        try {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(remetente);
        simpleMailMessage.setTo(destinatario);
        simpleMailMessage.setText(mensagem);
        javaMailSender.send(simpleMailMessage);

        return "Email enviado";

        } catch (Exception ex){
            return 
            "Erro ao enviar o email";
        }
    }

    public void enviarEmailTemplate(String destinatario, String titulo, Map<String,Object> propriedades ){
       MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {

                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

                mimeMessageHelper.setSubject(titulo);
                mimeMessageHelper.setFrom(titulo);
                mimeMessageHelper.setTo(destinatario);

                mimeMessageHelper.setText(getConteudoTemplate(propriedades), true);
                javaMailSender.send(mimeMessageHelper.getMimeMessage());
                
            }catch(MessagingException e){
                e.printStackTrace();
            }
    }

    public String getConteudoTemplate(Map<String,Object> model ){
        StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-recuperacao-codigo.flth"),model));
        } catch (Exception e){
            e.printStackTrace();
        }
        return content.toString();
    }
}
