/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.mid.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sit.mid.model.AdditionQuestion;

/**
 *
 * @author Game
 */
public class AdditionQuestionServlet extends HttpServlet {

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
        String answer = request.getParameter("answer");
        if (answer != null) {
            try {
                int answerNumber = Integer.parseInt(answer);
                AdditionQuestion aq = (AdditionQuestion)request.getSession().getAttribute("question");
                if(aq.checkAnswer(answerNumber)) {
                    aq.incrementCorrect();
                    request.setAttribute("message", "Correct!!!");
                }
                else {
                    request.setAttribute("message", String.format("Wrong answer %d + %d = %d", aq.getX(), aq.getY(), aq.getX()+aq.getY()));
                }
                aq.nextQuestion();
            }
            catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid input!!!");
            }
        }
        else {
            AdditionQuestion aq = new AdditionQuestion();
            aq.nextQuestion();
            request.getSession(true).setAttribute("question", aq);
        }
        getServletContext().getRequestDispatcher("/AdditionQuestion.jsp").forward(request, response);
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
