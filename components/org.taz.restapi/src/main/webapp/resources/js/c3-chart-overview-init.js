//function for load heap usage gauge
var heap_usage = c3.generate({
    bindto: '#heap_usage',

    data: {
        columns: [
            ['data', 0.0]
        ],
        type: 'gauge'
    },
    gauge: {
        label: {
            format: function (value, ratio) {
                return value;
            },
            show: true // to turn off the min/max labels.
        },
        min: 0, // 0 is default, //can handle negative min e.g. vacuum / voltage / current flow / rate of change
        max: 100, // 100 is default
        units: 'MiB',
        width: 50 // for adjusting arc thickness
    },
    size: {
        height: 180
    }
});


//function for load CPU usage gauge
var cpu_usage = c3.generate({
    bindto: '#cpu-usage',

    data: {
        columns: [
            ['data', 0.0]
        ],
        type: 'gauge'
    },
    gauge: {
        label: {
            format: function (value, ratio) {
                return value;
            },
            show: true // to turn off the min/max labels.
        },
        min: 0, // 0 is default, //can handle negative min e.g. vacuum / voltage / current flow / rate of change
        max: 100, // 100 is default
        units: '%',
        width: 50 // for adjusting arc thickness
    },
    color: {
        pattern: ['#60B044', '#F6C600', '#F97600', '#FF0000'], // the three color levels for the percentage values.
        threshold: {
            unit: 'value', // percentage is default
            max: 100, // 100 is default
            values: [30, 60, 90, 100]
        }
    },
    size: {
        height: 180
    }
});


//function for load GC Pause Time gauge
var gc_pause_time = c3.generate({
    bindto: '#gc-pause-time',

    data: {
        columns: [
            ['data', 0.0]
        ],
        type: 'gauge'
    },
    gauge: {
        label: {
            format: function (value, ratio) {
                return value;
            },
            show: true // to turn off the min/max labels.
        },
        min: 0, // 0 is default, //can handle negative min e.g. vacuum / voltage / current flow / rate of change
        max: 100, // 100 is default
        units: 'ms',
        width: 50 // for adjusting arc thickness
    },
    size: {
        height: 180
    }
});


//Heap usage Line chart initialization
var heapUsageRows = [["x", "Committed Heap", "Used Heap"]];
var heapUsageLineChart = c3.generate({
    bindto: '#heap-usage-line-chart',
    data: {
        x: 'x',
        xFormat : "%H:%M:%S",
        rows: heapUsageRows
    },
    axis: {
        x: {
            type: 'timeseries',
            tick: {
                format: "%H:%M:%S" // https://github.com/mbostock/d3/wiki/Time-Formatting#wiki-format
            },
            label: 'time'
        },
        y: {
            min: 0,
            label: 'MB'
        }
    },
    point: {
        show: false
    },
    zoom: {
        enabled: true,
        onzoomstart: function (event) {
            console.log("onzoomstart", event);
        },
        onzoomend: function (domain) {
            console.log("onzoomend", domain);
        }
    },
    subchart: { show: true }
});

var cpuUsageLineChartRaw = [["x", "Machine Total", "JVM + Application (User)", "JVM + Application (Kernel)"]];

var cpu_usage_line_chart = c3.generate({
    bindto: '#cpu-usage-line-chart',
    data: {
        x: 'x',
        rows: cpuUsageLineChartRaw,
        type: 'area'
    },
    axis: {
        x: {
            type: 'timeseries',
            tick: {
                format: "%H:%M:%S" // https://github.com/mbostock/d3/wiki/Time-Formatting#wiki-format
            },
            label: 'time'
        },
        y: {
            min: 10,
            max: 90,
            label: '%'
        }
    },
    point: {
        show: false
    },
    zoom: {
        enabled: true,
        onzoomstart: function (event) {
            console.log("onzoomstart", event);
        },
        onzoomend: function (domain) {
            console.log("onzoomend", domain);
        }
    },
    subchart: { show: true }
});


var loadCpuUsageLineChart = function (value) {
    //cpuUsageLineChartRaw = cpuUsageLineChartRaw.concat(value.sort(function (a, b) {
    //    return a[0] - b[0];
    //}));
    cpuUsageLineChartRaw = cpuUsageLineChartRaw.concat(value);

    //cpu_usage_line_chart.axis.range({max: {y: 100}, min: {y: 0}});
    cpu_usage_line_chart.load({
        rows: cpuUsageLineChartRaw
    });
};

var loadHeapUsageLineChart = function (value) {
    heapUsageRows = heapUsageRows.concat(value);
    heapUsageLineChart.load({
        rows: heapUsageRows
    });
};

var loadHeapUsage = function (value, maxValue) {
    heap_usage.internal.config.gauge_max = maxValue;
    heap_usage.load({
        columns: [['data', value.toFixed(2)]]
    });
};

var loadCpuUsage = function (value) {
    cpu_usage.load({
        columns: [['data', value.toFixed(2)]]
    });
};

var loadGCPauseTime = function (value, maxValue) {
    gc_pause_time.internal.config.gauge_max = maxValue;
    gc_pause_time.load({
        columns: [['data', value.toFixed(2)]]
    });
};




