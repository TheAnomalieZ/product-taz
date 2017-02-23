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

var loadanomalyScoreLineChart = function (value) {
    anomalyScoreRows = anomalyScoreRows.concat(value);
    anomalyScoreLineChart.load({
        rows: anomalyScoreRows
    });
};

var zoomRange = function (min, max){
    anomalyScoreLineChart.zoom([min, max]);
};


