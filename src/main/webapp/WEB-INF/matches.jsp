<%@ page contentType="text/html;charset=UTF-8"%>
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
    <a class="header__link" href="/">Tennis Scoreboard</a>
</header>
<div class="container">
    <h1 class="page-title">Finished matches</h1>
    <section class="matches">
        <div class="search">
            <form class="search-form" method="post">
                <div class="search-form__block">
                    <label class="search-form__label" for="search">Search: </label>
                    <input class="search-form__input" required id="search" type="text" name="search" placeholder="ex. Todd"/>
                </div>
                <input class="search-form__button" type="submit" value="Search"/>
            </form>
        </div>
        <table class="table">
            <tr class="table__row">
                <th class="table__field table__header">Player 1</th>
                <th class="table__field table__header">Player 2</th>
                <th class="table__field table__header">Winner</th>
            </tr>
            <tr class="table__row">
                <td class="table__field">Sam</td>
                <td class="table__field">Nicole</td>
                <td class="table__field">Sam</td>
            </tr>
        </table>
        <div class="pagination">
            <a class="pagination__button" href="matches?page=1">First</a>
            <a class="pagination__button" href="matches?page=2">2</a>
            <a class="pagination__button pagination__button--selected" disabled href="matches?page=3">3</a>
            <a class="pagination__button" href="matches?page=4">4</a>
            <a class="pagination__button" href="matches?page=6">Last</a>
        </div>
    </section>
</div>
</body>
</html>
