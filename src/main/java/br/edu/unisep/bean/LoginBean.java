package br.edu.unisep.bean;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class LoginBean {

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String senha;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    public String entrar() {

        var req = (HttpServletRequest) externalContext.getRequest();

        try {
            req.logout();
            req.login(login, senha);

            if (req.isUserInRole("ADMIN")) {
                return "admin/home";
            } else {
                return "app/home";
            }

        } catch (ServletException e) {
            e.printStackTrace();

            facesContext.addMessage("login", new FacesMessage("Dados inválidos para o Login"));

            return "login";
        }


    }

}
