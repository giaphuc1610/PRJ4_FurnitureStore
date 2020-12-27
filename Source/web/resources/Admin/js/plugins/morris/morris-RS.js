$(document).ready(function () {
    if(!$('morris-area-chart'))
        return;
    
    var iters = Number($('#iters').val());
    var data = [];
    
    for(var i = 1; i <= iters; i++) {
        data.push({period: i+'', Cost: (Number($('#cost'+i).val())).toFixed(2)});
    }
    
    Morris.Area({
        element: 'morris-area-chart',
        data: data,
        xkey: 'period',
        ykeys: ['Cost'],
        labels: ['Cost'],
        hideHover: 'auto',
        resize: true,
        lineColors: ['#87d6c6'],
        parseTime: false,
        smooth: false,
        pointSize: 3
    });
    
    if(!$('morris-line-chart'))
        return;
    
    var numFeatures = Number($('#rsForm\\:numFeatures').val());
    var errorData = [];
    
    for(var i = 1; i <= numFeatures; i++) {
        errorData.push({Error: i+'', a: (Number($('#errorTraining'+i).val())).toFixed(2), b: (Number($('#errorCV'+i).val())).toFixed(2)});
    }
    
    Morris.Line({
        element: 'morris-line-chart',
        data: errorData,
        xkey: 'Error',
        ykeys: ['a', 'b'],
        labels: ['Training', 'Cross Validation'],
        hideHover: 'auto',
        resize: true,
        lineColors: ['#FF5733','#FFBD33'],
        parseTime: false
    });
});
