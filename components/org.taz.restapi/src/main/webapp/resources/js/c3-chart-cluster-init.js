/**
 * Created by K.Kokulan on 2/21/2017.
 */

//anomaly score Line chart initialization
var anomalyScoreRows = [["x", "Anomaly Score", "Threshold"]];
var anomalyScoreLineChart = c3.generate({
    bindto: '#anomaly-score-line-chart',
    data: {
        x: 'x',
        rows: anomalyScoreRows
    },
    axis: {
        y: {
            min: 0,
            label: 'Score'
        }
    },
    point: {
         show: false
    },
    zoom: {
        enabled: true
    },
    subchart: { show: true }
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

var loadHeapUsageLineChart = function (value) {
    heapUsageRows = heapUsageRows.concat(value);
    heapUsageLineChart.load({
        rows: heapUsageRows
    });
};

var loadanomalyScoreLineChart = function (value) {
    anomalyScoreRows = anomalyScoreRows.concat(value);
    anomalyScoreLineChart.load({
        rows: anomalyScoreRows
    });
};

var zoomRange = function (min, max){
    anomalyScoreLineChart.zoom([min, max]);
};


