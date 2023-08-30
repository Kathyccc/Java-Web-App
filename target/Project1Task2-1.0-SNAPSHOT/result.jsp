<%--
  Created by IntelliJ IDEA.
  User: kathy
  Date: 10/02/2023
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%-- This is JSP comment --%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <title>Project1Task3</title>
</head>

<body>

<form action="worldCup" method="GET" >

    <h1>Country: <%= request.getParameter("country-name")%></h1>

    <h2>Nickname: <%= request.getAttribute("nickName")%></h2>

    <small>www.topendsports.com/sport/soccer/team-nicknames-women.htm</small>

    <h2>Capital City: <%= request.getAttribute("capital")%></h2>

    <small>www.restcountries.com</small>

    <h2>Top Scorer in 2019: <%= request.getAttribute("scorer")%>  <%=request.getAttribute("goal")%></h2>

    <small>www.espn.com/soccer/stats/_/league/FIFA.WWC/season/2019/view/scoring</small>

    <h2>Flag:</h2>

    <img src="<%= request.getAttribute("pictureURL")%>" width="200" height="120"><br>

    <small>www.cia.gov/the-world-factbook/countries/</small>

    <h2>Flag Emoji:</h2>

    <img src="<%= request.getAttribute("emojiPath")%>" width="200" height="200"><br>

    <small>www.cdn.jsdelivr.net/npm/country-flag-emoji-json@2.0.0</small>

</form>

<br/><br/>

<form action="worldCup" method="GET">

    <input type="submit" value="Continue">

</form>

</body>

</html>

