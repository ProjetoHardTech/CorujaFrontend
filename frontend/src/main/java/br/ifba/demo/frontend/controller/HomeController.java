package br.ifba.demo.frontend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifba.demo.frontend.dto.ComentarioDTO;
import br.ifba.demo.frontend.dto.LoginRequest;
import br.ifba.demo.frontend.dto.PostForm;
import br.ifba.demo.frontend.dto.PostResponse;
import br.ifba.demo.frontend.model.UsuarioModel;
import br.ifba.demo.frontend.service.ArquivoService;
import br.ifba.demo.frontend.service.PostService;
import br.ifba.demo.frontend.service.UsuarioService;
import br.ifba.demo.frontend.util.UpdateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController{

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PostService postService;
	@Autowired
	private ArquivoService arquivoService;

	@GetMapping
    public ModelAndView index() {
		return new ModelAndView("index");
    }

    @GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}


	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		return new ModelAndView("index");
	}

	@GetMapping("/signup")
	public ModelAndView signup() {
		return new ModelAndView("signup");
	}

	@GetMapping("/erro")
	public ModelAndView erro() {
		return new ModelAndView("erro");
	}


	@GetMapping("/termosdeuso")
	public ModelAndView termosdeuso(){
		return new ModelAndView("termosdeuso");
	}

	@GetMapping("/esquecisenha")
	public ModelAndView esquecisenha() {
		return new ModelAndView("esquecisenha");
	}

	@GetMapping("/confirmaremail")
	public ModelAndView confirmaremail() {
		return new ModelAndView("confirmaremail");
	}

	@GetMapping("/emergencia")
    public ModelAndView emergencia(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Long iduser = null;
        try {
            iduser = Long.valueOf(session.getAttribute("id_usuario").toString());
            UsuarioModel usuario = usuarioService.getUsuario(iduser);
            mav.addObject("usuario", usuario);
            mav.addObject("currentPage", "emergencia");
            mav.setViewName("leftmenu/emergencia");
        } catch (NumberFormatException e) {
            mav.setViewName("redirect:/login");
            e.printStackTrace();
        }
        return mav;
    }

	@GetMapping("/home")
    public ModelAndView home(HttpServletRequest request, Model model) {
        List<PostResponse> list = postService.listall();
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Long iduser = null;
        try {
            iduser = Long.valueOf(
session.getAttribute("id_usuario").toString() );
            UsuarioModel usuario = usuarioService.getUsuario(iduser);
            mav.addObject("usuario", usuario);
            mav.addObject("posts", list);
            mav.addObject("currentPage", "home");
        } catch (NumberFormatException e) {
            mav.setViewName("redirect:/login");
            e.printStackTrace();
        }
        return mav;
    }

	@GetMapping("/comments")
	public ModelAndView comments(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("currentPage", "comments");
		modelAndView.setViewName("comments");
		return modelAndView;
		// return new ModelAndView("comments");
	}

	@GetMapping("/timeline")
	public ModelAndView timeline() {
		return new ModelAndView("timeline");
	}

	@GetMapping("/novo_post")
    public ModelAndView upload_page(HttpServletRequest request, Model model) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Long iduser = null;
        try {
            iduser = Long.valueOf(session.getAttribute("id_usuario").toString() );
            UsuarioModel usuario = usuarioService.getUsuario(iduser);
            mav.addObject("usuario", usuario);
            mav.setViewName("leftmenu/novo_post");
            mav.addObject("currentPage", "novo_post");
        } catch (NumberFormatException e) {
            mav.setViewName("redirect:/login");
            e.printStackTrace();
        }
        return mav;
    }




	@GetMapping("/configure-perf")
	public ModelAndView exibirPerfilAutomatico(HttpServletRequest request) {
    HttpSession session = request.getSession();
	ModelAndView modelAndView = new ModelAndView();
	Long iduser = (Long)session.getAttribute("id_usuario");
	modelAndView.addObject("currentPage", "configure-perf");
	modelAndView.setViewName("leftmenu/configure-perf");
    UsuarioModel usuario = usuarioService.getUsuario(iduser);
    modelAndView.addObject("usuario", usuario);
    return modelAndView;
}

	@GetMapping("/profile")
	public ModelAndView perfilPessoal(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long iduser = (Long)session.getAttribute("id_usuario");	
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("currentPage", "profile");
		modelAndView.setViewName("leftmenu/profile");
		UsuarioModel usuario = usuarioService.getUsuario(iduser);
		modelAndView.addObject("usuario", usuario);
		return modelAndView;
	}

	@PostMapping("/update")
	public ModelAndView atualizarUsuario(@ModelAttribute UsuarioModel usuario, @RequestParam("file") MultipartFile imagem, HttpServletRequest request) {
	ModelAndView mav = new ModelAndView();
	HttpSession session = request.getSession();
	Long iduser = (Long)session.getAttribute("id_usuario");
	usuario.setId(iduser);
	try {
			if(UpdateUtil.enviarImagem(imagem)){
				usuario.setImagem_usuario(imagem.getOriginalFilename());
			}
	mav.addObject("usuario", usuario);
	usuarioService.update(usuario);
		}catch (Exception e) {
			
			System.out.println("erro ao salvar" + e.getMessage());

		}

	mav.setViewName("redirect:/profile");
	return mav;	

}


	// MAPEAMENTOS PARA A TELA DE CONFIGURACOES
	@GetMapping("/config")
	public ModelAndView config_page(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		Long iduser = Long.valueOf(session.getAttribute("id_usuario").toString());
		UsuarioModel usuario = usuarioService.getUsuario(iduser);
    	modelAndView.addObject("usuario", usuario);
		modelAndView.addObject("currentPage", "config");
		modelAndView.setViewName("leftmenu/config");
		return modelAndView;
	}

	@GetMapping("/config_my_account")
	public ModelAndView config_my_account_page(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		Long iduser = Long.valueOf(session.getAttribute("id_usuario").toString());
		UsuarioModel usuario = usuarioService.getUsuario(iduser);
    	modelAndView.addObject("usuario", usuario);
		modelAndView.addObject("currentPage", "config_my_account");
		modelAndView.setViewName("leftmenu/config_menu/config_my_account");
		return modelAndView;
	}

	@GetMapping("/config_notifications")
	public ModelAndView config_notifications_page(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		Long iduser = Long.valueOf(session.getAttribute("id_usuario").toString());
		UsuarioModel usuario = usuarioService.getUsuario(iduser);
    	modelAndView.addObject("usuario", usuario);
		modelAndView.addObject("currentPage", "config_notifications");
		modelAndView.setViewName("leftmenu/config_menu/config_notifications");
		return modelAndView;
	}

	@GetMapping("/config_security")
	public ModelAndView config_security_page(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		Long iduser = Long.valueOf(session.getAttribute("id_usuario").toString());
		UsuarioModel usuario = usuarioService.getUsuario(iduser);
    	modelAndView.addObject("usuario", usuario);
		modelAndView.addObject("currentPage", "config_security");
		modelAndView.setViewName("leftmenu/config_menu/config_security");
		return modelAndView;
	}

	@GetMapping("/config_privacy")
	public ModelAndView config_privacy_page(HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		Long iduser = Long.valueOf(session.getAttribute("id_usuario").toString());
		UsuarioModel usuario = usuarioService.getUsuario(iduser);
    	modelAndView.addObject("usuario", usuario);
		modelAndView.addObject("currentPage", "config_privacy");
		modelAndView.setViewName("leftmenu/config_menu/config_privacy");
		return modelAndView;
	}


	@PostMapping("/upload")
	public ModelAndView upload(
		@ModelAttribute PostForm postForm,
		HttpServletRequest request,
		RedirectAttributes redirectAttributes)
	{
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		Long id_usuario = (Long)session.getAttribute("id_usuario");
		System.out.println(id_usuario);
		postForm.setId_usuario(id_usuario);
		System.out.println("postForm: ");
		System.out.println(postForm);

		Boolean retorno = postService.add(postForm);
		if ( retorno ) {
			System.out.println("Post CRIANDO");
		}
		else{
			System.out.println("Post nao CRIANDO");
		}

		mav.setViewName("redirect:/home");
		return mav;
	}

	@PostMapping("/process_login")
	public ModelAndView process_login(HttpServletRequest request, Model model) {
		String usuario = request.getParameter( "usuario" );
		String senha   = request.getParameter( "senha" );

		LoginRequest loginRequest = new LoginRequest(usuario, senha);
		UsuarioModel uModel = usuarioService.login(loginRequest);

		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
        if (uModel != null) {
			session.setAttribute("id_usuario", uModel.getId());
			session.setAttribute("email", uModel.getEmail());
			session.setAttribute("nome" , uModel.getNome());
			session.setAttribute("perfil" , uModel.getId_perfil());
			// mav.setViewName("home");
			mav.setViewName("redirect:/home");
		}
		else {
			mav.addObject("possui_erro", true);
			mav.addObject("mensagem", "Usuário ou Senha Invalida");
			mav.setViewName("login");
		}
		return mav;
	}

	@PostMapping("/process_add")
	public ModelAndView process_add(HttpServletRequest request, Model model) {
		String nome = request.getParameter( "nome" );
		String sobrenome = request.getParameter( "sobrenome" );
		String email   = request.getParameter( "email" );
		String senha   = request.getParameter( "senha" );
		String confirmacao_senha   = request.getParameter( "confirmacao_senha" );
		String agree   = request.getParameter( "agree" );
		boolean termos = agree == "1" ? true : false;

		System.out.println("nome.............: " + nome);
		System.out.println("sobrenome........: " + sobrenome);
		System.out.println("email............: " + email);
		System.out.println("agree............: " + agree);
		System.out.println("senha............: " + senha);
		System.out.println("confirmacao_senha: " + confirmacao_senha);

		ModelAndView mav = new ModelAndView();

		if (agree == null){
			// model.addAttribute( "mensagem", "Termos e condições é de preenchimento obrigatório!");
			// model.addAttribute( "possui_erro", true);
			// return "signup";
			mav.addObject("mensagem", "Termos e condições é de preenchimento obrigatório!");
			mav.addObject("possui_erro", true);
			mav.setViewName("signup");
			return mav;
		}

		UsuarioModel usuModel = new UsuarioModel();
		usuModel.setNome(nome);
		usuModel.setSobrenome(sobrenome);
		usuModel.setEmail(email);
		usuModel.setSenha(senha);
		usuModel.setTermos_aceite(termos);
		usuModel.setId_perfil(Long.valueOf(2));

		Boolean resutado = usuarioService.insert( usuModel );

		if (resutado){
			mav.setViewName("login");
			return mav;
		}
		else {
			// model.addAttribute( "mensagem", "Erro ao cadastrar o usuário.");
			// model.addAttribute( "possui_erro", true);
			// return "signup";

			mav.addObject("mensagem", "Erro ao cadastrar o usuário.");
			mav.addObject("possui_erro", true);
			mav.setViewName("signup");
			return mav;
		}

	}

	@GetMapping("/exibirImagem/{id}")
	public String  exibir_imagem(@PathVariable String id) {
		System.out.println("exibir_imagem: " + id);
		// return arquivoService.get_imagem(id);
		return arquivoService.get_url_file("teste.png");
	}

	@PostMapping("/add_comentario")
	public ModelAndView add_comentario(HttpServletRequest request, Model model) {
		String comentario = request.getParameter( "comentario" );
		String id_post    = request.getParameter( "id_post" );

		System.out.println("comentario: " + comentario);
		System.out.println("id_post...: " + id_post);
		// System.out.println("tags......: " + tags);

		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		if ( session == null ){
			mav.addObject("message", "Efetue o Login Novamente");
        	mav.setViewName("login");
			return mav;
		}

		Long id_usuario = (Long)session.getAttribute("id_usuario");
		if ( id_usuario == null ){
			mav.addObject("message", "Efetue o Login Novamente");
        	mav.setViewName("login");
			return mav;
		}
		System.out.println("id_usuario...: " + id_usuario);

		ComentarioDTO comentarioDTO = new ComentarioDTO();
		comentarioDTO.setComentario(comentario);
		comentarioDTO.setId_usuario(id_usuario);
		comentarioDTO.setId_post( Long.parseLong(id_post) );

		if ( !postService.add_comentario(comentarioDTO) ) {
			System.out.println("Erro no cadastro do comentarios");
		}
		mav.setViewName("redirect:/home");
		return mav;
	}
}