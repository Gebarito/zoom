package com.zoom.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.PrimeFaces;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import com.zoom.modelo.Unidade;
import com.zoom.modelo.Usuario;
import com.zoom.modelo.enums.Status;
import com.zoom.service.LoginService;
import com.zoom.service.UsuarioService;
import com.zoom.util.MessageUtil;
import com.zoom.util.NegocioException;
import com.zoom.util.listener.SessionCounter;

import javax.persistence.NoResultException;
import lombok.extern.log4j.Log4j;

/**
 * @author Edson Murakami
 *
 */
@Log4j
@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private LoginService loginService;

    
	private String serverId;
	private String email;
	private String senha;
	private Usuario usuario = null;	
	private MenuModel modeloMenu = null;
	private List<Unidade> unidades = null;	
	private Unidade unidadeTemp = new Unidade();
	private boolean autenticado = false;
 	
	@PostConstruct
	public void inicializar() {	 
		
		//theme = ThemeService.getThemeDefault(); // tema default = cupertino		
		log.info("LoginBean inicializando...");
		getInstanceID();
		
	}
	
	/*
	 * pega o ID da instancia EC2
	 */
	private void getInstanceID() {
		
		try {
			serverId = InetAddress.getLocalHost().getHostName();
						
			log.debug("LocalHost serverId InetAdress Hostname: " + serverId);
			log.debug("LocalHost serverId InetAdress HostAdress: " + InetAddress.getLocalHost().getHostAddress());			
			
			HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			String url = origRequest.getScheme() + "://" + origRequest.getServerName() + ":" + origRequest.getServerPort();
			log.debug("LocalHost url email: " + url);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			serverId = System.getenv("HOSTNAME");
			log.debug("LocalHost getEnv: " + System.getenv("HOSTNAME"));
		}	
	}
	
	private Usuario isValidUser() throws NegocioException {
		
		usuario = usuarioService.buscarPeloEmail(email);		
		
		if(usuario != null) {			

			if(usuario.getStatus() == Status.INATIVO) {					
				throw new NegocioException("Usuario INATIVO!");
			}
			else {			
				String pwDigitada = senha;					
				String pwRecuperada = usuario.getSenha();
				
				if(!BCrypt.checkpw(pwDigitada, pwRecuperada)) {						
					throw new NegocioException("Senha inválida!");
				}
				
				// troca para o tema do usuario, se houver
				/*if(usuario.getTheme() != null) {
					theme = themeService.getThemes().get(usuario.getTheme());
				}
				*/
			}
		}
		else{
			throw new NegocioException("E-mail inválido!");
		}
		
		return usuario;		
	}
	
	public String entrar() {
		log.info("entrar: ");
		
		FacesMessage message = null; 
        boolean loggedIn = false;        
		
		try {
			
			HttpSession session = getSession(); // creates new empty session
			
			usuario = (Usuario)session.getAttribute("usuario");
			
			// se usuario não está na sessao = não está logado
			if(usuario == null) {
				
				usuario = isValidUser();	// busca usuario no banco				
				
				if(usuario != null) {									
					
					this.setUnidades(this.usuarioService.buscarUnidades());

					log.info("prop ORIGINAL do usuarioLogado (" + usuario.getNome() + ") : " + usuario.getUnidade().getNome());
					this.unidadeTemp = usuario.getUnidade();
					
					MessageUtil.info("Bem vindo " + usuario.getNome() + "!");
					loggedIn = true;					
					
					autenticado = true;
				
				} else {
					return "/restricted/home/ZoomHome.xhtml";
				}
			}
		} catch (NoResultException e) {
			e.printStackTrace();
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro de Login. Verifique seu e-mail. ", "Verifique seu e-mail.");
			FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
		} catch (NegocioException e) {
			log.error(e.getMessage());
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro de Login! ", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
		} catch (Exception e) {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro de Login! ", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
			e.printStackTrace();
		}
		
		// já está logado
		return null;
	}
	
	public String sair() {
		log.info("Session invalidate");		
		
		getExternalContext().invalidateSession();	    
		
		try {
			getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/Home.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/Home.xhtml";
	}
	
	public void trocarPerfil() {		
		try {
			log.info("Alterar Perfil no loginbean" );
			getExternalContext().redirect(getExternalContext().getRequestContextPath()+"/restricted/cadastros/TrocarPerfil.xhtml");
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	public void trocarSenha() {			
		try {
			getExternalContext().redirect(getExternalContext().getRequestContextPath()+"/restricted/cadastros/TrocarSenha.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	private MenuModel montaMenu() {
		if (modeloMenu == null) {
			if (getUsuario() != null) {
				try {
										
					loginService.setExternalContext(getExternalContext(), getUsuario());
					log.info("USUARIO: " + getUsuario().getNome());				
					
					modeloMenu = loginService.montarMenu(getUsuario());
				} catch (Exception e) {					
					e.printStackTrace();
				}
			} else {
				return new DefaultMenuModel();
			}
		} 
		
		return modeloMenu;
	}
	
	public Usuario getUsuario() {	
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getUserName() {				
		return usuario.getEmail();
	}
	
	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public MenuModel getMenu() {
		return modeloMenu;
	}
	
	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public String registraUnidadeTemp() {
		// prop temporaria - logado em prop diferente da origem
		
		try {
		
			HttpSession session = getSession(); // creates new empty session
			
			Usuario usuarioSessao = (Usuario)session.getAttribute("usuario");
			if(usuarioSessao != null) {
				log.info("prop do usuarioLogado OBTIDO da sessão : " + usuarioSessao.getUnidade().getNome());
				return "/restricted/home/ZoomHome.xhtml";
			}
			else {
				// seta a unidade temporária
				if(unidadeTemp != null) {
					usuario.setUnidade(unidadeTemp);	
				}
				session.setAttribute("usuario", usuario);
				
				log.debug("Montando menu... " + LocalDateTime.now());
				montaMenu();
				log.debug("Montado. " + LocalDateTime.now());
				
				log.info("prop do usuarioLogado COLOCADO na sessão: " + usuario.getUnidade().getNome());
			}
			
			return "/restricted/home/ZoomHome.xhtml";
			
		}catch(Exception e) {
			MessageUtil.info("Problema na sessão do login! ");
			e.printStackTrace();
		}
		return "/Home.xhtml";
	}

	public Unidade getUnidadeTemp() {
		return unidadeTemp;
	}
	public void setUnidadeTemp(Unidade unidade) {
		this.unidadeTemp = unidade;
	}
	
	public boolean unidadesCarregadas() {
		return getUnidades() != null ? true : false;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}
	
	public String getServerId() {
		return serverId;
	}
	
	public int getCounter() {
		
		SessionCounter counter = (SessionCounter) getSession().getAttribute(SessionCounter.COUNTER);
		return counter.getActiveSessionNumber();
	}

	//TODO  criar um mecanismo para mostrar o tempo restante de sessão...primefaces
	public long getTempoRestante() {
		
		Long segundos = getSession().getMaxInactiveInterval() -
                (System.currentTimeMillis() - getSession().getLastAccessedTime()) / 1000;
		
		long sessionTime = TimeUnit.SECONDS.toMinutes(segundos); // Exibe o tempo (em minutos) restante
		return sessionTime;
	}
	
	private HttpSession getSession() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession session = request.getSession();
		
		return session;
	}


}
