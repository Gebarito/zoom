package com.zoom.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zoom.modelo.Usuario;
import com.zoom.modelo.enums.Role;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class LoginService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Document doc;
	private ExternalContext externalContext;
	MenuModel modelo = new DefaultMenuModel();

	@PostConstruct
	public void init() {
		log.info("LoginService montando menu...");
	}

	public MenuModel montarMenu(Usuario user) throws Exception {

		//log.info("Montando menu...");
		// menuBar principal
		MenuModel modelo = new DefaultMenuModel();		

		/*
		 * Lê o Menu.xml
		 */
		NodeList subMenus = doc.getElementsByTagName("submenu");

		/*
		 * lê todos os subMenus e lê todos os menuItem de cada subMenu e monta o subMenu
		 */
		for (int i = 0; i < subMenus.getLength(); i++) {

			// pega um subMenu
			Element subMenu = (Element) subMenus.item(i);

			// se o usuario tem permissão para acessar esse subMenu entra
			if (temPermissao(user.getRole(), subMenu)) {
				
				// pega o label do subMenu
				DefaultSubMenu novoSubMenu = DefaultSubMenu.builder()
						.label(subMenu.getAttribute("label"))
						.icon(subMenu.getAttribute("icon"))
						.build();

				// pega os menuItens do subMenu
				NodeList menuItens = subMenu.getElementsByTagName("menuitem");
				novoSubMenu = montarSubMenu(menuItens, novoSubMenu, user);

				// pega subMenus2 do subMenu
				NodeList submenus2 = subMenu.getElementsByTagName("submenu2");
				novoSubMenu = montarSubMenu2(submenus2, novoSubMenu, user);

				modelo.getElements().add(novoSubMenu);
			}

		}

		/*
		 * Monta Menu do usuario
		 */
		DefaultSubMenu subMenuNome = DefaultSubMenu.builder().icon("pi pi-user").build();
		subMenuNome = addMenuItem(subMenuNome, user.getNome(), null);
		subMenuNome = addMenuItem(subMenuNome, user.getRole().name(), null);
		subMenuNome = addMenuItem(subMenuNome, user.getUnidade().getNome(), null);
		subMenuNome = addMenuItem(subMenuNome, "Alterar Perfil", "#{loginBean.trocarPerfil}");
		subMenuNome = addMenuItem(subMenuNome, "Alterar Senha", "#{loginBean.trocarSenha}");

		modelo.getElements().add(subMenuNome);	
		
		DefaultMenuItem div = new DefaultMenuItem();
		div.setValue(" | ");
		modelo.getElements().add(div);
		
		DefaultMenuItem item = new DefaultMenuItem();
		item.setCommand("#{loginBean.sair}");
		item.setValue("Sair");
		item.setIcon("pi pi-fw pi-power-off");
		modelo.getElements().add(item);
		
		
		

		return modelo;
	}
	
	private DefaultSubMenu montarSubMenu(NodeList menuitens, DefaultSubMenu novoSubMenu, Usuario user) {

		for (int j = 0; j < menuitens.getLength(); j++) {

			Element menuItem = (Element) menuitens.item(j);

			DefaultMenuItem novoMenuItem = new DefaultMenuItem();
			novoMenuItem.setValue(menuItem.getAttribute("value"));			

			if (!menuItem.getAttribute("outcome").equals(""))
				novoMenuItem.setOutcome(menuItem.getAttribute("outcome"));

			// verifica se não restrição ao link
			if (!temRestricao(user.getRole(), menuItem)) {
				
				novoSubMenu.getElements().add(novoMenuItem);				
			}
		}

		return novoSubMenu;
	}

	private DefaultSubMenu montarSubMenu2(NodeList submenus2, DefaultSubMenu novoSubMenu, Usuario user) {

		for (int i = 0; i < submenus2.getLength(); i++) {

			// pega um subMenu2
			Element subMenu = (Element) submenus2.item(i);

			DefaultSubMenu novoSubMenu2 = DefaultSubMenu.builder().label(subMenu.getAttribute("label")).build();
			//DefaultSubMenu novoSubMenu2 = new DefaultSubMenu(subMenu.getAttribute("label"));

			// pega os menuItens do subMenu2
			NodeList menuItens = subMenu.getElementsByTagName("menuitem2");
			novoSubMenu2 = montarSubMenu(menuItens, novoSubMenu2, user);

			novoSubMenu.getElements().add(novoSubMenu2);
			//novoSubMenu.addElement(novoSubMenu2);
		}

		return novoSubMenu;
	}

	private DefaultSubMenu addMenuItem(DefaultSubMenu subMenu, String value, String command) {
		DefaultMenuItem item = new DefaultMenuItem();
		item.setValue(value);
		if (command != null)
			item.setCommand(command);
		subMenu.getElements().add(item);
		//subMenu.addElement(item);
		return subMenu;
	}

	private static boolean temPermissao(Role role, Element elemento) {
		NodeList filhos = elemento.getElementsByTagName("role");
		boolean temPermissao = false;

		for (int j = 0; j < filhos.getLength(); j++) {
			Element grupoSubmenu = (Element) filhos.item(j);

			log.debug("item submenu: "+grupoSubmenu.getAttribute("value"));
				
			if (grupoSubmenu.getAttribute("value").equals(role.name())
					|| (grupoSubmenu.getAttribute("value").equals("TECNICO")
							&& role.name().equals("GESTOR"))) {
				temPermissao = true;
				log.debug("permissao: "+temPermissao);
				break;
			}
		}
		return temPermissao;
	}

	private static boolean temRestricao(Role role, Element elemento) {
		NodeList filhos = elemento.getElementsByTagName("role");
		boolean temRestricao = false;

		for (int j = 0; j < filhos.getLength(); j++) {
			Element grupoSubmenu = (Element) filhos.item(j);

			if (grupoSubmenu.getAttribute("value").equals(role.name())) {
				temRestricao = true;
				break;
			}
		}
		return temRestricao;
	}

	public ExternalContext getExternalContext() {
		return externalContext;
	}

	public void setExternalContext(ExternalContext externalContext, Usuario usuario)
			throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException {
		
		InputStream inStream;
		
		/*
		 * Carrega o xml do menu
		 */
		
		inStream = externalContext.getResourceAsStream("/WEB-INF/template/Menu.xml");

		
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = dBuilder.parse(inStream);
		inStream.close();
	}	
}
