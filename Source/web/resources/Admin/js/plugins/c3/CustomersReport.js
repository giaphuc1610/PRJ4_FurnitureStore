$(document).ready(function () {
    var numOfCusts = Number($('#numOfCusts').val());
    var income = ['Customers'];
    var names = [];

    for (var i = 1; i <= numOfCusts; i++) {
        income.push(Number($('#cust' + i).val()).toFixed(2));
        names.push($('#cust' + i).attr('name'));
    }

    c3.generate({
        bindto: '#stocked',
        data: {
            columns: [income],
            colors: {
                Customers: '#00e4d0'
            },
            type: 'bar',
            labels: {
                format: {
                    Customers: d3.format('$,')
                }
            }
        },
        axis: {
            x: {
                type: 'category',
                categories: names
            }
        }
    });
});
