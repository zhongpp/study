/*
var max = 10;
var run = function (data,datas) {
           if(data == null || data.length == 0 || datas == null || datas.length == 0) {
                return false;
            }
           var request = JSON.parse(data);
           var requests = JSON.parse(datas);
           for(var i = 0; i < requests.length; i++){
               if(request['uuid'] == requests[i]['uuid'] && i <  max){
                    return true;
               }
           }
           return false;
         }
*/

//每小时n单筛选
var max = 6;
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
var run = function (timeStamps,timeStamp) {
    var hours_max = 25;
    var day_max = 1000;
    var minute_max = 6
    try{
        if(timeStamps == null || timeStamps.length == 0) {
            return true;
        }
        var currentDate = timeStamp != null && timeStamp.length != 0 ? new Date(parseInt(timeStamp)) : new Date();
        var currentStr = currentDate.format("yyyy-MM-dd hh:mm:ss");
        print(currentStr);
        var timeStampArr = timeStamps.split(',');
        if(timeStampArr.length >= day_max) {
            return false;
        }
        var acceptSize = 0;
        for (var time in timeStampArr) {
            if(timeStampArr[time] == null || timeStampArr[time].length == 0) {
               continue;
            }
            var dateStr = new Date(parseInt(timeStampArr[time])).format("yyyy-MM-dd hh:mm:ss");
            print(dateStr);
            if(currentStr.substr(0,15) == dateStr.substr(0,15)) {
               acceptSize++ ;
            }
        }
        print(acceptSize);
        if(acceptSize >= minute_max) {
            return false;
        }
        return true;
    } catch(err) {
        print('流量控制出现错误: ' + err.message);
    }
    return false;
}
