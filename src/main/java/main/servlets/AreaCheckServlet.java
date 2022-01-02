package main.servlets;

import main.classes.SerializeXYR;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

//@WebServlet(name = "AreaCheckServlet", value = "/Servlet")
public class AreaCheckServlet extends HttpServlet {
    
    private double[] xArray = new double[] {-3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
    private double[] rArray = new double[] {1.0, 1.5, 2.0, 2.5, 3.0};


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String xString = request.getParameter("x-value");
        String yString = request.getParameter("y");
        String rString = request.getParameter("r-value");
        double x;
        double y;
        double r;
        String cookieName = "sessionId";
        try{
            x = Double.parseDouble(xString);
            y = Double.parseDouble(yString);
            r = Double.parseDouble(rString);
            boolean isPointInArea = checkArea(x, y, r);
            SerializeXYR storage = new SerializeXYR(x, y, r, isPointInArea);
            Cookie[] cookies = request.getCookies();
            boolean cookieExists = false;
            for (Cookie cookie : cookies){
                if (cookie.getName().equals(cookieName)){
                    addCookieToContext(cookie, storage);
                    cookieExists = true;
                    break;
                }
            }
            if (!cookieExists){
                Cookie cookie = new Cookie(cookieName, UUID.randomUUID().toString());
                cookie.setMaxAge(60*60*24);
                response.addCookie(cookie);
                addCookieToContext(cookie, storage);
            }

        } catch (NumberFormatException e){
            
        }finally {
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    private void addCookieToContext(Cookie cookie, SerializeXYR storage){
        String sessionId = cookie.getValue();
        HashMap<String, ArrayList<SerializeXYR>> resStorages = (HashMap<String, ArrayList<SerializeXYR>>) getServletContext().getAttribute("results");
        if (resStorages.containsKey(sessionId)){
            resStorages.get(sessionId).add(storage);
        } else {
            ArrayList<SerializeXYR> libraryOfStorages = new ArrayList<>();
            libraryOfStorages.add(storage);
            resStorages.put(sessionId, libraryOfStorages);
        }
    }

    private boolean checkArea(double x, double y, double r){
        boolean inArea = false;
        if (validateXYR(x, y, r)){
            inArea = checkAreaWithValidXYR(x, y, r);
        }
        return inArea;
    }

    private boolean validateX(double x){
        for (double i : xArray){
            if (x==i){
                return true;
            }
        }
        return false;
    }

    private boolean validateY(double y){
        return y > -3.0 && y < 5.0;
    }

    private boolean validateR(double r){
        for (double i : rArray){
            if (r==i){
                return true;
            }
        }
        return false;
    }

    private boolean validateXYR(double x, double y, double r){
        return validateX(x) && validateY(y) && validateR(r);
    }

    private boolean checkAreaWithValidXYR(double x, double y, double r){

        boolean res = false;

        if (x>=0 && y>=0){
            if (x<=r/2 && y<=r){
                res = true;
            }
        }else if (x<=0 && y>0){
            if (y<=(x*2+r)){
                res = true;
            }
        }else if (x>0 && y<=0){
            if ((x*x+y*y)<=(r*r/4)){
                res = true;
            }
        }

        return res;
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
