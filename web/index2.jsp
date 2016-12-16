<%-- 
    Document   : index
    Created on : 23-nov-2016, 4:09:04
    Author     : alfas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html5>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Web Game</title>
        <link rel="StyleSheet" href="index_style.css" media="all" type="text/css">
        <script src="js/funcionsEvents.js"></script>
    </head>
    <body>
        <div id="formlog">
            <h1>Park The Spacecraft</h1>
            <div  style="text-align: center;margin-top: 10%">
                <form action="/3.4.3ServletJPA/index" method="post">
                    <label>Usuario:</label><input type="text" name="nick"/><br>
                    <label>Contraseña:</label><input type="password" name="passwd"/><br><br>
                    <input type="submit" value="Entrar">
                    <input type="button" style="margin-left: 20px" onclick="ventanaRegist()" value="Registrarse" ></input>

                </form>
            </div>
        </div>
        <div id="formregist" style="display:none;">
            <h1>Pagina de Registro</h1>
            <div style="text-align: center;margin-top: 10%;">
                <form id="reg" action="/3.4.3ServletJPA/registro" method="post">
                    <label>Usuario: </label><input type="text"  name="nick" required/><br>
                    <label>Contraseña: </label><input type="password" name="passwd1" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" title="La contraseña tendrá minimo 8 caracteres y tiene que contener minimo una letra mayuscula, minuscula y numero."required/><br>
                    <label>Repite la Contraseña: </label><input type="password" name="passwd2" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"  required/><br>
                    <label>Email: </label><input type="email" name="email" required/><br><br>
                    <input type="submit" value="Registrarse">
                    <input type="button" style="margin-left: 20px" onclick="ventanaLogin()" value="Pagina de Login" ></input>                    
                </form>
            </div>
        </div>
    </body>
</html>
