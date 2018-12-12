<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Spring Security Example</title>
    <link href="/bootstrap.min.css" rel="stylesheet">
    <script src="/jquery-2.2.1.min.js"></script>
    <script src="/bootstrap.min.js"></script>
</head>
<body>
<div>
    <div class="container" style="margin: 50px">
        <div>
            <form action="/logout" method="post">
                <button type="submit" class="btn btn-danger">Log Out</button>
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
            </form>
        </div>
        <h3> Welcome, ${user}</h3>


        <div id="topVotedPlayer" class="small text-secondary"></div>
        <div class="row text-center">
            <strong> User Details</strong>
        </div>
        <div class="row" style="border: 1px solid green; padding: 10px">
            <div class="col-md-2 text-center">
                <strong></strong>
            </div>
            <div class="col-md-2 text-center">
                <strong>Name</strong>
            </div>
            <div class="col-md-1 text-center">
                <strong>Number</strong>
            </div>
            <div class="col-md-2 text-center">
                <strong>Position</strong>
            </div>
            <div class="col-md-1 text-center">
                <strong>Cards</strong>
            </div>
            <div class="col-md-4 text-center">
                <strong>Action</strong>
            </div>

        </div>
        <script>
            window.players = [];
            console.log(${players});
        </script>

        <c:forEach var="user" items="${players}">

            <script>
                ${user.position} = 'position';
            </script>
            <div class="row" style="border: 1px solid green; padding: 10px">
                <div class="col-sm-2 text-center">
                    <div>
                        <img src="/${user.image}" width="80px">
                    </div>
                    <div>
                        <a href="/download?filename=${user.image}">Download</a>
                    </div>
                </div>
                <div class="col-sm-2 text-center">${user.name}</div>
                <div class="col-sm-1 text-center">${user.num}</div>
                <div class="col-sm-2 text-center">${user.position}</div>
                <div class="col-sm-1 text-center">
                    <c:forEach var="card" items="${user.cards}">
                        ${card.type}
                    </c:forEach>
                </div>
                <div class="col-sm-4 text-center">
                    <c:if test="${isMatchReferee}">
                        <a href="/player/${user.id}/red" class="btn btn-sm btn-danger">Red Card</a>
                    </c:if>
                    <a href="/player/${user.id}/yellow" class="btn btn-sm btn-warning">Yellow Card</a>
                    <c:if test="${isMatchReferee}">
                        <a href="/player/${user.id}/delete" class="btn btn-link ">Delete</a>
                    </c:if>
                </div>
            </div>
        </c:forEach>


        <h3 class="mt-5">Add new player</h3>
        <div class="row">
            <form class="form-inline" action="/dashboard" method="post">
                <label class="sr-only" for="inlineFormInputName2">Name</label>
                <input name="name" type="text" class="form-control mb-2 mr-sm-2" id="inlineFormInputName2"
                       placeholder="Player name">

                <label class="sr-only" for="inlineFormInputGroupUsername2">position</label>
                <input name="position" type="text" class="form-control form-control mb-2 mr-sm-2"
                       id="inlineFormInputGroupUsername2" placeholder="Position">

                <label class="sr-only" for="number">number</label>
                <input name="num" type="number" style="width:50px" class="form-control form-control mb-2 mr-sm-2"
                       id="number" placeholder="No">


                <select name="team" class="form-control mb-2 mr-sm-2" title="team">
                    <option selected>Selecteaza o echipa</option>
                    <option value="1">Barcelona</option>
                    <option value="2">Real Madrid</option>
                    <option value="3">Manchester</option>
                </select>


                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary mb-2">Add</button>
            </form>
        </div>

    </div>
</div>
</body>
</html>