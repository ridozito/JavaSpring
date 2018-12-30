package inventory.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import inventory.model.Users;
import inventory.service.UserService;
import inventory.util.Constant;
import inventory.validate.LoginValidator;

@Controller
public class LoginController {
	@Autowired 
	private UserService userService;
	@Autowired
	private LoginValidator loginValidator;
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if(binder.getTarget()==null) return;
		if(binder.getTarget().getClass() == Users.class) {
			binder.setValidator(loginValidator);
		}
	}
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginForm", new Users());
		return "login/login";
	}
	@PostMapping("/processLogin")
	public String processLogin(Model model , @ModelAttribute("loginForm")@Validated Users users , BindingResult result , HttpSession session) {
		if(result.hasErrors()) {
			return "login/login";
		}
		Users user  = userService.findByProperty("userName", users.getUserName()).get(0);
		session.setAttribute(Constant.USER_INFO, user);
		return "redirect:/index";
	}

}
