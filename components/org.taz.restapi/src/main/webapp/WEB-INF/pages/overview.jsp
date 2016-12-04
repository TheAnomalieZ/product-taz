<%--
  Created by IntelliJ IDEA.
  User: K.Kokulan
  Date: 12/2/2016
  Time: 11:12 AM
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

    <title>The AnomalieZ - Overview</title>

    <!--Core CSS -->
    <link href="../../resources/js/bs3/css/bootstrap.min.css" rel="stylesheet">
    <link href="../../resources/css/bootstrap-reset.css" rel="stylesheet">
    <link href="../../resources/font-awesome/css/font-awesome.css" rel="stylesheet"/>

    <!--C3 Chart-->
    <link href="../../resources/c3-0.4.11/c3.css" rel="stylesheet"/>

    <!-- Custom styles for this template -->
    <link href="../../resources/css/style.css" rel="stylesheet">
    <link href="../../resources/css/style-responsive.css" rel="stylesheet"/>

    <!--iron slider-->
    <link href="../../resources/js/ion.rangeSlider-1.8.2/css/ion.rangeSlider.css" rel="stylesheet" />
    <link href="../../resources/js/ion.rangeSlider-1.8.2/css/ion.rangeSlider.skinFlat.css" rel="stylesheet"/>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https:/oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https:/oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<section id="container">
    <!--header start-->
    <header class="header fixed-top clearfix" style="background: #32323a;">
        <!--logo start-->
        <div class="brand">

            <a href="/home" class="logo">
                <img src="../../resources/images/logo_web.png" alt="">
            </a>
        </div>
        <!--logo end-->

    </header>
    <!--header end-->

    <!--sidebar start-->
    <%@include file="page_elements/sidebar.jsp" %>
    <!--sidebar end-->

    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <!-- page start-->

            <!-- Gauge view-->
            <div class="row">
                <div class="col-lg-4">
                    <section class="panel">
                        <header class="panel-heading">
                            Heap Usage
                        </header>
                        <div class="panel-body">
                            <div class="chart">
                                <div id="heap_usage"></div>
                            </div>
                            <div class="text-center">
                                Avg : ${heapUsageData.avg} MiB, Max : ${heapUsageData.max} MiB
                            </div>
                        </div>
                    </section>
                </div>
                <div class="col-lg-4">
                    <section class="panel">
                        <header class="panel-heading">
                            Total CPU Usage
                        </header>
                        <div class="panel-body">
                            <div class="chart">
                                <div id="cpu-usage"></div>
                            </div>
                            <div class="text-center">
                                Avg : ${totalCpuUsage.avg} %, Max : ${totalCpuUsage.max} %
                            </div>
                        </div>
                    </section>
                </div>
                <div class="col-lg-4">
                    <section class="panel">
                        <header class="panel-heading">
                            GC Pause Time
                        </header>
                        <div class="panel-body">
                            <div class="chart">
                                <div id="gc-pause-time"></div>
                            </div>
                            <div class="text-center">
                                Avg : ${gcData.avg} ms, Max : ${gcData.max} ms
                            </div>
                        </div>
                    </section>
                </div>
            </div>

            <!-- Gauge view end-->

            <%--Slider start--%>
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            Slider
                        </header>
                        <div class="panel-body">
                            <table class="table slider-table">
                                <tr>
                                    <td>
                                        <input id="range_1" type="text" name="range_1" value="" />
                                    </td>
                                </tr>
                            </table>

                        </div>
                    </section>
                </div>
            </div>
            <%--Slider end--%>

            <!-- heap usage start-->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            Heap Usage
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
                            CPU Usage
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

            <!-- General usage start-->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            General
                        </header>
                        <div class="panel-body">
                            <div class="text-left">
                                JVM Start Time:  <span>${jvmStartTime}</span>
                            </div>
                            <div class="text-left">
                                JVM Version:  <span>${jvmVersion}</span>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
            <!-- General usage end-->
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
<%--Slider--%>
<script src="../../resources/js/ion.rangeSlider-1.8.2/js/ion-rangeSlider/ion.rangeSlider.min.js" type="text/javascript"></script>

<!--C3 Chart-->
<script src="../../resources/js/d3js.org/d3.v3.min.js" charset="utf-8"></script>
<%--<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>--%>
<script src="../../resources/c3-0.4.11/c3.js"></script>
<script src="../../resources/js/c3-chart-overview-init.js"></script>

<!--common script init for all pages-->
<script src="../../resources/js/scripts.js"></script>

<script type="text/javascript">
    loadHeapUsage(${heapUsageData.data.get(0)});
    loadCpuUsage(${totalCpuUsage.data.get(0)});
    loadGCPauseTime(${gcData.data.get(0)});
    loadHeapUsageLineChart(${heapUsageData.tenpData});
    loadCpuUsageLineChart(${totalCpuUsage.tempCpuUsage});

    $("#range_1").ionRangeSlider({
        min: 0,
        max: 5000,
        from: 1000,
        to: 4000,
        type: 'double',
        step: 1,
        prefix: "",
        prettify: false,
        hasGrid: true
    });
</script>


</body>
</html>
