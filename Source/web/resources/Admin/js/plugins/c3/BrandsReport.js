$(document).ready(function () {
    var numOfBrands = Number($('#numOfBrands').val());
    var income = ['Brands'];
    var names = [];

    for(var i = 1; i <= numOfBrands; i++) {
        income.push(Number($('#brand'+i).val()).toFixed(2));
        names.push($('#brand'+i).attr('name'));
    }

    c3.generate({
        bindto: '#stocked',
        data: {
            columns: [income],
            colors: {
                Brands: '#00e4d0'
            },
            type: 'bar',
            labels: {
                format: {
                    Brands: d3.format('$,')
                }
            },
            groups: [
                ['Brands']
            ]
        },
        axis: {
            x: {
                type: 'category',
                categories: names
            }
        }
    });
});
