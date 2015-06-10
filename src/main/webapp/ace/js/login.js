/**
 * 
 */

function login() {
	// 异步访问
	var url = "../zoneber/account/login.do";
	$.ajax({
		type : 'POST',
		url : url,
		data : {"userCode":$("#userName").val(), "password":$("#password").val()},
		success : function(data){
			if(data) {
				//转向首页
				window.location = "index.html";
			} 
			else {
				alert("用户名或密码错误");
			}
		},
		dataType : "json"
	});
}
