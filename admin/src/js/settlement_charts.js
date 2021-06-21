function settlement_type (echart, id) {
  let st = echart.init(document.getElementById(id))
  let option = {
    color: ['#3398DB', '#9d2f8e', '#e8e8e8', '#121277'],
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: ['老师', '学生']
    },
    yAxis: {
      type: 'value',
    },
    series: [
      {
        data: [60 , 40],
        type: 'bar',
        barWidth: 30
      },
      {
        data: [10, 90],
        type: 'bar',
        barWidth: 30
      }
    ]
  }
  st.setOption(option)
}

function settlement_depart (echart, id) {
  let st = echart.init(document.getElementById(id))
  let option = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      x: '5%',
      icon: 'rect',
      itemWidth: 15,
      itemHeight: 15,
      y: 'center',
      textStyle: {
        color: '#000000'
      },
      data: ['计软', '机械', '电气', '商贸', '国教', '经管'],
      formatter: function (name) {
        let index = 0
        let clientlabels = ['计软', '机械', '电气', '商贸', '国教', '经管']
        let clientcounts = ['10%', '20%', '20%', '10%', '20%', '20%']
        clientlabels.forEach(function (value, i) {
          if (value === name) {
            index = i
          }
        })
        return name + '  ' + clientcounts[index]
      }
    },
    calculable: true,
    series: [
      {
        type: 'pie',
        center: ['60%', '50%'],
        radius: [10, '80%'],
        roseType: 'radius',
        label: {
          normal: {
            show: true
          },
          emphasis: {
            show: true
          }
        },
        lableLine: {
          normal: {
            show: false
          },
          emphasis: {
            show: true
          }
        },
        data: [
          {value: 10, name: '计软'},
          {value: 5, name: '机械'},
          {value: 15, name: '电气'},
          {value: 25, name: '商贸'},
          {value: 20, name: '国教'},
          {value: 35, name: '经管'},
        ]
      }
    ]
  }
  st.setOption(option)
}

function settlement_history (echart, id) {
  let st = echart.init(document.getElementById(id))
  let option = {
    color: ['#3398DB', '#694e9d', '#e8e8e8', '#121277'],
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: ['2015年', '2016年', '2017年', '2018年']
    },
    yAxis: {
      type: 'value',
    },
    series: [{
      data: [120, 140, 160, 220],
      type: 'bar',
      itemStyle: {
        normal: {
          color: function (params) {
            return option.color[params.dataIndex]
          },
          label: {
            show: true,
            position: 'top'
          }
        }
      },
      barWidth: 30
    }]
  }
  st.setOption(option)
}

function settlement_person (echart, id) {
  let st = echart.init(document.getElementById(id))
  let option = {
    color: ['#3398DB', '#694e9d', '#e8e8e8', '#121277'],
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: ['2015', '2016', '2017', '2018']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: [820, 932, 901, 934],
        type: 'line',
        smooth: true
      },
      {
        data: [220, 300, 400, 500],
        type: 'line',
        smooth: true
      }
    ]
  }
  st.setOption(option)
}

export default {
  settlement_type,
  settlement_depart,
  settlement_history,
  settlement_person
}
