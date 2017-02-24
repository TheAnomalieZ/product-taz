<%--
  Created by IntelliJ IDEA.
  User: kokulan
  Date: 2/23/17
  Time: 6:59 PM
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

    <title>The AnomalieZ</title>

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
                    <a class="current" href="#">Home</a>
                </li>
            </ul>
        </div>

        <%--breafcrumb end--%>

    </header>
    <!--header end-->

    <!--main content start-->
    <section id="main-content" class="merge-left">
        <section class="wrapper" style="background-color: #f1f2f7">
            <!-- page start-->
            <%-- Content start--%>

            <!-- heap usage start-->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            Actually what do you want?
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                             </span>
                        </header>
                        <div class="panel-body" style="display: block" id="panel_body">
                            <div class="col-md-6">
                                <button type="button" class="btn btn-info btn-lg"
                                        onclick="location.href='/profile_home'" style="width: 80%;">Profiling
                                </button>
                                <p></p>

                                <p><B>
                                    Able to analysis of the extensive of data collected by Java Flight Recorder.
                                </B>
                                </p>

                                <p><B>Features</B></p>
                                <p>* Garbage Collection Details </p>
                                <p>* Recorded Event Details</p>
                                <p>* Memory Related Information</p>
                                <p>* JVM Information</p>
                                <p>* CPU Usage Information</p>
                            </div>
                            <div class="col-md-6">
                                <button type="button" id="analyze" class="btn btn-info btn-lg" onclick=""
                                        style="width: 80%;">Detect Anomalies
                                </button>
                                <p></p>

                                <p>
                                    Able to Analysis and detect the outliers in the data collected by JFR.
                                </p>
                                <p><B>Features</B></p>
                                <P> * Outlier detection using machine learning techniques</P>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
            <!-- heap usage end-->
            <!-- heap usage start-->
            <div class="row" hidden id="subMenu">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            Analyze your JFR
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                             </span>
                        </header>
                        <div class="panel-body">
                            <div class="col-md-6">
                                <h4>Do you want to just find anomalous behaviour of your JFR without any training</h4>

                                <p>here you able to detect outliers using clustering</p>

                                <div>
                                    <button type="button" class="btn btn-info btn-lg"
                                            onclick="location.href='/cluster_file_upload'" style="width: 80%;">Use
                                        Clustering
                                    </button>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <h4>Do you want to train and find outliers in your JFR output</h4>

                                <p>It is better to train your own system for detect anomalous behaviour. In the case of this situation use this methods</p>

                                <div>
                                    <button type="button" class="btn btn-info btn-lg"
                                            id="ae" style="width: 80%;">Use Auto Encoder
                                    </button>
                                </div>
                                <div class="col-md-12" id="AE_options" hidden style="padding: 2%">
                                    <button type="button" class="btn btn-success btn-lg"
                                            id="ae_train_dropdown" style="width: 40%;">Train
                                    </button>
                                    <span>
                                        <button type="button" class="btn btn-warning btn-lg"
                                                id="ae_dropdown" style="width: 40%;">Analyse
                                        </button>
                                        </span>

                                    <div class="col-md-12" hidden id="option_pane_AE" style="padding: 2%">
                                        <form class="form-horizontal">
                                            <select class="form-control" style="width: 300px" id="system_Ae">
                                                <option value="app1">App 1</option>
                                                <option value="app2">App 2</option>
                                            </select>

                                            <p></p>
                                            <button type="button" class="btn btn-success"
                                                    onclick="load_file_upload_ae()">GO
                                            </button>
                                        </form>
                                    </div>
                                    <div class="col-md-12" hidden id="option_pane_train_AE" style="padding: 2%">
                                        <form class="form-horizontal">
                                            <select class="form-control" style="width: 300px" id="system_train_Ae">
                                                <option value="app1">App 1</option>
                                                <option value="app2">App 2</option>
                                            </select>

                                            <p></p>
                                            <button type="button" class="btn btn-success"
                                                    onclick="load_train_ae()">Train
                                            </button>
                                        </form>
                                    </div>
                                </div>
                                <p></p>

                                <p></p>

                                <%--<p>small description about clustering method</p>--%>

                                <div>
                                    <button type="button" class="btn btn-info btn-lg"
                                            id="hmm" style="width: 80%;">Use HMM
                                    </button>
                                </div>
                                <div class="col-md-12" id="HMM_options" style="padding: 2%" hidden>
                                    <button type="button" class="btn btn-success btn-lg"
                                            id="hmm_train_dropdown" style="width: 40%;">Train
                                    </button>
                                    <span>
                                        <button type="button" class="btn btn-warning btn-lg"
                                                id="hmm_dropdown" style="width: 40%;">Analyse
                                        </button>
                                    </span>

                                    <div class="col-md-12" hidden id="option_pane_HMM" style="padding: 2%">
                                        <form class="form-horizontal">
                                            <select class="form-control" style="width: 300px" id="system_Hmm">
                                                <option value="app1">App 1</option>
                                                <option value="app2">App 2</option>
                                            </select>

                                            <p></p>
                                            <button type="button" class="btn btn-success"
                                                    onclick="load_file_upload_hmm()">GO
                                            </button>
                                        </form>
                                    </div>
                                    <div class="col-md-12" hidden id="option_pane_train_HMM" style="padding: 2%">
                                        <form class="form-horizontal">
                                            <select class="form-control" style="width: 300px" id="system_train_Hmm">
                                                <option value="app1">App 1</option>
                                                <option value="app2">App 2</option>
                                            </select>

                                            <p></p>
                                            <button type="button" class="btn btn-success"
                                                    onclick="load_train_hmm()">Train
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
        </section>
        </div>
        </div>
        <!-- heap usage end-->


        <%--Anomaly region--%>
        <div class="row">

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


<!--common script init for all pages-->
<script src="../../resources/js/scripts.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#analyze").click(function () {
            $("#subMenu").show();
            document.getElementById('panel_body').style.display = 'none';
        });

        $("#ae").click(function () {
            $("#AE_options").show();
            $("#HMM_options").hide();
            $("#option_pane_HMM").hide();
            $("#option_pane_train_HMM").hide();
        });

        $("#hmm").click(function () {
            $("#AE_options").hide();
            $("#HMM_options").show();
            $("#option_pane_AE").hide();
            $("#option_pane_train_AE").hide();
        });

        $("#ae_dropdown").click(function () {
            $("#option_pane_AE").show();
            $("#option_pane_train_AE").hide();

        });


        $("#ae_train_dropdown").click(function () {
            $("#option_pane_train_AE").show();
            $("#option_pane_AE").hide();

        });

        $("#hmm_dropdown").click(function () {
            $("#option_pane_HMM").show();
            $("#option_pane_train_HMM").hide();

        });


        $("#hmm_train_dropdown").click(function () {
            $("#option_pane_train_HMM").show();
            $("#option_pane_HMM").hide();
        });
    });

    var load_file_upload_ae = function () {
        var sys = $('#system_Ae').val();
        var url = '/ae_file_upload';
        location.href = url;
    };

    var load_file_upload_hmm = function () {
        var sys = $('#system_Hmm').val();
        var url = '/hmm_file_upload';
        location.href = url;
    };


    var load_train_ae = function () {
        var sys = $('#system_train_ae').val();
        var url = '/ae_train_file_upload';
        location.href = url;
    };

    var load_train_hmm = function () {
        var sys = $('#system_train_hmm').val();
        var url = '/hmm_train_file_upload';
        location.href = url;
    };
</script>

</body>
</html>
