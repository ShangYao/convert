/*author:shangyao  date:2017.12.09*/
$(function(){
        test("pagination-plugin", function(){


            var element = $('#pagination');

            options = {
                size:"small",
                bootstrapMajorVersion:3,
                currentPage: $("#page").val(),
                numberOfPages: 5,
                totalPages:$("#totalPages").val(),
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
               // location.href = "/id/query?page=" + page;
                myform.action=$("#action").val()+page;
                myform.submit();
            }
            };

            element.bootstrapPaginator(options);

           
        })
    });
