/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DonationTracking;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Murthi
 */
@MultipartConfig(maxFileSize = 16177215)
public class Add_Campaign extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        
            /* TODO output your page here. You may use following sample code. */
             HttpSession user = request.getSession(true);
            String Fname = request.getParameter("Fname");
            String Fdes = request.getParameter("Fdes");
            String Amount = request.getParameter("Amount");
            String Fdate = request.getParameter("Fdate");
            String cid = user.getAttribute("cid").toString();
            String cname = user.getAttribute("cname").toString();
            
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String time = dateFormat.format(date);

            InputStream inputStream = null;
            Part filePart = request.getPart("Fpic");
            if (filePart != null) {
                System.out.println(filePart.getName());
                System.out.println(filePart.getSize());
                System.out.println(filePart.getContentType());
                inputStream = filePart.getInputStream();
            }

            Connection conn = SQLconnection.getconnection();
            Connection con = SQLconnection.getconnection();
            Statement st = con.createStatement();

            try {
                String sql = "insert into campaign(campName, CampDes, CampPic, Amount, EndDate, StartDate, AmountCol, CampStatus, cid, cname) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, Fname);
                statement.setString(2, Fdes);
                if (inputStream != null) {
                    statement.setBlob(3, inputStream);
                }
                statement.setString(4, Amount);
                statement.setString(5, Fdate);
                statement.setString(6, time);
                statement.setString(7, "0");
                statement.setString(8, "Inactive");
                statement.setString(9, cid);
                statement.setString(10, cname);
                int row = statement.executeUpdate();
                if (row > 0) {

                    response.sendRedirect("Add_Campaigns.jsp?Added");
                } else {
                    response.sendRedirect("Add_Campaigns.jsp?Failed");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Add_Campaign.class.getName()).log(Level.SEVERE, null, ex);
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
