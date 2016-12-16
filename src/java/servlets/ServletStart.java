/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import controllers.AndlunRegistryJpaController;
import controllers.AndlunUserGameJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
public class ServletStart extends HttpServlet {

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
        RequestDispatcher a = request.getRequestDispatcher("game.html");
        a.forward(request, response);
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
            int iduser=0;
            //Llamo a las clases que necesito para guardar los datos necesarios para la tabla AndlunRegistry
            AndlunRegistryJpaController regJpaContr = new AndlunRegistryJpaController(emf);
            AndlunUserGameJpaController userJpaContr = new AndlunUserGameJpaController(emf);
            AndlunRegistry ar = new AndlunRegistry();
            AndlunUserGame au = new AndlunUserGame();
            //Creo una lista que contiene todas las entidades encontradas de la tabla AndlunUserGame.
            List<AndlunUserGame> usergame = userJpaContr.findAndlunUserGameEntities();
            /**
             * Creo otra lista con las Cookies guardadas para comparar los datos 
             * del usuario con las cookies y asi poder sacar el numero idUser.
             */
            Cookie ck[] = request.getCookies();
            for (Cookie cookie : ck) {
                String comprCookie = cookie.getValue();
                for (AndlunUserGame andlunUserGame : usergame) {
                    if (comprCookie.contentEquals(andlunUserGame.getNameUser())) {
                        iduser = andlunUserGame.getIdUser();
                    }
                }
            }
            /**
             * Recojo los datos de inicio y fin de partida y la velocidad final,
             * las fechas las parseo a objetos Date y la velocidad la parseo a
             * objeto Float
             */          
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
            String fechaFin = request.getParameter("fechaFin");
            String fechaIni = request.getParameter("fechaIni");
            Date ff = sdf.parse(fechaFin);
            Date fi = sdf.parse(fechaIni);
            Float spd = Float.parseFloat(request.getParameter("spd"));
            /**
             * Introducimos las variables a los atributos de la clase UserGame
             * y Registry para introducirlos a la base de datos con su controlador.
             */
            au.setIdUser(iduser);
            ar.setIdUser(au);
            ar.setStartDate(fi);
            ar.setEndDate(ff);
            ar.setSpeed(spd);
            regJpaContr.create(ar);            
            RequestDispatcher a = request.getRequestDispatcher("game.html");
            a.forward(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ServletStart.class.getName()).log(Level.SEVERE, null, ex);
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
