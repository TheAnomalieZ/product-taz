<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: K.Kokulan
  Date: 12/25/2016
  Time: 7:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="../../resources/images/favicon.html">

    <title>The AnomalieZ - JVM Information</title>

    <!--Core CSS -->
    <link href="../../resources/js/bs3/css/bootstrap.min.css" rel="stylesheet">
    <link href="../../resources/css/bootstrap-reset.css" rel="stylesheet">
    <link href="../../resources/font-awesome/css/font-awesome.css" rel="stylesheet"/>

    <!-- Custom styles for this template -->
    <link href="../../resources/css/style.css" rel="stylesheet">
    <link href="../../resources/css/style-responsive.css" rel="stylesheet"/>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https:/oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https:/oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<section id="container">
    <!--header start-->
    <header class="header fixed-top clearfix">
        <!--logo start-->
        <div class="brand">

            <a href="/home" class="logo">
                <img src="../../resources/images/logo_web.png" alt="">
            </a>
        </div>
        <!--logo end-->
        <%--breafcrumb start--%>
        <div class="nav notify-row" id="top_menu">
            <ul class="breadcrumbs-alt">
                <li>
                    <a href="/home">Home</a>
                </li>
                <li>
                    <a class="active-trail active" href="/profile_home">My files</a>
                </li>
                <li>
                    <a class="current" href="">JVM Information</a>
                </li>
            </ul>
        </div>

        <%--breafcrumb end--%>

    </header>
    <!--header end-->

    <!--sidebar start-->
    <%@include file="page_elements/sidebar.jsp" %>
    <!--sidebar end-->

    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <!-- page start-->
            <%-- Content start--%>
            <%--JVM Property table start--%>
            <div class="row">
                <div class="col-md-12">
                    <section class="panel">
                        <header class="panel-heading">
                            General
                        </header>
                        <div class="panel-body">
                            <table class="table table-hover general-table">
                                <tbody>
                                <tr>
                                    <td>JVM Start Time</td>
                                    <td>${jvmInformation.jvmStartTime}</td>
                                </tr>
                                <tr>
                                    <td>JVM Name</td>
                                    <td>${jvmInformation.jvmName}</td>
                                </tr>
                                <tr>
                                    <td>JVM Version</td>
                                    <td>${jvmInformation.jvmVersion}</td>
                                </tr>
                                <tr>
                                    <td>JVM Command Line Arguments</td>
                                    <td>${jvmInformation.jvmArguments}</td>
                                </tr>
                                <tr>
                                    <td>Java Application Arguments</td>
                                    <td>${jvmInformation.javaArguments}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                    </section>
                </div>
            </div>
            <%--JVM Property Table End--%>

            <div class="row">
                <div class="col-md-12">
                    <section class="panel">
                        <header class="panel-heading">
                            System Properies at JVM Start
                        </header>
                        <div class="panel-body">
                            <table class="table  table-hover general-table">
                                <thead>
                                <tr>
                                    <th> Key</th>
                                    <th>Value</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${initialSystemProperties}" var="fileName" varStatus="status">
                                    <tr>
                                        <td>${fileName.key}</td>
                                        <td>
                                                ${fileName.value}
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>
            </div>

            <%-- Content end--%>

            <!-- page end-->
        </section>
    </section>
    <!--main content end-->
</section>

<!-- Placed js at the end of the document so the pages load faster -->

<!--Core js-->
<script src="../../resources/js/jquery.js"></script>
<script src="../../resources/js/bs3/js/bootstrap.min.js"></script>
<script class="include" type="text/javascript" src="../../resources/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="../../resources/js/jquery.scrollTo.min.js"></script>
<script src="../../resources/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.js"></script>
<script src="../../resources/js/jquery.nicescroll.js"></script>

<%--<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>--%>
<script src="../../resources/c3-0.4.11/c3.js"></script>
<script src="../../resources/js/c3-chart-overview-init.js"></script>

<!--common script init for all pages-->
<script src="../../resources/js/scripts.js"></script>

</body>
</html>
