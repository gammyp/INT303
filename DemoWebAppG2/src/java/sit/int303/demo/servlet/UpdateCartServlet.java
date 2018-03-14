/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sit.int303.demo.model.Cart;
import sit.int303.demo.model.OrderDetail;
import sit.int303.demo.model.OrderDetailPK;

/**
 *
 * @author Game
 */
public class UpdateCartServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        if (session != null) {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                response.sendError(500, "You cart is missing or emputy.");
                return;
            } else {
                String[] selectedItem = request.getParameterValues("deleteItems");
                for (String productCode : selectedItem) {
                    OrderDetailPK odpk = new OrderDetailPK(1, productCode);
                    cart.remove(odpk);
                }
                Enumeration<String> productCodes = request.getParameterNames();
                while (productCodes.hasMoreElements()) {
                    String code = productCodes.nextElement();
                    if(code.equalsIgnoreCase("deleteItems")) {
                        continue;
                    }
                    //System.out.printf("code : %s-10s value: %s\n",code ,request.getParameter(code));
                    int value = Integer.parseInt(request.getParameter(code));
                    OrderDetailPK odpk = new OrderDetailPK(1, code);
                    if (cart.getItem(odpk) != null) {
                        if (value == 0) {
                            cart.remove(odpk);
                        } else {
                            cart.getItem(odpk).setQuantityordered(value);
                        }
                    }
                }
            }
            getServletContext().getRequestDispatcher("/ViewCart").forward(request, response);
            return;
        } else {
            response.sendError(500, "Http Session has expired or not found ... Please add new product to your cart!");
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
