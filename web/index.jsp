<%-- 
    Document   : index
    Created on : 25-nov-2016, 19:28:19
    Author     : alfas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lander Game</title>
        <script type="text/javascript">
            function redirectServlet() {
               window.location="${pageContext.request.contextPath}/index";
            }
        </script>
    </head>
    <body onload='redirectServlet()'>
    </body>
</html>
