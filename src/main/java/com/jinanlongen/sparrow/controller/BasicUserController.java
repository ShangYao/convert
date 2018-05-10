package com.jinanlongen.sparrow.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jinanlongen.sparrow.domain.Group;
import com.jinanlongen.sparrow.domain.Role;
import com.jinanlongen.sparrow.domain.User;
import com.jinanlongen.sparrow.repository.GroupRep;
import com.jinanlongen.sparrow.repository.RoleRep;
import com.jinanlongen.sparrow.repository.UserRep;
import com.jinanlongen.sparrow.service.BasicGroupService;
import com.jinanlongen.sparrow.service.BasicUserService;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月26日
 */
@Controller
@RequestMapping("/basic/user/")
public class BasicUserController {
	private static final String user_html_path = "basic/user/";

	@Autowired
	private RoleRep roleRep;
	@Autowired
	private UserRep userRep;
	@Autowired
	private BasicUserService userService;
	@Autowired
	private GroupRep groupRep;
	@Autowired
	private BasicGroupService groupService;

	@RequestMapping("list")
	public String userList(Model model) {
		// List<Role> roleList = roleRep.findAll();
		// model.addAttribute("roleList", roleList);
		// model.addAttribute("user", new User());
		// model.addAttribute("topGroup", groupRep.findByParentIdIsNull());
		// Group group = new Group();
		// model.addAttribute("group1", group);
		// model.addAttribute("group2", group);
		// model.addAttribute("group3", group);
		// addrole();
		return "redirect:queryList";

	}

	@RequestMapping("queryList")
	public String queryList(Model model, User user, @RequestParam(value = "page", defaultValue = "0") Integer page) {
		user.setPage(page);
		model.addAttribute("user", userService.queryList(user));
		List<Role> roleList = roleRep.findAll();
		model.addAttribute("roleList", roleList);
		model.addAttribute("topGroup", groupRep.findByParentIdIsNull());
		model.addAttribute("group1", groupService.findGroupById(user.getGroup1()));
		model.addAttribute("group2", groupService.findGroupById(user.getGroup2()));
		model.addAttribute("group3", groupService.findGroupById(user.getGroup3()));
		return user_html_path + "list";

	}

	@RequestMapping("toModify")
	public String toModify(long id, Model model) {
		User user = userRep.findOne(id);
		Set<Long> setid = user.getRoleList().stream().map(i -> i.getId()).collect(Collectors.toSet());
		List<Role> roleList = roleRep.findAll();
		for (Role role : roleList) {
			if (setid.contains(role.getId())) {
				role.setSelected(true);
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("roleList", roleList);
		return user_html_path + "add";

	}

	@RequestMapping("add")
	public String addUser(User user, Model model) {
		Set<Group> groupList = new HashSet<Group>();
		Set<Role> roleList = new HashSet<Role>();
		if (null != user.getRoleId() && user.getRoleId().length != 0 && user.getRoleId()[0] != 0) {
			for (Long roleId : user.getRoleId()) {
				roleList.add(roleRep.findOne(roleId));
			}
		}
		if (null != user.getGroupIdString() && !"".equals(user.getGroupIdString())) {
			String[] idAttr = user.getGroupIdString().split(",");
			for (String id : idAttr) {
				groupList.add(groupRep.findOne(Long.parseLong(id)));
			}
		}
		user.setGroupList(groupList);
		user.setRoleList(roleList);
		user.setRoleNameString(roleList.stream().map(i -> i.getName()).collect(Collectors.joining(",")));
		userRep.save(user);

		return "redirect:list";

	}

	public void addrole() {
		Role role = roleRep.findOne(18l);
		List<User> list = userRep.findAll();
		Set<Role> roleList = null;
		for (User user : list) {
			roleList = user.getRoleList();
			roleList.add(role);
			user.setRoleList(roleList);
			user.setRoleNameString(user.getRoleIdString() + role.getName());
			userRep.save(user);
		}

	}
}
