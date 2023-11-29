<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">
</head>
<body>
<header class="header">
    <a class="header__link" href="${pageContext.request.contextPath}/">Tennis Scoreboard</a>
</header>
<div class="container">
    <h1 class="page-title">Tennis Scoreboard</h1>
    <section class="menu">
        <div class="menu-item">
            <a class="menu-item__link" href="${pageContext.request.contextPath}/new-match">New match</a>
        </div>
        <div class="menu-item">
            <a class="menu-item__link" href="${pageContext.request.contextPath}/matches">Matches</a>
        </div>
    </section>
</div>
</body>
</html>