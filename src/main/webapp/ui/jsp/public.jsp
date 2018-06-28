<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Spring Security Example</title>
    <link href="/bootstrap.min.css" rel="stylesheet">
    <script src="/jquery-2.2.1.min.js"></script>
    <script src="/bootstrap.min.js"></script>
</head>
<body>

<h2 class="mt-5 text-center"> Best player ranking </h2>
<c:if test="${not empty voter}" >
    <h5 class=" text-center"> Iti multumim ${voter} pentru vot! </h5>
</c:if>

<div class="container">

    <div class="row" style="border: 1px solid green; padding: 10px">
        <div class="col-sm-2 text-center">
            <strong></strong>
        </div>
        <div class="col-sm-2 text-center">
            <strong>Name</strong>
        </div>
        <div class="col-sm-2 text-center">
            <strong>Number</strong>
        </div>
        <div class="col-sm-2 text-center">
            <strong>Position</strong>
        </div>
        <div class="col-sm-2 text-center">
            <strong>Votes</strong>
        </div>
        <div class="col-sm-2 text-center">
            <strong>Action</strong>
        </div>

    </div>



    <c:forEach var="user" items="${players}">

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
            <div class="col-sm-2 text-center">${user.num}</div>
            <div class="col-sm-2 text-center">${user.position}</div>
            <div class="col-sm-2 text-center">
                    ${user.votes.size()}
            </div>
            <div class="col-sm-2 text-center">
                <button class="btn btn-sm btn-primary" data-id="${user.id}" id="up" data-toggle="modal"
                        data-target="#playerModal">
                    Vote up
                </button>
                <script>
                    $(document).on("click", "#up", function () {
                        var myBookId = $(this).data('id');
                        $("#upPlayer").val(myBookId);
                    });
                </script>
                <button class="btn btn-sm btn-primary" data-id="${user.id}" id="down" data-toggle="modal"
                        data-target="#playerDownModal">Vote down
                </button>

                <script>
                    $(document).on("click", "#down", function () {
                        var myBookId = $(this).data('id');
                        $("#downPlayer").val(myBookId);
                    });
                </script>
            </div>
        </div>
    </c:forEach>

    <!-- Modal -->
    <div class="modal fade" id="playerModal" tabindex="-1" role="dialog" aria-labelledby="playerModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <form action="/public/vote">

                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title" id="playerModalLabel">Up Vote</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" id="upPlayer" name="id" value="${_csrf.token}"/>

                        <label for="voter">Numele tau </label>
                        <input required class="form-control" type="text" id="voter" name="voter">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </div>
            </form>

        </div>
    </div>

    <div class="modal fade" id="playerDownModal" tabindex="-1" role="dialog" aria-labelledby="playerModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <form action="/public/voteDown">

                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title">Down Vote</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" id="downPlayer" name="id" value=""/>


                        <label for="downvoter">Numele tau </label>
                        <input required class="form-control" type="text" id="downvoter" name="voter">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>

</body>
</html>
