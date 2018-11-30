var chart = Highcharts.chart('worker', {
    title: {
        text: '游客职业<br>占比',
        align: 'center',
        verticalAlign: 'middle',
        y: 50
    },
    tooltip: {
        headerFormat: '{series.name}<br>',
        pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
    },
    plotOptions: {
        pie: {
            dataLabels: {
                enabled: true,
                distance: -50,
                style: {
                    fontWeight: 'bold',
                    color: 'white',
                    textShadow: '0px 1px 2px black'
                }
            },
            startAngle: -90, // 圆环的开始角度
            endAngle: 90,    // 圆环的结束角度
            center: ['50%', '75%']
        }
    },
    series: [{
        type: 'pie',
        name: '浏览器占比',
        innerSize: '50%',
        data: [
            ['医生',   45.0],
            ['学生',       26.8],
            ['职员', 12.8],
            ['教师',    8.5],
            ['程序员',     6.2],
            {
                name: '其他',
                y: 0.7,
                dataLabels: {
                    // 数据比较少，没有空间显示数据标签，所以将其关闭
                    enabled: false
                }
            }
        ]
    }]
});
