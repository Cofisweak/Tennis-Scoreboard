<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">
    <title>New match | Tennis Scoreboard</title>
</head>
<body>
<header class="header">
    <a class="header__link" href="${pageContext.request.contextPath}/">Tennis Scoreboard</a>
</header>
<div class="container">
    <h1 class="page-title">New match</h1>
    <form class="form" method="post">
        <div class="form__block">
            <label class="form__label" for="first_player">First Player: </label>
            <input class="form__input" required id="first_player" type="text" name="first_player" placeholder="Player 1"/>
        </div>
        <div class="form__block">
            <label class="form__label" for="second_player">Second Player: </label>
            <input class="form__input" required id="second_player" type="text" name="second_player"
                   placeholder="Player 2"/>
        </div>
        <div>
            <div>
                <input type="radio" id="three_sets" name="sets_count" value="3" required>
                <label for="three_sets">3 sets</label><br>
            </div>
            <div class="mt10">
                <input type="radio" id="five_sets" name="sets_count" value="5">
                <label for="five_sets">5 sets</label><br>
            </div>
        </div>
        <input class="button" type="submit" value="Start"/>
    </form>
</div>
</body>
</html>