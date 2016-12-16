/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import clases.Encriptacion;
import controllers.AndlunUserGameJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jpa.AndlunUserGame;

/**
 *
 * @author alfas
 */
public class ServletRegistro extends HttpServlet {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("3.4.3ServletJPAPU");

   /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            boolean emailCompr = false,nickCompr = false;
            //Llama a la clase de controlador de la tabla andlun_user_game
            AndlunUserGameJpaController userJpaContr = new AndlunUserGameJpaController(emf);
            //Creo una lista que contiene todo el contenido de la tabla andlun_user_game
            List<AndlunUserGame> usergame = userJpaContr.findAndlunUserGameEntities();
            //Llamo a la clase JPA de la tabla andlun_user_game
            AndlunUserGame jpauser = new AndlunUserGame();
            Encriptacion encript = new Encriptacion(); //llama a la clase encriptacion para los passwords
            /**
             * Guardo en variables el contenido del formulario de registro.
             * pass1 y pass2 se encriptan al instante de ser recogidas para mejor protección de datos.
             */
            String nick = request.getParameter("nick").toLowerCase();
            String pass1 = encript.md5(request.getParameter("passwd1"));
            String pass2 = encript.md5(request.getParameter("passwd2"));
            String email = request.getParameter("email");
            //Comprobación de que el nick-name o el email no se repiten
            if (usergame != null && !usergame.isEmpty()) {
                for (AndlunUserGame andlunUserGame : usergame) {
                    if (nick.contentEquals(andlunUserGame.getNameUser())) {
                        nickCompr = true;
                    }
                    if (email.contentEquals(andlunUserGame.getEmail())) {
                        emailCompr = true;
                    }
                }
            }
            /**
             * Si no ha encontrado que tanto el nick como el email estan ya registrado 
             * en la base de datos se dispondra a introducirlos.
             * En cambio si el nick o el email ya estan en uso será avisado entrado en una web vacia con un mensaje.
             * Por otro lado si las contraseñas no coinciden tambien te enviará a la web vacia con su respectivo mensaje 
             */
            if (pass1.contentEquals(pass2) && nickCompr == false && emailCompr == false) {
                jpauser.setNameUser(nick);
                jpauser.setPasswd(pass1);
                jpauser.setEmail(email);
                //Método para Insertar valores en una tabla.
                userJpaContr.create(jpauser);
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    request.getRequestDispatcher("index2.jsp").include(request, response);
                       out.println("<p style='color:white;text-align: center'>Registro completado ahora puedes Iniciar sesión<p>");
                    
                }
            } else {
                if (emailCompr == true) {
                    response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
                        /* TODO output your page here. You may use following sample code. */
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Registro</title>");
                        out.println("<link rel=\"StyleSheet\" href=\"index_style.css\" media=\"all\" type=\"text/css\">");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Ya hay un usuario registrado con ese email.</h1>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                }else if(nickCompr== true){
                    response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
                        /* TODO output your page here. You may use following sample code. */
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Registro</title>");
                        out.println("<link rel=\"StyleSheet\" href=\"index_style.css\" media=\"all\" type=\"text/css\">");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Ya hay un usuario registrado con ese Nombre de usuario.</h1>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                } else {
                    response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
                        /* TODO output your page here. You may use following sample code. */
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Registro</title>");
                        out.println("<link rel=\"StyleSheet\" href=\"index_style.css\" media=\"all\" type=\"text/css\">");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Las contraseñas no coinciden</h1>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServletRegistro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
