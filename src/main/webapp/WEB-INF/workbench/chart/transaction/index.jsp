<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="/crm/jquery/ECharts/echarts.min.js"></script>
    <script src="/crm/jquery/jquery-1.11.1-min.js"></script>
</head>
<body>
<div id="main" style="width:800px;height:500px; float:left; margia-left:100px; margia-top:100px;"></div>
<div id="main2" style="width: 500px;height:500px; float: right;margin-right: 100px;margin-top: -450px"></div>
<div id="main3" style="width: 800px;height:600px; float: left;margin-left:200px;margin-top:150px;transform: scale(2,2)"></div>
<script>
    var myChart = echarts.init(document.getElementById('main'));
    var myChart2 = echarts.init(document.getElementById('main2'));
    var myChart3 = echarts.init(document.getElementById('main3'));
    $.get("/crm/workbench/chart/barVoEcharts",{

    },function (data) {
       var option = {
            xAxis: {

                type: 'category',
                data: data.titles
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    data: data.values,
                    type: 'bar',
                    showBackground: true,
                    backgroundStyle: {
                        color: 'rgba(180, 180, 180, 0.2)'

                    }
                }
            ]
        };

        myChart.setOption(option);
    },'json');


    $.get("/crm/workbench/chart/pieVoEcharts",{
    },function (data) {
        var option = {
            title: {
                text: 'Referer of a Website',
                subtext: 'Fake Data',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    name: 'Access From',
                    type: 'pie',
                    radius: '50%',
                    data:data,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart2.setOption(option);

    },'json');


    $.get("/crm/workbench/chart/pieVoEcharts",{
    },function (data) {
        var option = {
            legend: {
                top: 'bottom'
            },
            toolbox: {
                show: true,
                feature: {
                    mark: { show: true },
                    dataView: { show: true, readOnly: false },
                    restore: { show: true },
                    saveAsImage: { show: true }
                }
            },
            series: [
                {
                    name: 'Nightingale Chart',
                    type: 'pie',
                    radius: [50, 250],
                    center: ['50%', '50%'],
                    roseType: 'area',
                    itemStyle: {
                        borderRadius: 8
                    },
                    data: data
                }
            ]
        };
        myChart3.setOption(option);
    },'json');




</script>
</body>
</html>