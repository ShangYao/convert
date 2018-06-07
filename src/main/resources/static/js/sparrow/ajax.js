function getGroup2(id) {
	var t = $("#group1").val();
	if (t == "") {
		return;
	}
	$.ajax({
		url : "/basic/group/group",
		type : "post",
		data : {
			id : id
		},
		cache : false,
		async : false,
		success : function(data) {
			//debugger
			$("#group2").html("");
			$("#group2").append("<option value=\"0\" >选择二级小组</option>");
			$.each(data, function(i, item) {
				$("#group2").append(
						"<option value="+item.id+">" + item.name
								+ "</option>");
			});
		}
	})
}

function getGroup3(id) {

	var t = $("#group2").val();
	if (t == "") {
		return;
	}

	$.ajax({
		url : "/basic/group/group",
		type : "post",
		data : {
			id : id
		},
		cache : false,
		async : false,
		success : function(data) {
			$("#group3").html("");
			$("#group3").append("<option value=\"0\">选择三级小组</option>");
			$.each(data, function(i, item) {
				$("#group3").append(
						"<option value="+item.id+">" + item.name
								+ "</option>");
			});

		}
	})
}

function getTaxon2(id) {
	var t = $("#taxon1").val();
	if (t == "") {
		return;
	}
	$.ajax({
		url : "/merchandise/mine/taxon2",
		type : "post",
		data : {
			id : id
		},
		cache : false,
		async : false,
		success : function(data) {
			//debugger
			$("#taxon2").html("");
			$("#taxon2").append("<option value=\"0\" >选择二级分类</option>");
			$.each(data, function(i, item) {
				$("#taxon2").append(
						"<option value="+item.code+">" + item.name
								+ "</option>");
			});
		}
	})
}


function getTaxon3(id) {
	var t = $("#taxon2").val();
	if (t == "") {
		return;
	}
	$.ajax({
		url : "/merchandise/mine/taxon3",
		type : "post",
		data : {
			id : id
		},
		cache : false,
		async : false,
		success : function(data) {
			//debugger
			$("#taxon3").html("");
			$("#taxon3").append("<option value=\"0\" >选择三级分类</option>");
			$.each(data, function(i, item) {
				$("#taxon3").append(
						"<option value="+item.code+">" + item.name
						+ "</option>");
			});
		}
	})
}