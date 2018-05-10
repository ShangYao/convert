document.writeln(" <nav class=\"navbar navbar-inverse navbar-static-top\"><div class=\"container-fluid\"><div class=\"container\"> ")
document.writeln(" <div class=\"navbar-header\"> ")
document.writeln(" <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#bs-navbar-collapse-1\" aria-expanded=\"false\"> ")
document.writeln(" <span class=\"sr-only\">Toggle navigation</span> ")
document.writeln("  <span class=\"icon-bar\"></span>")
document.writeln("  <span class=\"icon-bar\"></span>")
document.writeln(" <span class=\"icon-bar\"></span>")
document.writeln("  </button><a class=\"navbar-brand\" href=\"/merchandise/mine/index\">精编系统</a>")
document.writeln("  </div>")
document.writeln("  <div class=\"collapse navbar-collapse\" id=\"bs-navbar-collapse-1\">")
document.writeln("  <ul class=\"nav navbar-nav\"> <li class=\"dropdown\">")
document.writeln("   <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">精编管理<span class=\"caret\"></span></a>")
document.writeln("  <ul class=\"dropdown-menu\"><li><a href=\"/merchandise/mine/list\">我的精编</a></li><li><a href=\"/merchandise/maintain/list\">精编维护</a></li><li><a href=\"/merchandise/audit/audit\">精编审核</a></li><li><a href=\"/merchandise/all/all\">所有精编</a></li>")
document.writeln("   <li><a href=\"/merchandise/statistics/statistics\">精编统计</a></li></ul> </li></ul>")
//document.writeln(" <ul class=\"nav navbar-nav\"><li class=\"dropdown\"> ")
//document.writeln("   <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">告警管理<span class=\"caret\"></span></a>")
//document.writeln("   <ul class=\"dropdown-menu\"><li><a href=\"/alarm/list\">告警配置</a></li><li><a href=\"/alarm/report-list\">告警报告</a></li></ul></li></ul>")

document.writeln("   <ul class=\"nav navbar-nav\"> <li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">系统设置<span class=\"caret\"></span></a>")
document.writeln("   <ul class=\"dropdown-menu\"><li><a href=\"/basic/user/list\">用户管理</a></li><li><a href=\"/basic/store/list\">店铺管理</a></li><li><a href=\"/basic/group/list\">小组管理</a></li><li><a href=\"/basic/role/list\">角色管理</a></li></ul></li></ul>")
document.writeln("  <ul class=\"nav navbar-nav navbar-right\"><li><a href=\"/merchandise/mine/toAdd\">添加精编</a></li><li class=\"dropdown\">")
document.writeln("  <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\" id=\"cur_user\"><span class=\"caret\"></span></a>")
document.writeln("  <ul class=\"dropdown-menu\"><li><a href=\"/merchandise/mine/index\">个人中心</a></li>")
document.writeln(" <li role=\"separator\" class=\"divider\"></li><li><a href=\"/logout\">退出</a></li></ul></li></ul>")
document.writeln(" </div></div></nav>")
//<li><a href=\"#\" data-toggle=\"modal\" data-target=\"#updatePassword\">修改密码</a></li>

 $(function(){
  if($("#login_user").val()!="" ){
	  var a=$("#login_user").val();  	
  	$("#cur_user").html(a);
  }	
}); 



//