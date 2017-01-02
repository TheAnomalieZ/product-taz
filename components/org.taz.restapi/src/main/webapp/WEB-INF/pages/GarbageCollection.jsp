<%--
  Created by IntelliJ IDEA.
  User: K.Kokulan
  Date: 12/26/2016
  Time: 11:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    <title>The AnomalieZ - Garbage Collection</title>

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
        <div class="nav notify-row" id="top_menu">
            <ul class="breadcrumbs-alt">
                <li>
                    <a href="/home">Home</a>
                </li>
                <%--<li>--%>
                <%--<a class="active-trail active" href="#">Pages</a>--%>
                <%--</li>--%>
                <li>
                    <a class="current" href="">Garbage Collection</a>
                </li>
            </ul>
        </div>
    </header>
    <!--header end-->

    <!--sidebar start-->
    <%@include file="page_elements/sidebar.jsp" %>
    <!--sidebar end-->

    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <!-- page start-->
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

            <!-- CPU usage start-->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            Reference Object
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                             </span>
                        </header>
                        <div class="panel-body">
                            <div class="chart">
                                <div id="cpu-usage-line-chart"></div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
            <!-- CPU usage end-->


            <div class="row">
                <%--Gc Id Table Start--%>
                <div class="col-lg-4">
                    <section class="panel">
                        <header class="panel-heading">
                            Garbage Collections
                        </header>
                        <div class="panel-body" style="height: 1282px; overflow: auto;">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>GC ID</th>
                                    <th>Longest Pause</th>
                                    <th>Type</th>
                                </tr>
                                </thead>
                                <tbody>

                                    <c:forEach items="${garbageCollectionCommon}" var="gcCommon" varStatus="status">
                                        <tr data-toggle="tab" href="#gcid_${gcCommon.gcId}">
                                        <td>${gcCommon.gcId}</td>
                                        <td>${gcCommon.longestPause}</td>
                                        <td>${gcCommon.name}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>

                <%--Gc Id Table end--%>

                <div class="tab-content">
                    <c:forEach items="${garbageCollectionCommon}" var="gcCommon" varStatus="status">
                        <div id="gcid_${gcCommon.gcId}" class="tab-pane">


                                <%--Reference Object Start--%>
                            <div class="col-sm-8">
                                <section class="panel">
                                    <header class="panel-heading">
                                        Reference Objects
                                    </header>
                                    <div class="panel-body">
                                        <div class="col-lg-6">
                                            <div class="chartJS">
                                                <canvas id="pie-chart-js-${gcCommon.gcId}" height="250" width="300"></canvas>
                                            </div>
                                        </div>


                                        <div class="col-lg-6">
                                            <table class="table">
                                                <thead>
                                                <tr>
                                                    <th>Type</th>
                                                    <th></th>
                                                    <th>Count</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <td style="background: #E67A77">  </td>
                                                    <td>Soft reference</td>
                                                    <td>${pieChartData.get(gcCommon.gcId).get("Soft reference")}</td>
                                                </tr>
                                                <tr>
                                                    <td style="background: #D9DD81">  </td>
                                                    <td>Weak reference</td>
                                                    <td>${pieChartData.get(gcCommon.gcId).get("Weak reference")}</td>
                                                </tr>
                                                <tr>
                                                    <td style="background: #79D1CF">  </td>
                                                    <td>Final reference</td>
                                                    <td>${pieChartData.get(gcCommon.gcId).get("Final reference")}</td>
                                                </tr>
                                                <tr>
                                                    <td style="background: #BC89D1">  </td>
                                                    <td>Phantom reference</td>
                                                    <td>${pieChartData.get(gcCommon.gcId).get("Phantom reference")}</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </section>
                            </div>
                                <%--Reference Object end--%>

                        <%--Gc General Table Start--%>
                        <div class="col-lg-4">
                            <section class="panel">
                                <div class="panel-body">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>General</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>Type</td>
                                            <td>${gcCommon.name}</td>
                                        </tr>
                                        <tr>
                                            <td>GC Reason</td>
                                            <td>${gcCommon.cause}</td>
                                        </tr>
                                        <tr>
                                            <td>GC ID</td>
                                            <td>${gcCommon.gcId}</td>
                                        </tr>
                                        <tr>
                                            <td>Start Time</td>
                                            <td>${gcCommon.startTimeString}</td>
                                        </tr>
                                        <tr>
                                            <td>End Time</td>
                                            <td>${gcCommon.endTimeString}</td>
                                        </tr>
                                        <tr>
                                            <td>GC Duration</td>
                                            <td>${gcCommon.durationString}</td>
                                        </tr>
                                        <tr>
                                            <td>Longest Pause</td>
                                            <td>${gcCommon.longestPause}</td>
                                        </tr>
                                        <tr>
                                            <td>Sum of Pause</td>
                                            <td>${gcCommon.sumOfPauses}</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </section>
                        </div>

                        <%--Gc General Table End--%>

                        <%--Gc Heap Table Start--%>
                        <div class="col-lg-4">
                            <section class="panel">
                                <%--<header class="panel-heading">--%>
                                    <%--Heap--%>
                                <%--</header>--%>
                                <div class="panel-body">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>Heap</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${allGCEvents.get(gcCommon.gcId).heapSummaryEvent}" var="heapSummary">
                                        <c:if test="${heapSummary.when=='Before GC'}">
                                            <tr>
                                                <td><b>Heap Before GC</b></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Reserved Heap Size</td>
                                                <td>${heapSummary.heapSpaceReservedSize}</td>
                                            </tr>
                                            <tr>
                                                <td>Committed Heap Size</td>
                                                <td>${heapSummary.heapSpaceCommittedSize}</td>
                                            </tr>
                                            <tr>
                                                <td>Heap Usage</td>
                                                <td>${heapSummary.heapUsed}</td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${heapSummary.when=='After GC'}">
                                            <tr>
                                                <td><b>Heap After GC</b></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Reserved Heap Size</td>
                                                <td>${heapSummary.heapSpaceReservedSize}</td>
                                            </tr>
                                            <tr>
                                                <td>Committed Heap Size</td>
                                                <td>${heapSummary.heapSpaceCommittedSize}</td>
                                            </tr>
                                            <tr>
                                                <td>Heap Usage</td>
                                                <td>${heapSummary.heapUsed}</td>
                                            </tr>
                                        </c:if>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </section>
                        </div>

                        <%--Gc Heap Table End--%>

                        <%--Metaspace before gc Start--%>
                        <div class="col-lg-4">
                            <section class="panel">
                                <div class="panel-body">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>Metaspace Before GC</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${allGCEvents.get(gcCommon.gcId).metaspaceSummaryEvents}" var="metaSpaceEvent">
                                            <c:if test="${metaSpaceEvent.when=='Before GC'}">
                                                <tr>
                                                    <td><b>Class Memory</b></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Used Memory</td>
                                                    <td>${metaSpaceEvent.classSpaceUsed}</td>
                                                </tr>
                                                <tr>
                                                    <td>Committed Memory</td>
                                                    <td>${metaSpaceEvent.classSpaceCommitted}</td>
                                                </tr>
                                                <tr>
                                                    <td>Reserved Memory</td>
                                                    <td>${metaSpaceEvent.classSpaceReserved}</td>
                                                </tr>
                                                <tr>
                                                    <td><b>Data Memory</b></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Used Memory</td>
                                                    <td>${metaSpaceEvent.dataSpaceUsed}</td>
                                                </tr>
                                                <tr>
                                                    <td>Committed Memory</td>
                                                    <td>${metaSpaceEvent.dataSpaceCommitted}</td>
                                                </tr>
                                                <tr>
                                                    <td>Reserved Memory</td>
                                                    <td>${metaSpaceEvent.dataSpaceReserved}</td>
                                                </tr>
                                                <tr>
                                                    <td><b>Total Memory</b></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Used Memory</td>
                                                    <td>${metaSpaceEvent.metaspaceUsed}</td>
                                                </tr>
                                                <tr>
                                                    <td>Committed Memory</td>
                                                    <td>${metaSpaceEvent.metaspaceCommitted}</td>
                                                </tr>
                                                <tr>
                                                    <td>Reserved Memory</td>
                                                    <td>${metaSpaceEvent.metaspaceReserved}</td>
                                                </tr>

                                            </c:if>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </section>
                        </div>

                        <%--Metaspace before gc End--%>

                        <%--Metaspace after gc Start--%>
                        <div class="col-lg-4">
                            <section class="panel">
                                <div class="panel-body">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>Metaspace After GC</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${allGCEvents.get(gcCommon.gcId).metaspaceSummaryEvents}" var="metaSpaceEvent">
                                            <c:if test="${metaSpaceEvent.when=='After GC'}">
                                                <tr>
                                                    <td><b>Class Memory</b></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Used Memory</td>
                                                    <td>${metaSpaceEvent.classSpaceUsed}</td>
                                                </tr>
                                                <tr>
                                                    <td>Committed Memory</td>
                                                    <td>${metaSpaceEvent.classSpaceCommitted}</td>
                                                </tr>
                                                <tr>
                                                    <td>Reserved Memory</td>
                                                    <td>${metaSpaceEvent.classSpaceReserved}</td>
                                                </tr>
                                                <tr>
                                                    <td><b>Data Memory</b></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Used Memory</td>
                                                    <td>${metaSpaceEvent.dataSpaceUsed}</td>
                                                </tr>
                                                <tr>
                                                    <td>Committed Memory</td>
                                                    <td>${metaSpaceEvent.dataSpaceCommitted}</td>
                                                </tr>
                                                <tr>
                                                    <td>Reserved Memory</td>
                                                    <td>${metaSpaceEvent.dataSpaceReserved}</td>
                                                </tr>
                                                <tr>
                                                    <td><b>Total Memory</b></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Used Memory</td>
                                                    <td>${metaSpaceEvent.metaspaceUsed}</td>
                                                </tr>
                                                <tr>
                                                    <td>Committed Memory</td>
                                                    <td>${metaSpaceEvent.metaspaceCommitted}</td>
                                                </tr>
                                                <tr>
                                                    <td>Reserved Memory</td>
                                                    <td>${metaSpaceEvent.metaspaceReserved}</td>
                                                </tr>

                                            </c:if>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </section>
                        </div>

                        <%--Metaspace after gc End--%>

                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- page end-->
        </section>
    </section>
    <!--main content end-->
</section>

<!-- Placed js at the end of the document so the pages load faster -->

<!--Core js-->
<script src="../../resources/js/jquery.js"></script>
<script src="../../resources/js/jquery-1.8.3.min.js"></script>
<script src="../../resources/js/bs3/js/bootstrap.min.js"></script>
<script class="include" type="text/javascript" src="../../resources/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="../../resources/js/jquery.scrollTo.min.js"></script>
<script src="../../resources/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.js"></script>
<script src="../../resources/js/jquery.nicescroll.js"></script>
<!--C3 Chart-->
<script src="../../resources/js/d3js.org/d3.v3.min.js" charset="utf-8"></script>
<%--<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>--%>
<script src="../../resources/c3-0.4.11/c3.js"></script>
<script src="../../resources/js/c3-chart-Garbage-Collection-init.js"></script>

<!--common script init for all pages-->
<script src="../../resources/js/scripts.js"></script>

<!--Easy Pie Chart-->
<script src="../../resources/js/easypiechart/jquery.easypiechart.js"></script>

<!--Chart JS-->
<script src="../../resources/js/chart-js/Chart.js"></script>
<%--<script src="../../resources/js/chartjs.init.js"></script>--%>

<script type="text/javascript">
    loadPie();
    function loadPie() {
        <c:forEach items="${gcId}" var="gc">
        var myPie${gc} = new Chart(document.getElementById('pie-chart-js-' + ${gc}).getContext("2d")).Pie(${pieChartDataString.get(gc)});
        </c:forEach>
    }

    loadHeapUsageLineChart(${heapGraphData});
    loadCpuUsageLineChart(${referenceObjDataString});

</script>


</body>
</html>
