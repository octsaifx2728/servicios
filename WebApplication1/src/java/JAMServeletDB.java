/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.com.jammexico.jamcomponents.JAMLibKernel;
import mx.com.jammexico.jamdb.JAMSendSql;
import mx.com.jammexico.jamdrivers.JAMSendLogon;

/**
 *
 * @author sfx
 */
@WebServlet(urlPatterns = {"/servlet/JAMServeletDB"})
public class JAMServeletDB extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse res)
            throws ServletException, IOException {
        
      
      System.out.println("hola SQL");
        
        InputStream inputStream = request.getInputStream();
        ObjectInputStream inputFromApplet = new ObjectInputStream(inputStream);
        try {
            Object string = (Object) inputFromApplet.readObject();
            System.out.println(string.getClass().getName());
            URL miurl = new URL("http://72.55.137.77:8024/JAMMexico/servlet/JAMServeletDB");
            URLConnection conexion = miurl.openConnection();
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.setUseCaches(false);
            conexion.setDefaultUseCaches(false);
            conexion.setRequestProperty("Content-Type", "java-internal/" + string.getClass().getName());
            conexion.connect();

            ObjectOutputStream output = new ObjectOutputStream(conexion.getOutputStream());
            output.writeObject(string);
            output.flush();
            JAMSendSql recibe  = null;
            
            try
            {
              ObjectInputStream input = new ObjectInputStream(conexion.getInputStream());
              Object response = input.readObject();
              recibe = (JAMSendSql)response;
      
            output.close();
            input.close();
             
              
                  // set the content type
                res.setContentType("java-internal/" + recibe.getClass().getName());

                // get the output stream
                OutputStream out = res.getOutputStream();

                // create an object output stream
                ObjectOutputStream oos = new ObjectOutputStream(out);

                // write the serialized output object
                oos.writeObject(recibe);
                oos.flush();
                oos.close();
            }
            catch (ClassNotFoundException e)
            {
              e.printStackTrace();
            }
            if (recibe.getError() != null)
            {

              System.out.println(recibe.getError());

            }
      } catch (ClassNotFoundException ex) {
            Logger.getLogger(JAMServeletLogon.class.getName()).log(Level.SEVERE, null, ex);
      }  
        
        
       
     
     
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
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
