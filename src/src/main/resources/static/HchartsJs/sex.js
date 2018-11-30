$(function () {
	$(document).ready(function () {
        var chart = Highcharts.chart('sex', {
            chart: {
                // spacing : [50, 0 , 50, 0],
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            colors: [
                '#7cb5ec',
                '#f15c80'
            ],
            title: {
                // floating:true,
                text:null// '游客性别比例',
                // align: 'left',
                // verticalAlign: 'top'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black', fontSize:'16px'
                        }
                    },
                },
                showInLegend:true
            },
            legend: {
                layout: 'horizontal',
                align: 'center',
                verticalAlign: 'bottom',
                // borderWidth: 0
            },
            //图表要展现的数据
            series: [{
                type: 'pie',
                innerSize: '60%',
                name: '占比',
                // data: [
                //     // {name:'男性',   y: 45.0, url : ''},
                //     ['男性', 45.0],
                //     ['女性', 55.0],
                // ]
            }],
            credits: {
                enabled:false
            }
        }, function(c) { // 图表初始化完毕后的回调函数
            // 环形图圆心
            var centerY = c.series[0].center[1],
                titleHeight = parseInt(c.title.styles.fontSize);
            // 动态设置标题位置
            // c.setTitle({
            // 	y:centerY + titleHeight/2
            // });
        });

        //异步请求数据
		$.ajax({
			type:"GET",
			url:'userJson/getSexJson',
            dataType:'json',
			success:function (data) {
			    // alert(data.status);
                sexCount = [];
                sexCount.push(['男性', data["boy"]]);
                sexCount.push(['女性', data["girl"]]);
				//设置数据
				chart.series[0].setData(sexCount);
            },
			error:function(e){
				alert(e);
		}
		});
    })
})