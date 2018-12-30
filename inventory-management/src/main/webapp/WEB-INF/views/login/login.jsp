<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page language="java" pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Inventory Management | </title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <!-- NProgress -->
    <link href="<c:url value="/resources/vendors/nprogress/nprogress.css"/>" rel="stylesheet">
    <!-- Animate.css -->
    <link href="<c:url value="/resources/vendors/animate.css/animate.min.css"/>" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css"/>" rel="stylesheet">
  </head>

  <body class="login">
    <div>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form:form modelAttribute="loginForm" servletRelativeAction="/processLogin" method="POST" >
              <h1>Login Form</h1>
              <div>
                <form:input path="userName" cssClass="form-control" placeholder="Username"/>
                <div class="has-error">
                	<form:errors path="userName" cssClass="help-block"></form:errors>
                </div>
              </div>
              <div>
                <form:password path="password" cssClass="form-control" placeholder="Password" />
                <div class="has-error">
                	<form:errors path="password" cssClass="help-block"></form:errors>
                </div>
              </div>
              <div>
                <button class="btn btn-default submit" type="submit">Log in</button>
              </div>

              <div class="clearfix"></div>

              <div class="separator">

                <div class="clearfix"></div>
                <br />

                <div>
                  <h1><i class="fa fa-paw"></i> Inventory Management System!</h1>
                  <p>©2019 Inventory Management System. Privacy and Terms</p>
                </div>
              </div>
            </form:form>
          </section>
        </div>

        
      </div>
    </div>
  </body>
</html>
