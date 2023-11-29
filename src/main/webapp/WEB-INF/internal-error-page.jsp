<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">
  <title>Tennis Scoreboard</title>
</head>
<body>
<header class="header">
  <a class="header__link" href="${pageContext.request.contextPath}/">Tennis Scoreboard</a>
</header>
<div class="container">
  <h1 class="page-title">INTERNAL ERROR ${requestScope['jakarta.servlet.error.status_code']}</h1>
  <div class="button-wrapper">
    <a class="button button--bold" href="${pageContext.request.contextPath}/">To main page</a>
  </div>
</div>
</body>
</html>
