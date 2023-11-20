<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@include file='./style.css' %>
    </style>
    <title>Match | Tennis Scoreboard</title>
</head>
<body>
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
                <td class="table__field">Sam</td>
                <td class="table__field">2</td>
                <td class="table__field">4</td>
                <td class="table__field">50</td>
            </tr>
            <tr class="table__row">
                <td class="table__field">Sam</td>
                <td class="table__field">2</td>
                <td class="table__field">4</td>
                <td class="table__field">50</td>
            </tr>
        </table>
        <div class="match-buttons">
            <form class="form" method="POST">
                <input class="form__button" type="submit" value="Player 1 won"/>
            </form>
            <form class="form" method="POST">
                <input class="form__button" type="submit" value="Player 2 won"/>
            </form>
        </div>
    </section>
</div>
</body>
</html>