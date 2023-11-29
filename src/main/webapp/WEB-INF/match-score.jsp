<jsp:useBean id="matchScoreDto" scope="request" type="com.cofisweak.dto.MatchScoreDto"/>
<%@ page import="com.cofisweak.model.MatchStatus" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">
    <title>Match | Tennis Scoreboard</title>
</head>
<body>
<header class="header">
    <a class="header__link" href="${pageContext.request.contextPath}/">Tennis Scoreboard</a>
</header>
<div class="container">
    <h1 class="page-title">MATCH</h1>
    <section class="match">
        <table class="table">
            <tr class="table__row">
                <th class="table__field table__header">Player</th>
                <th class="table__field table__header">Sets</th>
                <th class="table__field table__header">Games</th>
                <th class="table__field table__header">Points</th>
            </tr>
            <tr class="table__row">
                <td class="table__field">${matchScoreDto.firstPlayerName()}</td>
                <td class="table__field">${matchScoreDto.firstPlayerScore().sets()}</td>
                <td class="table__field">${matchScoreDto.firstPlayerScore().games()}</td>
                <td class="table__field">${matchScoreDto.firstPlayerScore().points()}</td>
            </tr>
            <tr class="table__row">
                <td class="table__field">${matchScoreDto.secondPlayerName()}</td>
                <td class="table__field">${matchScoreDto.secondPlayerScore().sets()}</td>
                <td class="table__field">${matchScoreDto.secondPlayerScore().games()}</td>
                <td class="table__field">${matchScoreDto.secondPlayerScore().points()}</td>
            </tr>
        </table>
        <c:choose>
            <c:when test="${matchScoreDto.matchStatus() != MatchStatus.FINISHED}">
                <div class="match-buttons">
                    <form class="form" method="POST">
                        <button class="button button--small button--bold" name="player_id" type="submit" value="1">Player 1 won</button>
                    </form>
                    <c:if test="${matchScoreDto.matchStatus() == MatchStatus.TIE_BRAKE}">
                        <p class="text text--bold">Tie Brake</p>
                    </c:if>
                    <form class="form" method="POST">
                        <button class="button button--small button--bold" name="player_id" type="submit" value="2">Player 2 won</button>
                    </form>
                </div>
            </c:when>
            <c:when test="${matchScoreDto.matchStatus() == MatchStatus.FINISHED}">
                <div class="match-finished-block">
                    <h2 class="text text--bold">Match finished</h2>
                    <p class="text">${matchScoreDto.winnerName()} wins!</p>
                    <a class="button button--bold button--small" href="${pageContext.request.contextPath}/">To main page</a>
                </div>
            </c:when>
        </c:choose>
    </section>
</div>
</body>
</html>
