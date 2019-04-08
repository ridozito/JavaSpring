package inventory.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import inventory.model.Auth;
import inventory.model.AuthForm;
import inventory.model.Category;
import inventory.model.Menu;
import inventory.model.Paging;
import inventory.model.Role;
import inventory.service.MenuService;
import inventory.service.RoleService;
import inventory.util.Constant;

@Controller
public class MenuController {
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;

	@GetMapping(value = { "/menu/list", "/menu/list/" })
	public String redirect() {
		return "redirect:/menu/list/1";
	}

	@RequestMapping("/menu/list/{page}")
	public String menuList(Model model, @PathVariable("page") int page, @ModelAttribute("searchForm") Menu menu,HttpSession session) {
		Paging paging = new Paging(15);
		paging.setIndexPage(page);
		List<Menu> menuList = menuService.getListMenu(paging, menu);
		List<Role> roles = roleService.getRoleList(null, null);
		Collections.sort(roles, (o1, o2) -> o1.getId() - o2.getId());
		for (Menu item : menuList) {
			Map<Integer, Integer> mapAuth = new TreeMap<>();
			for (Role role : roles) {
				mapAuth.put(role.getId(), 0);// 1-0 ,2-0,3-0
			}
			for (Object obj : item.getAuths()) {
				Auth auth = (Auth) obj;
				mapAuth.put(auth.getRole().getId(), auth.getPermission());
			}
			item.setMapAuth(mapAuth);
		}
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("menuList", menuList);
		model.addAttribute("roles", roles);
		model.addAttribute("pageInfo", paging);
		return "menu-list";
	}

	@GetMapping("/menu/change-status/{id}")
	public String change(Model model, @PathVariable("id") int id, HttpSession session) {
		try {
			menuService.changeStatus(id);
			session.setAttribute(Constant.MSG_SUCCESS, "Change status success!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Change status has error!!!");
		}
		return "redirect:/menu/list";
	}
	@GetMapping("/menu/permission")
	public String permission(Model model) {
		model.addAttribute("modelForm", new AuthForm());
		initSelectbox(model);
		return "menu-permission";
	}
	
	@PostMapping("/menu/update-permission")
	public String updatePermission(Model model,HttpSession session,@ModelAttribute("modelForm") AuthForm authForm ) {
		try {
			menuService.updatePermission(authForm.getRoleId(), authForm.getMenuId(), authForm.getPermission());
			session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Update has error!!!");
		}
		return "redirect:/menu/list";
	}
	private void initSelectbox(Model model) {
		List<Role> roles = roleService.getRoleList(null, null);
		List<Menu> menus = menuService.getListMenu(null, null);
		Map<Integer, String> mapRole = new HashMap<>();
		Map<Integer, String> mapMenu = new HashMap<>();
		for(Role role :roles) {
			mapRole.put(role.getId(), role.getRoleName());
		}
		for(Menu menu:menus) {
			mapMenu.put(menu.getId(), menu.getUrl());
		}
		model.addAttribute("mapRole", mapRole);
		model.addAttribute("mapMenu", mapMenu);
	}
}
