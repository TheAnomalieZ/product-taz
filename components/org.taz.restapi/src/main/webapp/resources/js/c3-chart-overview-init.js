//function for load heap usage gauge
var heap_usage = c3.generate({
    bindto: '#heap_usage',

    data: {
        columns: [
            ['data', 0.0]
        ],
        type: 'gauge',
        onclick: function (d, i) {
            console.log("onclick", d, i);
        },
        onmouseover: function (d, i) {
            console.log("onmouseover", d, i);
        },
        onmouseout: function (d, i) {
            console.log("onmouseout", d, i);
        }
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


//function for load CPU usage gauge
var cpu_usage = c3.generate({
    bindto: '#cpu-usage',

    data: {
        columns: [
            ['data', 0.0]
        ],
        type: 'gauge',
        onclick: function (d, i) {
            console.log("onclick", d, i);
        },
        onmouseover: function (d, i) {
            console.log("onmouseover", d, i);
        },
        onmouseout: function (d, i) {
            console.log("onmouseout", d, i);
        }
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
        type: 'gauge',
        onclick: function (d, i) {
            console.log("onclick", d, i);
        },
        onmouseover: function (d, i) {
            console.log("onmouseover", d, i);
        },
        onmouseout: function (d, i) {
            console.log("onmouseout", d, i);
        }
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


//Heap usage Line chart initialization
var heapUsageRows = [["x", "Committed Heap", "Used Heap"]];
var heapUsageLineChart = c3.generate({
    bindto: '#heap-usage-line-chart',
    data: {
        x: 'x',
        rows: heapUsageRows
    },
    axis: {
        x: {
            type: 'timeseries',
            tick: {
                format: "%H:%M:%S" // https://github.com/mbostock/d3/wiki/Time-Formatting#wiki-format
            }
        }
    }
});


var loadHeapUsageLineChart = function (value) {
    heapUsageRows = heapUsageRows.concat(value.sort(function (a, b) {
        return a[0] - b[0];
    }));

    heapUsageLineChart.load({
        rows: heapUsageRows
    });
};

var loadHeapUsage = function (value) {
    heap_usage.load({
        columns: [['data', value]]
    });
};

var loadCpuUsage = function (value) {
    cpu_usage.load({
        columns: [['data', value]]
    });
};

var loadGCPauseTime = function (value) {
    gc_pause_time.load({
        columns: [['data', value]]
    });
};




