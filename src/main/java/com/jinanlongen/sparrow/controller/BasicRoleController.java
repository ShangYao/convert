package com.jinanlongen.sparrow.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jinanlongen.sparrow.domain.Privilege;
import com.jinanlongen.sparrow.domain.Role;
import com.jinanlongen.sparrow.repository.PrivilegeRep;
import com.jinanlongen.sparrow.repository.RoleRep;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月26日
 */
@Controller
@RequestMapping("/basic/role/")
public class BasicRoleController {
	private static final String role_html_path = "basic/role/";
	@Autowired
	private RoleRep roleRep;
	@Autowired
	private PrivilegeRep pRep;

	@RequestMapping("list")
	public String list(Model model) {
		model.addAttribute("roleList", roleRep.findAll());
		return role_html_path + "list";
	}

	@RequestMapping("toAdd")
	public String toAdd(Model model) {
		model.addAttribute("role", new Role());
		return role_html_path + "add";
	}

	@RequestMapping("toModify")
	public String modify(Long id, Model model) {
		model.addAttribute("role", roleRep.findOne(id));
		return role_html_path + "add";
	}

	@RequestMapping("add")
	public String addRole(Role role, RedirectAttributes attr, Model model) {

		Set<Privilege> setP = new HashSet<Privilege>();
		if (null != role.getPriIdString() && !"".equals(role.getPriIdString())) {
			String[] idAttr = role.getPriIdString().split(",");
			for (String id : idAttr) {
				setP.add(pRep.findOne(Long.parseLong(id)));
			}
		}
		role.setPrivilegeList(setP);
		int count = 0;
		if (0 != role.getId()) {
			count = roleRep.countName(role.getName(), role.getId());
		} else {

			count = roleRep.countRoleName(role.getName());
		}
		if (0 != count) {
			role.setAlertMessage("请更改名称，名称已存在！");
			model.addAttribute("role", role);
			return role_html_path + "add";
		}

		roleRep.save(role);
		attr.addFlashAttribute("message", "角色添加成功！！");
		return "redirect:list";

	}

	@RequestMapping("delete")
	public String delete(long id, RedirectAttributes attr) {
		int count = roleRep.findUserRole(id);
		if (count > 0) {
			attr.addFlashAttribute("message", "角色已被用户使用，无法删除！！");
			return "redirect:list";
		}
		roleRep.delete(id);
		attr.addFlashAttribute("message", "角色已被删除！！");
		return "redirect:list";
	}

	@RequestMapping("rolePrivileges")
	@ResponseBody
	public List<Privilege> rolePrivileges(long id) {
		List<Privilege> pList = pRep.findAll();
		if (0 == id) {
			return pList;
		} else {
			Role role = roleRep.findOne(id);
			Set<Long> set = role.getPrivilegeList().stream().map(i -> i.getId()).collect(Collectors.toSet());
			for (Privilege p : pList) {
				if (set.contains(p.getId())) {
					p.setChecked(true);
				}
			}
		}
		return pList;
	}
}
