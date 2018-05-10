package com.jinanlongen.sparrow.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jinanlongen.sparrow.domain.Group;
import com.jinanlongen.sparrow.domain.User;
import com.jinanlongen.sparrow.repository.GroupRep;
import com.jinanlongen.sparrow.repository.UserRep;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月26日
 */
@Controller
@RequestMapping("/basic/group/")
public class BasicGroupController {
	private static final String group_html_path = "basic/group/";
	private Logger logger = LoggerFactory.getLogger(BasicGroupController.class);

	@Autowired
	private GroupRep groupRep;
	@Autowired
	private UserRep userRep;

	@RequestMapping("list")
	public String list() {
		return group_html_path + "list";
	}

	@RequestMapping("toAdd")
	public String toAdd(String parentId, Model model) {
		logger.info("添加分组，上级小组id" + parentId);
		Group group = new Group();
		group.setParentId(parentId);
		model.addAttribute("group", group);
		return group_html_path + "add";
	}

	@RequestMapping("add")
	public String add(Group group, Model model) {
		int count = groupRep.countGroupName(group.getName());
		if (0 != count) {
			group.setAlertMessage("请更换小组名称，小组名称已存在!");
			model.addAttribute("group", group);
			return group_html_path + "add";
		}
		if ("".equals(group.getParentId())) {
			group.setParentId(null);
			group.setLevel(1);
		} else {
			Group parentGroup = groupRep.findOne(Long.parseLong(group.getParentId()));
			group.setLevel(parentGroup.getLevel() + 1);
		}
		groupRep.save(group);
		return "redirect:list";
	}

	@RequestMapping("modify")
	public String modify(Group group, Model model) {
		int count = groupRep.countGroupName2(group.getName(), group.getId());
		if (0 != count) {
			group.setAlertMessage("请更换小组名称，小组名称已存在!");
			model.addAttribute("group", group);
			return group_html_path + "modify";
		}
		if ("".equals(group.getParentId())) {
			group.setParentId(null);
			group.setLevel(1);
		}
		groupRep.save(group);
		return "redirect:list";
	}

	@RequestMapping("delete")
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public String delete(Long id, RedirectAttributes attr) {
		int count = 0;
		count = groupRep.findGroupUser(id);
		List<Group> list = groupRep.findByParentId(id + "");
		if (null != list && list.size() != 0) {
			for (Group group : list) {
				count += groupRep.findGroupUser(group.getId());
				count += groupRep.findGroupStore(group.getId());
			}
		}
		if (count > 0) {
			attr.addFlashAttribute("message", "小组已被使用，无法删除！！");
			return "redirect:list";
		}
		groupRep.delete(id);
		groupRep.deleteByParentId(id + "");
		attr.addFlashAttribute("message", "小组已被删除！！");
		return "redirect:list";
	}

	@RequestMapping("toModify")
	public String toModify(Long id, Model model) {
		logger.info("修改分组，小组id" + id);
		Group group = groupRep.findOne(id);
		model.addAttribute("group", group);
		return group_html_path + "modify";
	}

	// @RequestMapping("modify")
	public String modify(Group group) {
		groupRep.save(group);
		return "redirect:list";
	}

	@RequestMapping("data")
	@ResponseBody
	public List<Group> data() {
		return groupRep.findAll();
	}

	@RequestMapping("userGroups")
	@ResponseBody
	public List<Group> userGroups(Long id) {
		User user = userRep.findOne(id);
		Set<Long> setid = user.getGroupList().stream().map(i -> i.getId()).collect(Collectors.toSet());
		List<Group> groupList = groupRep.findAll();
		for (Group group : groupList) {
			if (setid.contains(group.getId())) {
				group.setChecked(true);
			}
		}
		return groupList;
	}

	@RequestMapping("topGroup")
	@ResponseBody
	public List<Group> findByParentIdIsNull() {
		return groupRep.findByParentIdIsNull();
	}

	@RequestMapping("group")
	@ResponseBody
	public List<Group> findByParentId(String id) {
		return groupRep.findByParentId(id);
	}

}
