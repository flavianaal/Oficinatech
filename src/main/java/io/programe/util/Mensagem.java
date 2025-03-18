package io.programe.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Flaviana Andrade
 */
public class Mensagem {
    
    public static void addMensagem(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public static void mensagemInfo(String mensagem) {
        System.out.println("Mensagem recebida: " + mensagem);
        addMensagem(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
    }
    
    public static void mensagemAlerta(String mensagem) {
        System.out.println("Mensagem recebida: " + mensagem);
        addMensagem(FacesMessage.SEVERITY_WARN, mensagem, mensagem);
    }
    
    public static void mensagemErro(String mensagem) {
        System.out.println("Mensagem recebida: " + mensagem);
        addMensagem(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
    }
}
