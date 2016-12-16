/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Listener.PostgresqlListener;
import clases.Encriptacion;
import controllers.AndlunRegistryJpaController;
import controllers.AndlunUserGameJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jpa.AndlunRegistry;
import jpa.AndlunUserGame;

/**
 *
 * @author alfas
 */
public class ServletLogin extends HttpServlet {

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
        /**
         * Llamo a las clases que necesito para guardar los datos necesarios
         * para la tabla AndlunUserGame y AndlunRegistry.
         */
        AndlunUserGameJpaController userJpaContr = new AndlunUserGameJpaController(emf);
        List<AndlunUserGame> usergame = userJpaContr.findAndlunUserGameEntities();
        AndlunRegistryJpaController regJpaContr = new AndlunRegistryJpaController(emf);
        List<AndlunRegistry> listRegPer = regJpaContr.findAndlunRegistryEntities();
        AndlunUserGame jpauser = new AndlunUserGame();
        String comprCookie;
        int iduser = 0;
        boolean nickCompr = false, passwCompr = false;
        /**
         * Comprobamos que si tenemos cookies de login guardadas, en el caso de
         * que tengamos las usaremos para conectar a la pagina principal del usuario
         * si no nos mantiene en la página de Login.
         */
        Cookie ck[] = request.getCookies();
        for (Cookie cookie : ck) {
            comprCookie = cookie.getValue();
            for (AndlunUserGame andlunUserGame : usergame) {
                if (comprCookie.contentEquals(andlunUserGame.getNameUser())) {
                    nickCompr = true;
                    iduser = andlunUserGame.getIdUser();
                    String nick = comprCookie.toString();
                    request.setAttribute("nombreuser", nick);
                }
                if (comprCookie.contentEquals(andlunUserGame.getPasswd())) {
                    passwCompr = true;
                }
            }
        }
        if (nickCompr == true && passwCompr == true) {
            /**
             * Comandos para enviar listas de consultas al JSP.
             */
            request.setAttribute("scoresPers", listRegPer(iduser));
            request.setAttribute("scoresGlob", listRegGlob());
            request.setAttribute("dateEnd", listRegDEnd());
            RequestDispatcher a = request.getRequestDispatcher("panelprincipal.jsp");
            a.forward(request, response);
        } else {
            RequestDispatcher a = request.getRequestDispatcher("index2.jsp");
            a.forward(request, response);
        }

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
            /**
             * Llamo a las clases que necesito para guardar los datos necesarios
             * para la tabla AndlunUserGame y AndlunRegistry.
             */
            AndlunUserGameJpaController userJpaContr = new AndlunUserGameJpaController(emf);
            List<AndlunUserGame> usergame = userJpaContr.findAndlunUserGameEntities();
            AndlunRegistryJpaController regJpaContr = new AndlunRegistryJpaController(emf);
            List<AndlunRegistry> listRegPer = regJpaContr.findAndlunRegistryEntities();
            //La clase Encriptación contiene el método necesario para encriptar las contraseñas que introducimos para dar seguridad.
            Encriptacion encript = new Encriptacion();
            String nick = request.getParameter("nick").toLowerCase();
            String pass = encript.md5(request.getParameter("passwd"));
            int iduser = 0;
            boolean nickCompr = false;
            boolean passwCompr = false;
            /**
             * Comprobamos que el usuario y contraseña estan guardados en la
             * base de datos y que tienen relación.
             */
            if (usergame != null && !usergame.isEmpty()) {
                for (AndlunUserGame andlunUserGame : usergame) {
                    if (nick.contentEquals(andlunUserGame.getNameUser()) && pass.contentEquals(andlunUserGame.getPasswd())) {
                        nickCompr = true;
                        iduser = andlunUserGame.getIdUser();
                        passwCompr = true;
                    }
                }
                /**
                 * Sí lo encuentra genera cookies con la información y genera
                 * unas consultas, algunas estandar y otras personales y las
                 * lleva a la pagina principal. Si no concuerda la información
                 * con la base de datos te muestra un aviso. El tiempo de
                 * duración de las cookies es de 1 hora.
                 */
                if (nickCompr == true && passwCompr == true) {
                    Cookie ckn = new Cookie("nick", nick);
                    Cookie ckp = new Cookie("passwd", pass);
                    String stnick = nick.toString();
                    ckn.setMaxAge(60 * 60);
                    ckp.setMaxAge(60 * 60);
                    ckn.setPath("/");
                    ckp.setPath("/");
                    response.addCookie(ckn);
                    response.addCookie(ckp);
                    request.setAttribute("nombreuser", stnick);
                    request.setAttribute("scoresPers", listRegPer(iduser));
                    request.setAttribute("scoresGlob", listRegGlob());
                    request.setAttribute("dateEnd", listRegDEnd());
                    //Pagina principal de usuario.
                    RequestDispatcher a = request.getRequestDispatcher("panelprincipal.jsp");
                    a.forward(request, response);
                } else {
                    response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
                        /* TODO output your page here. You may use following sample code. */
                        request.getRequestDispatcher("index2.jsp").include(request, response);
                        out.println("<p style='color:white;text-align: center'>El nombre de usuario o contraseña no son correctos<p>");

                    }
                }
            }else{
                response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
                        /* TODO output your page here. You may use following sample code. */
                        request.getRequestDispatcher("index2.jsp").include(request, response);
                        out.println("<p style='color:white;text-align: center'>No hay usuarios. Se el primero en unirte a la familia<p>");

                    }
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<AndlunRegistry> listRegPer(int codi) {
        EntityManager em = emf.createEntityManager();
        @SuppressWarnings("unchecked")
        TypedQuery<AndlunRegistry> query = em.createNamedQuery("AndlunRegistry.findByIdUser", AndlunRegistry.class);
        query.setParameter("code", codi).setMaxResults(10);
        return query.getResultList();
    }

    public List<AndlunRegistry> listRegGlob() {
        EntityManager em = emf.createEntityManager();
        @SuppressWarnings("unchecked")
        TypedQuery<AndlunRegistry> query = em.createNamedQuery("AndlunRegistry.findByGlobalPoints", AndlunRegistry.class).setMaxResults(25);
        return query.getResultList();
    }

    public List<AndlunRegistry> listRegDEnd() {
        EntityManager em = emf.createEntityManager();
        @SuppressWarnings("unchecked")
        TypedQuery<AndlunRegistry> query = em.createNamedQuery("AndlunRegistry.findByEndDateOrder", AndlunRegistry.class).setMaxResults(10);
        return query.getResultList();
    }

}
