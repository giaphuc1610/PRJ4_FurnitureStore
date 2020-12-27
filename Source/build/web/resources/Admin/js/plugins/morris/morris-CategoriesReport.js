$(document).ready(function() {
    if(!$('morris-donut-chart'))
        return;
    
    var numOfCats = Number($('#numOfCats').val());
    var colorsSource = ['#00e4d0', '#5983e8', '#165b55', '#9ed336', '#802510', '#ff40aa', '#71ba4c'];
    var dataSource = [];
    
    for(var i = 1; i <= numOfCats; i++)
        dataSource.push({label: $('#cat'+i).attr('name'), value: Number($('#cat'+i).val())});
    
    Morris.Donut({
        element: 'morris-donut-chart',
        data: dataSource,
        resize: true,
        colors: colorsSource,
        formatter: function (y, data) { return '$' + y.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,'); }
    }).select(0);
});

