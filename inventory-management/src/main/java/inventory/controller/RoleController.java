package inventory.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import inventory.model.Role;
import inventory.model.Paging;
import inventory.service.RoleService;
import inventory.util.Constant;
import inventory.validate.RoleValidator;

@Controller
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleValidator roleValidator;
	static final Logger log = Logger.getLogger(RoleController.class);
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if(binder.getTarget()==null) {
			return;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if(binder.getTarget().getClass()== Role.class) {
			binder.setValidator(roleValidator);
		}
	}
	@RequestMapping(value= {"/role/list","/role/list/"})
	
	public String redirect() {
		return "redirect:/role/list/1";
	}
	
	@RequestMapping(value="/role/list/{page}")
	public String showRoleList(Model model,HttpSession session , @ModelAttribute("searchForm") Role role,@PathVariable("page") int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Role> roles = roleService.getRoleList(role,paging);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo", paging);
		model.addAttribute("roles", roles);
		return "role-list";
		
	}
	@GetMapping("/role/add")
	public String add(Model model) {
		model.addAttribute("titlePage", "Add Role");
		model.addAttribute("modelForm", new Role());
		model.addAttribute("viewOnly", false);
		return "role-action";
	}
	@GetMapping("/role/edit/{id}")
	public String edit(Model model , @PathVariable("id") int id) {
		log.info("Edit role with id="+id);
		Role role = roleService.findByIdRole(id);
		if(role!=null) {
			model.addAttribute("titlePage", "Edit Role");
			model.addAttribute("modelForm", role);
			model.addAttribute("viewOnly", false);
			return "role-action";
		}
		return "redirect:/role/list";
	}
	@GetMapping("/role/view/{id}")
	public String view(Model model , @PathVariable("id") int id) {
		log.info("View role with id="+id);
		Role role = roleService.findByIdRole(id);
		if(role!=null) {
			model.addAttribute("titlePage", "View Role");
			model.addAttribute("modelForm", role);
			model.addAttribute("viewOnly", true);
			return "role-action";
		}
		return "redirect:/role/list";
	}
	@PostMapping("/role/save")
	public String save(Model model,@ModelAttribute("modelForm") @Validated Role role,BindingResult result,HttpSession session) {
		if(result.hasErrors()) {
			if(role.getId()!=null) {
				model.addAttribute("titlePage", "Edit Role");
			}else {
				model.addAttribute("titlePage", "Add Role");
			}
			
			model.addAttribute("modelForm", role);
			model.addAttribute("viewOnly", false);
			return "role-action";
			
		}
		if(role.getId()!=null && role.getId()!=0) {
			try {
				roleService.updateRole(role);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Update has error");
			}
			
		}else {
				try {
					roleService.saveRole(role);
					session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
				}
		}
		return "redirect:/role/list";
		
	}
	@GetMapping("/role/delete/{id}")
	public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
		log.info("Delete role with id="+id);
		Role role = roleService.findByIdRole(id);
		if(role!=null) {
			try {
				roleService.deleteRole(role);
				session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
			}
		}
		return "redirect:/role/list";
	}
	
}
