<%@ page import="main.classes.SerializeXYR" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Вторая лабораторная</title>
    <meta charset="UTF-8">
    <link href="css/style.css" rel="stylesheet" />

    <script src="js/js.cookie.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.11.1/jquery.validate.min.js"></script>
    <script>
        var dots = [];
    </script>

</head>
<body class="page">
<div id="header"><a href="https://github.com/SudYar/Web2" target="_blank">Ссылка на git</a>
    <span>Сударушкин Ярослав P3214<br/>
        Вариант 25450</span><br/></div>
<div class="clear"></div>
<div id="content">
    <div id="left">
        <input type="button" id="clear-table" value="Очистить таблицу" onclick="clearAll()">
        <table id="answer-table">
            <tr>
                <td>X</td><td>Y</td> <td>R</td><td>Результат</td>
            </tr>
            <%
                ArrayList<SerializeXYR> myResults;
                String currentId = "";

                Cookie[] cookies = request.getCookies();
                if (cookies!=null){
                    for (Cookie cookie : cookies){
                        if (cookie.getName().equals("sessionId")){
                            currentId = cookie.getValue();
                        }
                    }
                }
                HashMap<String, ArrayList<SerializeXYR>> allResults = (HashMap<String, ArrayList<SerializeXYR>>) request.getServletContext().getAttribute("results");
                if (allResults!=null && allResults.get(currentId)!=null){
                    myResults = allResults.get(currentId);
                    if(myResults.size()>0){
                        SerializeXYR lastInputs = myResults.get(myResults.size()-1);
                        pageContext.setAttribute("x-value", lastInputs.getX());
                        pageContext.setAttribute("y", lastInputs.getY());
                        pageContext.setAttribute("r-value", lastInputs.getR());
                    }
                } else {
                    myResults = new ArrayList<>();
                }
                pageContext.setAttribute("myResults", myResults);
            %>
            <c:forEach items="${myResults}" var="result">
                <tr>
                    <td>${result.getX()}</td>
                    <td>${result.getY()}</td>
                    <td>${result.getR()}</td>
                    <td>${result.getRes()}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div id="right">
        <canvas id="graph" width="250" height="250">Возникла ошибка с canvas</canvas>
        <p>Зона, в которую надо попасть</p>
        <div id="request">
            <form id="request-coordinates">
                <p>Выберите X</p>
                <p><input type="radio" name="x-value" id="x-3" value="-3">
                    <label for="x-3">-3</label>
                    <input type="radio" name="x-value" id="x-2" value="-2">
                    <label for="x-2">-2</label>
                    <input type="radio" name="x-value" id="x-1" value="-1">
                    <label for="x-1">-1</label>
                    <input type="radio" name="x-value" id="x0" value="0">
                    <label for="x0">0</label>
                    <input type="radio" name="x-value" id="x1" value="1">
                    <label for="x1">1</label>
                    <input type="radio" name="x-value" id="x2" value="2">
                    <label for="x2">2</label>
                    <input type="radio" name="x-value" id="x3" value="3">
                    <label for="x3">3</label>
                    <input type="radio" name="x-value" id="x4" value="4">
                    <label for="x4">4</label>
                    <input type="radio" name="x-value" id="x5" value="5">
                    <label for="x5">5</label>
                </p>
                <p>Введите Y:   <input type="text" id="y-text" name="y" autocomplete="false" required placeholder="(-3; 5)" size="2px" min="-3" max="5" maxlength="10"></p>
                <p>Выберите R </p>
                <p> <span style="color: red; visibility: hidden" id="r-error">Сначала надо выбрать R</span></p>
                <p>
                    <input type="radio" name="r-value" id="r1" value="1">
                    <label for="r1">1</label>
                    <input type="radio" name="r-value" id="r1.5" value="1.5">
                    <label for="r1.5">1.5</label>
                    <input type="radio" name="r-value" id="r2" value="2">
                    <label for="r2">2</label>
                    <input type="radio" name="r-value" id="r2.5" value="2.5">
                    <label for="r2.5">2.5</label>
                    <input type="radio" name="r-value" id="r3" value="3">
                    <label for="r3">3</label>
                </p>
                <p><input type="submit" name="submit" > <input id="reset_button" type="reset" name="reset"></p>
            </form>
        </div>
    </div>
</div>
<div class="clear"></div>
<script src="js/canvasPlus.js"></script>
<script src="js/input_valid.js"></script>

<script>
    <c:forEach items="${myResults}" var="result">
    dots.push({
        x: ${result.getX()},
        y: ${result.getY()}
    });
    </c:forEach>
</script>
<%
    if(myResults!=null && myResults.size()>0)  {
%>
<script>
    setX(${x});
    setY(${y});
    $("#r-value").val(${r});
</script>
<%
    }
%>

<%--<script type="text/javascript" src="js/script.js"></script>--%>
</body>
</html>