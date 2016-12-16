<%-- 
    Document   : panelprincipal
    Created on : 28-nov-2016, 0:41:42
    Author     : alfas
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="jpa.AndlunRegistry"%>
<%@page import="jpa.AndlunUserGame"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilo.css"/>
        <script type="text/javascript" src="js/funcionsEvents.js"></script>
        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
        <title></title>
    </head>
    <body>
        <div class="contenedor">            
            <%String stnick = (String) request.getAttribute("nombreuser");%>
            <%List<AndlunRegistry> scoreP = (List) request.getAttribute("scoresPers");%>
            <%List<AndlunRegistry> scoreG = (List) request.getAttribute("scoresGlob");%>
            <%List<AndlunRegistry> dateEnd = (List) request.getAttribute("dateEnd");%>
            <%SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
                Date dateActual = new Date();%>
            <form action="/3.4.3ServletJPA/ServletLogout" method="post">
                <input id="blogout" type="submit" value="Logout">
            </form>
            <p style="float: right;">Cuenta de <%=stnick.toUpperCase()%></p>
            <div class="titulo">Park The Spacecraft</div>
            <div id="pestanas">
                <ul id=lista>
                    <li id="pestana1"><a href='javascript:cambiarPestanna(pestanas,pestana1);'>Inicio</a></li>
                    <li id="pestana2"><a href='javascript:cambiarPestanna(pestanas,pestana2);'>Score Personal</a></li>
                    <li id="pestana3"><a href='javascript:cambiarPestanna(pestanas,pestana3);'>Score Mundial</a></li>
                    <li id="pestana4"><a href='javascript:cambiarPestanna(pestanas,pestana4);'>Últimas Conexiones</a></li>
                </ul>
            </div>

            <body onload="javascript:cambiarPestanna(pestanas, pestana1);">

                <div id="contenidopestanas">
                    <div id="cpestana1">
                        <form action="/3.4.3ServletJPA/Game" method="get">
                            <center><input style="margin-top: 30px;margin-bottom: 30px" id="binicio" type="submit" value="Iniciar Partida"></center>

                            <p> Objetivo: Aterriza la nave en la plataforma sin estrellarlo.<br>                                
                                Instrucciones: Pulsa la barra de espacio para activar la propulsión,  soltar barra de espacio para apagar propulsión</p>

                        </form> 
                    </div>
                    <div id="cpestana2">                        
                        <center><table style="text-align: center">
                                <tr>
                                    <th colspan="3"><u>Top 10 de <%=stnick.toUpperCase()%></u></th>
                                </tr>
                                <tr>
                                    <td>Nº</td>
                                    <td> ----------------------------------------------------------------- </td>
                                    <td>Speed</td>
                                    <td>Points</td>
                                </tr>
                                <%float pointsP, percentP;
                                    for (int i = 0; i < scoreP.size(); i++) {
                                        if (scoreP.get(i).getSpeed() > -2) {
                                            percentP = (scoreP.get(i).getSpeed() * 100) / -2;
                                            pointsP = 100 - percentP;
                                        } else {
                                            pointsP = 0;
                                        }%>
                                <tr>
                                    <td><%=i + 1%></td>
                                    <td>------------------------------------------------------------------</td>
                                    <td><%=scoreP.get(i).getSpeed()%></td>
                                    <td><%=pointsP%></td>
                                </tr>
                                <%}%>
                            </table></center>
                    </div>
                    <div id="cpestana3">
                        <center><table style="text-align: center">
                                <tr>
                                    <th colspan="4"><u>Top 25 Mejores Puntuaciones</u></th>
                                </tr>
                                <tr>
                                    <td>Nº</td>
                                    <td>Name</td>
                                    <td> ----------------------------------------------------------------- </td>
                                    <td>Speed</td>
                                    <td>Points</td>
                                </tr>
                                <% float pointsG, percentG;
                                    for (int x = 0; x < scoreG.size(); x++) {
                                        if (scoreG.get(x).getSpeed() > -2) {
                                            percentG = (scoreG.get(x).getSpeed() * 100) / -2;
                                            pointsG = 100 - percentG;
                                        } else {
                                            pointsG = 0;
                                        }%>
                                <tr>
                                    <td><%=x + 1%>.</td>
                                    <td style="text-align: left"><%=scoreG.get(x).getIdUser().getNameUser().toUpperCase()%></td>
                                    <td> ----------------------------------------------------------------- </td>
                                    <td><%=scoreG.get(x).getSpeed()%></td>
                                    <td><%=pointsG%></td>
                                </tr>
                                <%}%>
                            </table></center>
                    </div>
                    <div id="cpestana4">
                        <center><table style="text-align: center">
                                <tr>
                                    <th colspan="3"><u>Últimas partidas</u></th>
                                </tr>
                                <tr>
                                    <th>Name</th>
                                    <th> ----------------------- </th>
                                    <th>Time (min)</th>
                                </tr>
                                <%for (int x = 0; x < dateEnd.size(); x++) {%>
                                <tr>                                    
                                    <td><%=dateEnd.get(x).getIdUser().getNameUser().toUpperCase()%></td>
                                    <td> ----------------------- </td>
                                    <% long tiempo = (dateActual.getTime() - dateEnd.get(x).getEndDate().getTime()) / (1000 * 60);%>                                   
                                    <td><%=tiempo%></td>
                                </tr>
                                <%}%>
                            </table></center>
                    </div>                            
                </div>
            <center><p>CopyRight © Andreu Luna<br>Trabajo Acceso a Datos</p></center>
        </div>
    </body>
</html>
