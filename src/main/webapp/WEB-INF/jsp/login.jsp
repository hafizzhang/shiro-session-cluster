<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
    <title>登陆页</title>
</head>
<body>
    <div>
        用户名：<input type="text" name = "userName" /><br/>
        密码：<input type="text" name="password"/>
        <input name="btn_login" type="button" value="登陆">
    </div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
    $(function(){
        $("input[name='btn_login']").click(function(){
            var userName = $("input[name='userName']").val();
            var password = $("input[name='password']").val();
            if (userName == null) {
                alert("用户名不能为空");
            }
            if (password == null) {
                alert("密码不能为空");
            }
            $.ajax({
                type:"post",
                url:"/login.json",
                dataType:"json",
                data:{"userName":userName, "password":password},
                success:function(data) {
                    if (data.success) {
                        window.location.href = "/index.html";
                    } else {
                        alert(data.msg);
                    }
                },
                error:function() {
                    alert("请求出错");
                }
            });
        });
    });
</script>
</html>