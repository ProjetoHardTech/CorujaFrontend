package br.ifba.demo.frontend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String index() {
        return "index";
    }
    @GetMapping("/dashboard")
	public String dashboard(HttpServletRequest request, Model model) {
		// HttpSession session = request.getSession();
		List<PostResponse> list = postService.listall();
		model.addAttribute( "posts", list);
		model.addAttribute( "currentPage", "dashboard");
		return "dashboard";
	}

    @GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@GetMapping("/erro")
	public String erro() {
		return "erro";
	}
	
	@GetMapping("/termosdeuso")
	public String termosdeuso(){
		return "termosdeuso";
	}

	@GetMapping("/esquecisenha")
	public String esquecisenha() {
		return "esquecisenha";
	}
	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/timeline")
	public String timeline() {
		return "timeline";
	}

	@GetMapping("/novo_post")
	public ModelAndView upload_page(Model model) {
		// model.addAttribute( "currentPage", "novo_post");
		// return "/post/novo_post";

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("currentPage", "novo_post");
		modelAndView.setViewName("/post/novo_post");
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
		// System.out.println(id_usuario);
		postForm.setId_usuario(id_usuario);
		System.out.println(postForm);

		Boolean retorno = postService.add(postForm);
		if ( retorno ) {
			System.out.println("Post CRIANDO");
		}
		else{
			System.out.println("Post nao CRIANDO");
		}

		mav.setViewName("redirect:/dashboard");
		return mav;
	}

	@PostMapping("/process_login")
	public String process_login(HttpServletRequest request, Model model) {
		String usuario = request.getParameter( "usuario" );
		String senha   = request.getParameter( "senha" );
		
		LoginRequest loginRequest = new LoginRequest(usuario, senha);
		UsuarioModel uModel = usuarioService.login(loginRequest);
		
		HttpSession session = request.getSession();
		if (uModel != null) {
			session.setAttribute("id_usuario", uModel.getId());
			session.setAttribute("email", uModel.getEmail());
			session.setAttribute("nome" , uModel.getNome());
			session.setAttribute("perfil" , uModel.getId_perfil());
			model.addAttribute( "nome", uModel.getNome());
			return "redirect:/dashboard";
		}
		else {
			model.addAttribute( "possui_erro", true);
			model.addAttribute( "mensagem", "Usuário ou Senha Invalida");
			return "login";
		}
	}

	@PostMapping("/process_add")
	public String process_add(HttpServletRequest request, Model model) {
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
		System.out.println("agree...........: " + agree);
		System.out.println("senha............: " + senha);
		System.out.println("confirmacao_senha: " + confirmacao_senha);

		if (agree == null){
			model.addAttribute( "mensagem", "Termos e condições é de preenchimento obrigatório!");
			model.addAttribute( "possui_erro", true);
			return "signup";
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
			return "login";	
		}
		else {
			model.addAttribute( "mensagem", "Erro ao cadastrar o usuário.");
			model.addAttribute( "possui_erro", true);
			return "signup";
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

		// System.out.println("comentario: " + comentario);
		// System.out.println("id_post...: " + id_post);
		// System.out.println("tags......: " + tags);

		ModelAndView mav = new ModelAndView();
        
		HttpSession session = request.getSession();
		if ( session == null ){
			mav.addObject("message", "Efetue o Login Novamente");
        	mav.setViewName("login");
			return mav;
		}

		Long id_usuario = (Long)session.getAttribute("id_usuario");
		
		ComentarioDTO comentarioDTO = new ComentarioDTO();
		comentarioDTO.setComentario(comentario);
		comentarioDTO.setId_usuario(id_usuario);
		comentarioDTO.setId_post( Long.parseLong(id_post) );

		if ( !postService.add_comentario(comentarioDTO) ) {
			System.out.println("Erro no cadastro do comentarios");
		}
		mav.setViewName("redirect:/dashboard");
		return mav;
	}
}