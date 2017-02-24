<%--
  Created by IntelliJ IDEA.
  User: K.Kokulan
  Date: 2/17/2017
  Time: 10:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="../../resources/images/favicon.html">

    <title>The AnomalieZ -Analyse Using Clustering</title>

    <!--Core CSS -->
    <link href="../../resources/js/bs3/css/bootstrap.min.css" rel="stylesheet">
    <link href="../../resources/css/bootstrap-reset.css" rel="stylesheet">
    <link href="../../resources/font-awesome/css/font-awesome.css" rel="stylesheet"/>

    <!--C3 Chart-->
    <link href="../../resources/c3-0.4.11/c3.css" rel="stylesheet"/>

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
                <%--<li>--%>
                <%--<a class="active-trail active" href="#">Pages</a>--%>
                <%--</li>--%>
                <li>
                    <a class="current" href="">Analyse using clustering</a>
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
            <!-- heap usage start-->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            Heap Usage
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                             </span>
                        </header>
                        <div class="panel-body">
                            <div class="chart">
                                <div id="heap-usage-line-chart"></div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
            <!-- heap usage end-->
            <!-- heap usage start-->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            Anomaly Score
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                             </span>
                        </header>
                        <div class="panel-body">
                            <div class="chart">
                                <div id="anomaly-score-line-chart"></div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
            <!-- heap usage end-->

            <%--Anomaly region--%>
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <header class="panel-heading">
                            Anomaly Regions
                     <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                                <a href="javascript:;" class="fa fa-cog"></a>
                                <a href="javascript:;" class="fa fa-times"></a>
                            </span>
                        </header>
                        <div class="panel-body">
                            <c:forEach var="region" items="${hotMethods}">
                                <button type="button" data-toggle="tab" href="#regin_${region.regionID}" onclick="zoomGraph(${region.startGCId}, ${region.endGCId})"
                                        class="btn btn-primary">Anomaly Region ${region.regionID}</button>
                            </c:forEach>
                        </div>
                    </section>
                </div>
                <%--</div>--%>
                <%--<div class="row">--%>
                <div class="tab-content">
                    <c:forEach var="region" items="${hotMethods}">
                        <div class="col-sm-12 tab-pane" id="regin_${region.regionID}" >
                            <section class="panel">
                                <header class="panel-heading">
                                    Hot Methods
                                        <%--<span class="tools pull-right">--%>
                                        <%--<a href="javascript:;" class="fa fa-chevron-down"></a>--%>
                                        <%--<a href="javascript:;" class="fa fa-cog"></a>--%>
                                        <%--<a href="javascript:;" class="fa fa-times"></a>--%>
                                        <%--</span>--%>
                                </header>
                                <div class="panel-body">

                                    <table class="table  table-hover general-table">
                                        <thead>
                                        <tr>
                                            <th> Method</th>
                                            <th>Percentage bar</th>
                                            <th>Percentage</th>
                                        </tr>
                                        </thead>
                                        <tbody id="hotMethodTableBody">
                                        <c:forEach var="type" items="${region.hotMethodsPercentage.entrySet()}">
                                            <tr>
                                                <td>${type.key}</td>
                                                <td>
                                                    <div class="progress progress-striped progress-xs">
                                                        <div style="width: ${type.value}%" aria-valuemax="100"
                                                             aria-valuemin="0"
                                                             aria-valuenow="40" role="progressbar"
                                                             class="progress-bar progress-bar-success">
                                                            <span class="sr-only">${type.value}% Complete (success)</span>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>${type.value} %</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>

                                </div>
                            </section>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <%--Anomaly region End--%>
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

<!--C3 Chart-->
<script src="../../resources/js/d3js.org/d3.v3.min.js" charset="utf-8"></script>
<%--<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>--%>
<script src="../../resources/c3-0.4.11/c3.js"></script>
<script src="../../resources/js/c3-chart-cluster-init.js"></script>

<!--common script init for all pages-->
<script src="../../resources/js/scripts.js"></script>
<script type="text/javascript">
    loadanomalyScoreLineChart(${anomalyScore});
    loadHeapUsageLineChart(${heapUsedData});


    var zoomGraph = function(min, max){
        zoomRange(min, max);
    };
</script>

</body>
</html>

