<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
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
    <!-- jQuery custom content scroller -->
    <link href="<c:url value="/resources/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet"/>

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css"/>" rel="stylesheet">
     <!-- PNotify -->
    <link href="<c:url value="/resources/vendors/pnotify/dist/pnotify.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/vendors/pnotify/dist/pnotify.buttons.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/vendors/pnotify/dist/pnotify.nonblock.css"/>" rel="stylesheet">
      <!-- jQuery -->
    <script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js"/>"></script>
  </head>

  <body class="nav-md ">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="index.html" class="site_title"><i class="fa fa-paw"></i> <span>Inventory Management!</span></a>
            </div>

            <div class="clearfix"></div>

            <!-- menu profile quick info -->
            <div class="profile clearfix">
              <div class="profile_pic">
                <img src="<c:url value="/resources/images/user.png" />"alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2>${userInfo.name}</h2>
              </div>
            </div>
            <!-- /menu profile quick info -->

            <br />

            <!-- sidebar menu -->
           	<tiles:insertAttribute name="sidebar"></tiles:insertAttribute>
            <!-- /sidebar menu -->

            <!-- /menu footer buttons -->
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout" href="<c:url value='/logout'/>">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
            <!-- /menu footer buttons -->
          </div>
        </div>

        <!-- top navigation -->
        <tiles:insertAttribute name="top-nav"></tiles:insertAttribute>
        <!-- /top navigation -->

        <!-- page content -->
        <tiles:insertAttribute name="body"></tiles:insertAttribute>
        <!-- /page content -->

        <!-- footer content -->
        <tiles:insertAttribute name="footer"></tiles:insertAttribute>
        <!-- /footer content -->
      </div>
    </div>

  
    <!-- Bootstrap -->
    <script src="<c:url value="/resources/vendors/bootstrap/dist/js/bootstrap.min.js"/>"></script>
    <!-- FastClick -->
    <script src="<c:url value="/resources/vendors/fastclick/lib/fastclick.js"/>"></script>
    <!-- NProgress -->
    <script src="<c:url value="/resources/vendors/nprogress/nprogress.js"/>"></script>
    <!-- jQuery custom content scroller -->
    <script src="<c:url value="/resources/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"/>"></script>
     <!-- PNotify -->
    <script src="<c:url value="/resources/vendors/pnotify/dist/pnotify.js"/>"></script>
    <script src="<c:url value="/resources/vendors/pnotify/dist/pnotify.buttons.js"/>"></script>
    <script src="<c:url value="/resources/vendors/pnotify/dist/pnotify.nonblock.js"/>"></script>
    

    <!-- Custom Theme Scripts -->
    <script src="<c:url value="/resources/build/js/custom.js"/>"></script>
  </body>
</html>