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
        try{
            ArrayList<SerializeXYR> resStorages = (ArrayList<SerializeXYR>) getServletContext().getAttribute("results");
            resStorages.clear();
        } catch (Exception e){
            log(e.getMessage());
        }finally {
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ArrayList<SerializeXYR> results = new ArrayList<>();
        if (getServletContext().getAttribute("results")==null){
            getServletContext().setAttribute("results", results);
        }
    }
}
