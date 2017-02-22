<%--
  Created by IntelliJ IDEA.
  User: K.Kokulan
  Date: 12/2/2016
  Time: 11:16 AM
  To change this template use File | Settings | File Templates.
--%>
<aside>
    <div id="sidebar" class="nav-collapse">
        <!-- sidebar menu start-->
        <div class="leftside-navigation">
            <ul class="sidebar-menu" id="nav-accordion">
                <li>
                    <a href="/home">
                        <i class="fa fa-home"></i>
                        <span>Home</span>
                    </a>
                </li>
                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-dashboard"></i>
                        <span>General</span>
                    </a>
                    <ul class="sub">
                        <li><a href="/overview?fileName=${fileName}">Overview</a></li>
                    </ul>
                    <ul class="sub">
                        <li><a href="/jvm_information?fileName=${fileName}">JVM Information</a></li>
                    </ul>
                    <ul class="sub">
                        <li><a href="/recording?fileName=${fileName}">Recording</a></li>
                    </ul>
                </li>

                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-hdd-o"></i>
                        <span>Memory</span>
                    </a>
                    <ul class="sub">
                        <li><a href="/garbage_collection?fileName=${fileName}">Garbage Collection</a></li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-laptop"></i>
                        <span>Analysis JFR</span>
                    </a>
                    <ul class="sub">
                        <li><a href="/cluster_analysis?fileName=${fileName}">Cluster Analysis</a></li>
                        <li><a href="/error">NN Analysis</a></li>
                        <li><a href="/error">HMM Analysis</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- sidebar menu end-->
    </div>
</aside>