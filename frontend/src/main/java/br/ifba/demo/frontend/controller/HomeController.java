package br.ifba.demo.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController{

	 @GetMapping
    public String index() {
        return "index";
    }
    @GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

    @GetMapping("/login")
	public String login() {
		return "login";
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

}