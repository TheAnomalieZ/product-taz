<%--
  Created by IntelliJ IDEA.
  User: K.Kokulan
  Date: 12/1/2016
  Time: 5:21 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>Lock Screen</title>

    <!-- Bootstrap core CSS -->
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="../../resources/css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="../../resources/font-awesome/css/font-awesome.css" rel="stylesheet"/>
    <!-- Custom styles for this template -->
    <link href="../../resources/css/stylec4ca.css?1" rel="stylesheet">
    <link href="../../resources/css/style-responsive.css" rel="stylesheet"/>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
    <script src="../../resources/js/html5shiv.js"></script>
    <script src="../../resources/js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="lock-screen" onload="startTime()">

<div class="lock-wrapper">
    <div class="text-center">
        <h1 style="color: whitesmoke; font-size: 350%">Welcome to TAZ</h1>
    </div>
    <div id="time"></div>

    <div class="lock-box text-center">
        <a href="/home">
            <img src="../../resources/images/lock_thumb.jpg" alt="lock avatar"/>
        </a>
    </div>
</div>
<script>
    function startTime() {
        var today = new Date();
        var h = today.getHours();
        var m = today.getMinutes();
        var s = today.getSeconds();
        // add a zero in front of numbers<10
        m = checkTime(m);
        s = checkTime(s);
        document.getElementById('time').innerHTML = h + ":" + m + ":" + s;
        t = setTimeout(function () {
            startTime()
        }, 500);
    }

    function checkTime(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }
</script>
</body>
</html>

