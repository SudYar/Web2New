package main.servlets;

import main.classes.SerializeXYR;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//@WebServlet(name = "ClearServlet", value = "/ClearServlet")
public class ClearServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cookieName = "sessionId";
        try{
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies){
                if (cookie.getName().equals(cookieName)){
                    clearInfo(cookie);
                    break;
                }
            }
        } catch (NumberFormatException e){
            log(e.getMessage());
        }finally {
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
    private void clearInfo(Cookie cookie){
        String sessionId = cookie.getValue();
        HashMap<String, ArrayList<SerializeXYR>> resStorages = (HashMap<String, ArrayList<SerializeXYR>>) getServletContext().getAttribute("results");
        if (resStorages.containsKey(sessionId)){
            resStorages.get(sessionId).clear();
        } else {
            ArrayList<SerializeXYR> libraryOfStorages = new ArrayList<>();
            resStorages.put(sessionId, libraryOfStorages);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        HashMap<String, ArrayList<SerializeXYR>> results = new HashMap<>();
        if (getServletContext().getAttribute("results")==null){
            getServletContext().setAttribute("results", results);
        }
    }
}
