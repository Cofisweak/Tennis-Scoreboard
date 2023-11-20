<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@include file='./style.css' %>
    </style>
    <title>New match | Tennis Scoreboard</title>
</head>
<body>
<div class="container">
    <h1 class="page-title">New match</h1>
    <form class="form" method="post">
        <div class="form__block">
            <label class="form__label" for="firstPlayer">First Player: </label>
            <input class="form__input" required id="firstPlayer" type="text" name="firstPlayer" placeholder="Player 1"/>
        </div>
        <div class="form__block">
            <label class="form__label" for="secondPlayer">Second Player: </label>
            <input class="form__input" required id="secondPlayer" type="text" name="secondPlayer" placeholder="Player 2"/>
        </div>
        <input class="form__button" type="submit" value="Start"/>
    </form>
</div>
</body>
</html>