<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
    <title>欢迎页</title>
</head>
<body>
    <div>Hello World</div>
    <input type="button" name="btn_logout" value="退出登录"/>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
    $(function(){
        $("input[name='btn_logout']").click(function(){
            $.ajax({
                type:"post",
                url:"/logout.json",
                success:function(data) {
                    if (data.success) {
                        window.location.href = "/login.html";
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