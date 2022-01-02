package main.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Map;

public class ControllerServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        response.getWriter().println("<html><body><p>Привет, возвращайтесь на домашнюю страницу!! </p><a href='/Web2New-1.0-SNAPSHOT'>сюда</a></body></html>");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map params = request.getParameterMap();
        boolean hasToClear = params.containsKey("do") && request.getParameter("do").equals("clear");
        boolean containsKeys = params.containsKey("x-value") && params.containsKey("y") && params.containsKey("r-value");
        if (hasToClear){
            try {
                getServletContext().getNamedDispatcher("ClearServlet").forward(request, response);
            } catch (ServletException | IOException e) {
                log(e.getMessage());
            }
        }else if (containsKeys && params.get("x-value")!=null && params.get("y")!=null && params.get("r-value")!=null){
            try {
                getServletContext().getNamedDispatcher("AreaCheckServlet").forward(request, response);
            } catch (ServletException | IOException e) {
                log(e.getMessage());
            }
        } else {
            response.sendError(400, "Incorrect Request");
            response.getOutputStream().close();
        }
    }
}
