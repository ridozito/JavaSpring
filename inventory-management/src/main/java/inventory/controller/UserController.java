package inventory.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import inventory.model.Category;
import inventory.model.Paging;
import inventory.model.Role;
import inventory.model.UserRole;
import inventory.model.Users;
import inventory.model.Users;
import inventory.service.ProductService;
import inventory.service.RoleService;
import inventory.service.UserService;
import inventory.util.Constant;
import inventory.validate.UserValidator;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserValidator userValidator;
	static final Logger log = Logger.getLogger(UserController.class);
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if(binder.getTarget()==null) {
			return;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if(binder.getTarget().getClass()== Users.class) {
			binder.setValidator(userValidator);
		}
	}
	@RequestMapping(value= {"/user/list","/user/list/"})
	
	public String redirect() {
		return "redirect:/user/list/1";
	}
	
	@RequestMapping(value="/user/list/{page}")
	public String showUsersList(Model model,HttpSession session , @ModelAttribute("searchForm") Users user,@PathVariable("page") int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Users> users = userService.getUsersList(user,paging);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo", paging);
		model.addAttribute("users", users);
		return "user-list";
		
	}
	@GetMapping("/user/add")
	public String add(Model model) {
		model.addAttribute("titlePage", "Add Users");
		model.addAttribute("modelForm", new Users());
		List<Role> roles = roleService.getRoleList(null, null);
		Map<String, String> mapRole = new HashMap<>();
		for(Role role : roles) {
			mapRole.put(String.valueOf(role.getId()), role.getRoleName());
		}
		model.addAttribute("mapRole", mapRole); 
		model.addAttribute("viewOnly", false);
		return "user-action";
	}
	@GetMapping("/user/edit/{id}")
	public String edit(Model model , @PathVariable("id") int id) {
		log.info("Edit user with id="+id);
		List<Users> results = userService.findByProperty("id", id);
		if(results!=null &&  !results.isEmpty()) {
			Users user = results.get(0);
			List<Role> roles = roleService.getRoleList(null, null);
			Map<String, String> mapRole = new HashMap<>();
			for(Role role : roles) {
				mapRole.put(String.valueOf(role.getId()), role.getRoleName());
			}
			UserRole userRole =(UserRole) user.getUserRoles().iterator().next();
			user.setRoleID(userRole.getRole().getId());
			model.addAttribute("mapRole", mapRole);
			model.addAttribute("titlePage", "Edit Users");
			model.addAttribute("modelForm", user);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editMode", true);
			return "user-action";
		}
		return "redirect:/user/list";
	}
	@GetMapping("/user/view/{id}")
	public String view(Model model , @PathVariable("id") int id) {
		log.info("View user with id="+id);
		List<Users> results = userService.findByProperty("id", id);
		if(results!=null &&  !results.isEmpty()) {
			Users user = results.get(0);
			List<Role> roles = roleService.getRoleList(null, null);
			Map<String, String> mapRole = new HashMap<>();
			for(Role role : roles) {
				mapRole.put(String.valueOf(role.getId()), role.getRoleName());
			}
			model.addAttribute("mapRole", mapRole);
			model.addAttribute("titlePage", "View Users");
			model.addAttribute("modelForm", user);
			model.addAttribute("viewOnly", true);
			model.addAttribute("editMode", true);
			return "user-action";
		}
		return "redirect:/user/list";
	}
	@PostMapping("/user/save")
	public String save(Model model,@ModelAttribute("modelForm") @Validated Users user,BindingResult result,HttpSession session) {
		if(result.hasErrors()) {
			if(user.getId()!=null) {
				model.addAttribute("titlePage", "Edit Users");
				model.addAttribute("editMode", true);
			}else {
				model.addAttribute("titlePage", "Add Users");
			}
			List<Role> roles = roleService.getRoleList(null, null);
			Map<String, String> mapRole = new HashMap<>();
			for(Role role : roles) {
				mapRole.put(String.valueOf(role.getId()), role.getRoleName());
			}
			model.addAttribute("mapRole", mapRole);
			model.addAttribute("modelForm", user);
			model.addAttribute("viewOnly", false);
			return "user-action";
			
		}
		
	//	UserRole userRole =(UserRole) user.getUserRoles().iterator().next();
		if(user.getId()!=null && user.getId()!=0) {
			try {
				userService.update(user);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
			} catch (Exception e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Update has error");
			}
			
		}else {
				try {
					userService.save(user);
					session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
				}
		}
		return "redirect:/user/list";
		
	}
	@GetMapping("/user/delete/{id}")
	public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
		log.info("Delete user with id="+id);
		List<Users> results = userService.findByProperty("id", id);
		if(results!=null &&  !results.isEmpty()) {
			Users user = results.get(0);
			try {
				userService.deleteUser(user);
				session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
			}
		}
		return "redirect:/user/list";
	}
}
