<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: K.Kokulan
  Date: 11/26/2016
  Time: 11:39 AM
  To change this template use File | Settings | File Templates.
--%>
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

    <!--C3 Chart-->
    <link href="../../resources/js/c3-chart/c3.css" rel="stylesheet"/>

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

            <a href="" class="logo">
                <img src="../../resources/images/logo_web.png" alt="">
            </a>
        </div>
        <!--logo end-->

    </header>
    <!--header end-->

    <!--main content start-->
    <section id="main-content" class="merge-left">
        <section class="wrapper">
            <!-- page start-->

            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <header class="panel-heading">
                            ${chart_title}
                        </header>
                        <div class="panel-body">

                            <div class="chart">
                                <div id="chart"></div>
                            </div>

                        </div>
                    </section>
                </div>
            </div>

            <!-- page end-->
        </section>

        <form id="upload_file">
            <div class="position-center">
                <div class="form-group">
                    <label for="input_file">File input</label>
                    <input type="file" id="input_file" name="file" required>

                    <p class="help-block">Max file size: 20Mb</p>
                </div>
            </div>
        </form>
        <div class="col-sm-offset-1">
            <button type="button" onclick="submitAll()" class="btn btn-info">Submit</button>
        </div>
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
<!--Easy Pie Chart-->
<script src="../../resources/js/easypiechart/jquery.easypiechart.js"></script>
<!--Sparkline Chart-->
<script src="../../resources/js/sparkline/jquery.sparkline.js"></script>
<!--jQuery Flot Chart-->
<script src="../../resources/js/flot-chart/jquery.flot.js"></script>
<script src="../../resources/js/flot-chart/jquery.flot.tooltip.min.js"></script>
<script src="../../resources/js/flot-chart/jquery.flot.resize.js"></script>
<script src="../../resources/js/flot-chart/jquery.flot.pie.resize.js"></script>
<!--C3 Chart-->
<script src="../../resources/js/d3js.org/d3.v3.min.js"></script>
<script src="../../resources/js/c3-chart/c3.js"></script>
<script src="../../resources/js/c3-chart.init.js"></script>


<!--common script init for all pages-->
<script src="../../resources/js/scripts.js"></script>

<script type="text/javascript">

    function submitAll() {
        var form = $('#upload_file');
        if (form[0].checkValidity()) {
            uploadJFR();
        } else {
            form.submit();
        }
    }

    function uploadJFR() {
        if ($('#input_file').val()) {
            $.ajax({
                type: 'POST',
                url: 'file/uploadFile',
                data: new FormData($('#upload_file')[0]),
                async: false,
                cache: false,
                dataType: "json",
                contentType: false,
                processData: false,
                success: function (data) {
                    console.log(data);
                    if (data.statusCode == "S1000") {
                        $('#upload_file').submit();
                    } else {
                        console.log("Error with Status Code:" + data.statusCode);
                    }
                },
                error: function (data) {
                    console.log("error");
                }
            });
        } else {
            alert("You Should Upload a JFR")
        }
    }

</script>

</body>
</html>
