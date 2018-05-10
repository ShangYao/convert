/*author:shangyao  date:2017.12.09*/
$(function(){
        test("pagination-plugin", function(){
            var element = $('#pagination-draft');
            options = {
                size:"small",
                bootstrapMajorVersion:3,
                currentPage: $("#draftPage").val(),
                numberOfPages: 5,
                totalPages:$("#draftTotalPages").val(),
                 shouldShowPage:true,
                 itemTexts: function (type, page, current) {        
        switch (type) {            
        case "first": return "首页";            
        case "prev": return "上一页";            
        case "next": return "下一页";            
        case "last": return "末页";            
        case "page": return page;
        }
    },
    onPageClicked: function (event, originalEvent, type, page) {
                myform.action=$("#draftAction").val()+page;
                myform.submit();
            }
            };
            element.bootstrapPaginator(options);
        })
    });


$(function(){
	test("pagination-plugin", function(){
		var element = $('#pagination-committed');
		options = {
				size:"small",
				bootstrapMajorVersion:3,
				currentPage: $("#committedPage").val(),
				numberOfPages: 5,
				totalPages:$("#committedTotalPages").val(),
				shouldShowPage:true,
				itemTexts: function (type, page, current) {        
					switch (type) {            
					case "first": return "首页";            
					case "prev": return "上一页";            
					case "next": return "下一页";            
					case "last": return "末页";            
					case "page": return page;
					}
				},
				onPageClicked: function (event, originalEvent, type, page) {
					myform.action=$("#committedAction").val()+page;
					myform.submit();
				}
		};
		element.bootstrapPaginator(options);
	})
});


$(function(){
	test("pagination-plugin", function(){
		var element = $('#pagination-published');
		options = {
				size:"small",
				bootstrapMajorVersion:3,
				currentPage: $("#publishedPage").val(),
				numberOfPages: 5,
				totalPages:$("#publishedTotalPages").val(),
				shouldShowPage:true,
				itemTexts: function (type, page, current) {        
					switch (type) {            
					case "first": return "首页";            
					case "prev": return "上一页";            
					case "next": return "下一页";            
					case "last": return "末页";            
					case "page": return page;
					}
				},
				onPageClicked: function (event, originalEvent, type, page) {
					myform.action=$("#publishedAction").val()+page;
					myform.submit();
				}
		};
		element.bootstrapPaginator(options);
	})
});


$(function(){
	test("pagination-plugin", function(){
		var element = $('#pagination-retired');
		options = {
				size:"small",
				bootstrapMajorVersion:3,
				currentPage: $("#retiredPage").val(),
				numberOfPages: 5,
				totalPages:$("#retiredTotalPages").val(),
				shouldShowPage:true,
				itemTexts: function (type, page, current) {        
					switch (type) {            
					case "first": return "首页";            
					case "prev": return "上一页";            
					case "next": return "下一页";            
					case "last": return "末页";            
					case "page": return page;
					}
				},
				onPageClicked: function (event, originalEvent, type, page) {
					myform.action=$("#retiredAction").val()+page;
					myform.submit();
				}
		};
		element.bootstrapPaginator(options);
	})
});


$(function(){
	test("pagination-plugin", function(){
		var element = $('#pagination-trash');
		options = {
				size:"small",
				bootstrapMajorVersion:3,
				currentPage: $("#trashPage").val(),
				numberOfPages: 5,
				totalPages:$("#trashTotalPages").val(),
				shouldShowPage:true,
				itemTexts: function (type, page, current) {        
					switch (type) {            
					case "first": return "首页";            
					case "prev": return "上一页";            
					case "next": return "下一页";            
					case "last": return "末页";            
					case "page": return page;
					}
				},
				onPageClicked: function (event, originalEvent, type, page) {
					myform.action=$("#trashAction").val()+page;
					myform.submit();
				}
		};
		element.bootstrapPaginator(options);
	})
});


$(function(){
	test("pagination-plugin", function(){
		var element = $('#pagination-declined');
		options = {
				size:"small",
				bootstrapMajorVersion:3,
				currentPage: $("#declinedPage").val(),
				numberOfPages: 5,
				totalPages:$("#declinedTotalPages").val(),
				shouldShowPage:true,
				itemTexts: function (type, page, current) {        
					switch (type) {            
					case "first": return "首页";            
					case "prev": return "上一页";            
					case "next": return "下一页";            
					case "last": return "末页";            
					case "page": return page;
					}
				},
				onPageClicked: function (event, originalEvent, type, page) {
					myform.action=$("#declinedAction").val()+page;
					myform.submit();
				}
		};
		element.bootstrapPaginator(options);
	})
});
