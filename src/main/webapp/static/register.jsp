<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="errors" scope="page" value="${requestScope.ERRORS}"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
          integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">

    <style>
        .container-index {
            background-image: url("${pageContext.request.contextPath}/static/image/bg_login.jpg");
        }
    </style>
</head>

<body>
<div id="navbar">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-expanded="false">
                        Dropdown
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="#">Action</a>
                        <a class="dropdown-item" href="#">Another action</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Something else here</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled">Disabled</a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>
</div>

<div class="container-fluid w-100 container-index full-height">
    <div class="row">
        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 col-xl-6 user-form">
            <form action="${pageContext.request.contextPath}/actions/register" method="POST" id="registerForm"
                  accept-charset="utf-8">
                <div class="form-group">
                    <h1 class="form-title">Register</h1>
                </div>
                <div class="form-group">
                    <label for="registerEmail">Email address</label>
                    <input name="txtEmail" type="email" class="form-control" id="registerEmail" value="${param.txtEmail}">
                    <small class="err-msg" ><c:out value="${errors['txtEmail']}"/></small>
                </div>
                <div class="form-group">
                    <label for="registerFullName">Full Name</label>
                    <input name="txtFullName" type="text" class="form-control" id="registerFullName" value="${param.txtFullName}">
                    <small class="err-msg" ><c:out value="${errors['txtFullName']}"/></small>
                </div>
                <div class="form-group">
                    <label for="registerPassword">Password</label>
                    <input name="txtPassword" type="password" class="form-control" id="registerPassword">
                    <small class="err-msg" ><c:out value="${errors['txtPassword']}"/></small>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Password</label>
                    <input name="txtConfirmPassword" type="password" class="form-control" id="confirmPassword">
                    <small class="err-msg" ><c:out value="${errors['txtConfirmPassword']}"/></small>
                </div>
                <button type="submit" class="btn btn-primary">Register</button>
                <a href="${pageContext.request.contextPath}/pages/login" class="float-right btn-toggle">Login</a>
                <br/>
                <a class="btn btn-outline-dark btn-google" href="/users/googleauth" role="button"
                   style="text-transform:none">
                    <img width="20px" style="margin-bottom:3px; margin-right:5px" alt="Google sign-in"
                         src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/512px-Google_%22G%22_Logo.svg.png"/>
                    Register with Google
                </a>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/js/main.js"></script>
<script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
        crossorigin="anonymous"></script>
</body>

</html>