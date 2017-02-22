/**
 * Created by kokulan on 2/22/17.
 */

//anomaly score Line chart initialization
var anomalyScoreRows = [["x", "Anomaly Score"]];
var anomalyScoreLineChart = c3.generate({
    bindto: '#anomaly-score-line-chart',
    data: {
        x: 'x',
        rows: anomalyScoreRows
    },
    axis: {
        y: {
            min: -50,
            label: 'Score'
        }
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


