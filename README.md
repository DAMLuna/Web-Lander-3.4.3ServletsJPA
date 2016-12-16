# Web-Lander-3.4.3ServletsJPA-
Versión 1.0 de Java ServletJDBC.  
Creado por **Andreu Luna Font**  
Fecha 12/12/2016  
IDE: Netbeans 8.2  

Librerías:
* JDK (1.8)
* Apache Tomcat or TomEE
* EclipseLink (JPA 2.1) - eclipselink.jar
* EclipseLink (JPA 2.1) - javax.persistence_2.1.0.v201304241213.jar
* EclipseLink (JPA 2.1) - org.eclipse.persistence.jpa.jpql_2.5.2.v20140319-9ad6abd.jar
* postgresql-9.4.1209.jar

Base de Datos **Webdb**

**Tablas**

andlun_UserGame | andlun_Registry
--------------- | -----------------
1. id_user | 1. id_play
2. name_user |  2. id_user
3. password | 3. start_date
4. email | 4. end_date
	     |  5. speed

>(Las sentencia SQL para crear las tablas esta dentro del proyecto con el nombre tablas.sql, pero el mismo genera automaticamente las tablas.)

El proyecto consiste en crear para un juego simulador de nave espacial aterrizando en una plataforma una web .jsp que conenga formulario de registro y login manejados por Servlets, pagina principal de usuario y una serie de listas generadas mediante consultas a la base de datos. Tambien registrar datos del juego como quien ha jugado la partida en que lapso de tiempo (inicio y fin de partida) y la velocidad de la neve al alcanzar dicha plataforma mediante javascript y Servlets. Como extra tener control de errores en el tanto en la parte web, en java o en la base de datos.


#Explicación detallada#
La web inicia con index.jsp pero te redirige automaticamente a index2.jsp para que algunas funciones se activen mediante el Servlet **ServletLogin.java** que comprueba las cookies de usuario y password, en el caso de que esten guardados (cosa que significaría que se inicio correctamente) nos redirige a la página principal del usuario. En el caso de que no tengamos cookies guardadas nos aparece un formulario de login o si lo seleccionamos el formulario de registro.

###Normas de registro###
1. Es necesario rellenar todos los campos.
2. La contraseña tendra que tener mínimo 8 caracteres entre los cuales son necesario almenos un caracter en mayuscula, un caracter en minuscula y un número.
3. El email ha de tener las características típicas de un email [String]@[String].[com,es,org].

Al registrar y estar todo correcto los valores del formulario seran tratados en **ServletRegistro** que nos introduce un usuario nuevo a la tabla de andlun_UserGame dando paso a poder loguear con el e iniciar nuestra página principal. 
**Recalcar que la contraseña será encriptada por md5 y siempre ser manipulara de manera encriptada para asegurar la privacidad de esta.**

La página principal consta de cuatro partes separadas por pestañas:
* La primera es la del juego con un botón central que nos dirige al juego y las intrucciones del juego.
* La segunda pestaña nos muestra una lista con las 10 mejores partidas personales.
* La tercera pestaña nos muestra una lista con las 25 mejores partidas de todos los usuarios registrados.
* La cuarta pestaña nos muestra una lista con de las mas recientes personas que han jugado mostrando los minutos de su última partida realizada.
* Tambien cuenta con un botón Logout que nos ejecuta el post de **ServletLogout** encargado de borrar las cookies que nos permitian iniciar la web directamente en la página principal del usuario y llevandonos a la pagina e index2.jsp

> * Todas las consultas se encuentran en las clases jpa en las NamedQuery de sus respectivas tablas.

##Funcionamiento del juego##
Al iniciar el juego en el javascript de este nos guarda el valor de la fecha de inicio de partida y al finalizar nos recoge la velocidad alcanzada al llegar a la plataforma y la fecha de fin de partida y la envia en forma de parametros a **ServletStart** mediante ajax para ser introducidos a la tabla andlun_Registry. En el caso de interrumpir la partida no se añadira ningun valor a la tabla.