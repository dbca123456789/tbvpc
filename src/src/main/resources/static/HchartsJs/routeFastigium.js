var chart = Highcharts.chart('routeFastigium', {
    chart: {
        type: 'area'
    },
    title: {
        text: '路线交通流量峰值变化'
    },
    subtitle: {
        text: null// '数据来源: Wikipedia.org'
    },
    xAxis: {
        categories: ['5月', '6月', '7月', '8月', '9月', '10月', '11月'],
        tickmarkPlacement: 'on',
        title: {
            enabled: false
        }
    },
    yAxis: {
        title: {
            text: '百分比'
        }
    },
    tooltip: {
        pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.percentage:.1f}%</b> ({point.y:,.0f} 百万)<br/>',
        shared: true
    },
    plotOptions: {
        area: {
            stacking: 'percent',
            lineColor: '#ffffff',
            lineWidth: 1,
            marker: {
                lineWidth: 1,
                lineColor: '#ffffff'
            }
        }
    },
    series: [{
        name: '西湖',
        data: [302, 335, 209, 547, 902, 834, 1268]
    }, {
        name: '大堰湖',
        data: [106, 107, 111, 133, 221, 767, 1766]
    }, {
        name: '长江',
        data: [163, 203, 276, 408, 547, 729, 628]
    }, {
        name: '三峡',
        data: [218, 131, 54, 156, 339, 818, 1201]
    }, {
        name: '青海',
        data: [120, 32, 224, 396, 103, 130, 146]
    },{
        name: '天安门',
        data: [118, 131, 154, 156, 339, 818, 1201]
    }]
});