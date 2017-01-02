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
            types: 'timeseries',
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
    zoom: {
        enabled: true
    },
    subchart: { show: true }
});

var cpuUsageLineChartRaw = [["x", "Soft reference", "Weak reference", "Final reference", "Phantom reference"]];
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
            label: 'Count'
        }
    },
    zoom: {
        enabled: true
    },
    subchart: { show: true }
});


var loadCpuUsageLineChart = function (value) {
    cpuUsageLineChartRaw = cpuUsageLineChartRaw.concat(value);
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


