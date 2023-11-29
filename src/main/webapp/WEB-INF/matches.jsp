<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="matchesDto" scope="request" type="com.cofisweak.dto.MatchesDto"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">
    <title>Matches | Tennis Scoreboard</title>
</head>
<body>
<header class="header">
    <a class="header__link" href="${pageContext.request.contextPath}/">Tennis Scoreboard</a>
</header>
<div class="container">
    <h1 class="page-title">Finished matches</h1>
    <section class="matches">
        <div class="search">
            <form class="search-form" method="get">
                <div class="search-form__block">
                    <label class="search-form__label" for="search">Search: </label>
                    <input class="search-form__input" id="search" type="text" name="filter_by_player_name"
                           placeholder="ex. Todd" value="${matchesDto.query()}"/>
                </div>
                <input class="search-form__button" type="submit" value="Search"/>
            </form>
        </div>
        <c:choose>
            <c:when test="${matchesDto.matches().isEmpty()}">
                <h2 class="text text--center">No matches found</h2>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <tr class="table__row">
                        <th class="table__field table__header">Player 1</th>
                        <th class="table__field table__header">Player 2</th>
                        <th class="table__field table__header">Winner</th>
                    </tr>
                    <c:forEach items="${matchesDto.matches()}" var="item">
                        <tr class="table__row">
                            <td class="table__field">${item.firstPlayerName()}</td>
                            <td class="table__field">${item.secondPlayerName()}</td>
                            <td class="table__field">${item.winnerName()}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
        <c:if test="${matchesDto.pageCount() > 1}">
            <div class="pagination">
                <%
                    String url;
                    if (matchesDto.query() == null) {
                        url = "matches?page=";
                    } else {
                        url = "matches?filter_by_player_name=" + matchesDto.query() + "&page=";
                    }

                    if (matchesDto.page() == 1) {
                        out.println("<a class=\"pagination__button pagination__button--selected\" disabled href=\"" + url + "1\">1</a>");
                    } else {
                        out.println("<a class=\"pagination__button\" href=\"" + url + "1\">1</a>");
                    }

                    for (int i = Math.max(2, matchesDto.page() - 2);
                         i <= Math.min(matchesDto.page() + 2, matchesDto.pageCount() - 1);
                         i++) {
                        if (matchesDto.page() == i) {
                            out.println("<a class=\"pagination__button pagination__button--selected\" disabled href=\"" + url + i + "\">" + i + "</a>");
                        } else {
                            out.println("<a class=\"pagination__button\" href=\"" + url + i + "\">" + i + "</a>");
                        }
                    }
                    if (matchesDto.page() == matchesDto.pageCount()) {
                        out.println("<a class=\"pagination__button pagination__button--selected\" href=\"" + url + matchesDto.pageCount() + "\">" + matchesDto.pageCount() + "</a>");
                    } else {
                        out.println("<a class=\"pagination__button\" href=\"" + url + matchesDto.pageCount() + "\">" + matchesDto.pageCount() + "</a>");
                    }
                %>
            </div>
        </c:if>
    </section>
</div>
</body>
</html>