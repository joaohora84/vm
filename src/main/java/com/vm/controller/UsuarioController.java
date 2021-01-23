package com.vm.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.tomcat.jni.Thread;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vm.model.Usuario;
import com.vm.repository.RoleRepository;
import com.vm.repository.UsuarioRepository;
import com.vm.service.ObjetoEnviaEmail;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastrousuario")
	public ModelAndView inicio() {

		ModelAndView model = new ModelAndView("cadastrousuario");

		model.addObject("usuarioobj", new Usuario());
		model.addObject("roles", roleRepository.findAll());

		return model;

	}


	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarusuario")
	public ModelAndView salvar(Usuario usuario) throws PSQLException {
		
	
		ModelAndView model = new ModelAndView("/cadastrousuario");
		

		try {			
			
			String senha = gerarSenha();
			
			

			String senhacriptografada = new BCryptPasswordEncoder().encode(senha);

			usuario.setLogin(usuario.getEmail());

			usuario.setSenha(senhacriptografada);

			Date data = new Date(System.currentTimeMillis());

			usuario.setData_alteracao(data);

			if (usuario.getData_cadastro() == null) {

				usuario.setData_cadastro(data);

				usuarioRepository.save(usuario);

			} else {

				usuarioRepository.save(usuario);

			}

			usuarioRepository.save(usuario);
			
			new java.lang.Thread(() -> {
				
				ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail(usuario.getEmail(), "Farmácia Biovida",
						"Seu acesso ao sistema",
						"Olá, estes são os dados para acesso ao sistema." +
						"\n" +
								"\n" + 
						"Login: " + usuario.getLogin()
								+ "\n" +
						"Senha: " + senha + "\n" + "Acesse: magistralpersonal.com");
				
				
			  try {
				enviaEmail.enviarEmail();
			} catch (UnsupportedEncodingException | MessagingException e) {
				
				e.printStackTrace();
			}	
				
			}).start();
			

			String msgSucesso = "Usuário salvo com sucesso!" +"\n" 
					+ "Login: " + usuario.getLogin() + "\n"
					+ "Senha: " + senha;

			model.addObject("usuarioobj", usuario);
			model.addObject("msgSucesso", msgSucesso);
			model.addObject("roles", roleRepository.findAll());
			

			return model;
			
			
			

		} catch (Exception e) {

			model.addObject("usuarioobj", new Usuario());
			model.addObject("msgErro", e.getCause().getCause().getMessage());
			model.addObject("roles", roleRepository.findAll());

			return model;

		}
		
		

	}

	@GetMapping("/editarusuario/{iduser}")
	public ModelAndView editar(@PathVariable("iduser") Long iduser) {

		ModelAndView model = new ModelAndView("/cadastrousuario");
		Optional<Usuario> usuario = usuarioRepository.findById(iduser);
		model.addObject("usuarioobj", usuario.get());
		model.addObject("roles", roleRepository.findAll());
		

		return model;

	}

	@GetMapping("/removerusuario/{iduser}")
	public ModelAndView excluir(@PathVariable("iduser") Long iduser) {

		usuarioRepository.deleteById(iduser);
		
		String msgExclusao = "Usuário excluido com sucesso!";
		
		ModelAndView model = new ModelAndView("/listausuario");
		model.addObject("usuarios", usuarioRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		model.addObject("msgExclusao", msgExclusao);
		model.addObject("usuarioobj", new Usuario());
		
		
		

		return model;

	}

	@PostMapping("**/pesquisarusuario")
	public ModelAndView pesquisar(@RequestParam("nome") String nome,
			@PageableDefault(size = 5, sort = { "nome" }) Pageable pageable) {

		Page<Usuario> usuarios = null;

		usuarios = usuarioRepository.findUsuarioByNamePage(nome, pageable);
		
	    

		ModelAndView model = new ModelAndView("/listausuario");
		model.addObject("usuarios", usuarios);
		model.addObject("usuarioobj", new Usuario());

		return model;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarusuarios")
	public ModelAndView usuarios() {

		ModelAndView model = new ModelAndView("/listausuario");
		model.addObject("usuarios", usuarioRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		
		return model;

	}

	@GetMapping("/usuariopage")
	public ModelAndView carregaUsuarioPorPaginacao(@PageableDefault(size = 5) Pageable pageable, ModelAndView model) {

		Page<Usuario> pageUsuario = usuarioRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("nome")));
		model.addObject("usuarios", pageUsuario);
		model.setViewName("/listausuario");

		return model;

	}
	
	@GetMapping("/enviaremail/{iduser}")
	public ModelAndView enviarEmail(@PathVariable("iduser") Long iduser) throws UnsupportedEncodingException, MessagingException {
		
		ModelAndView model = new ModelAndView("/listausuario");
		
		Optional<Usuario> usuario = usuarioRepository.findById(iduser);
		
				
		
		ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("contato@magistralpersonal.com",
				"Farmácia Biovida",
				"Seu acesso ao sistema",
				"Login: " + usuario.get().getLogin() + "\n" + "Senha: " + usuario.get().getSenha());
		
		enviaEmail.enviarEmail();
		
		model.addObject("msgExclusao", "Email enviado com sucesso!");
		model.addObject("usuarios", usuarioRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		return model;
		
	}
	
	public String gerarSenha() {
		
		String[] carct ={"0","1","2","3","4","5","6","7","8","9",
				"a","b","c","d","e","f","g","h","i","j","k","l","m","n",
				"o","p","q","r","s","t","u","v","w","x","y","z"};
		
		String senha = "";
		
		for(int x=0; x < 6; x++) {
			int j = (int) (Math.random()*carct.length);
			senha += carct[j];
		}
		
		
		return senha;
	}
	
	
		
		
	
	

}
