////大网件过滤脚
var run = function (data) {
            try{
                if(data == null || data.length == 0) {
                    return false;
                }
                var pattern;
                var request = JSON.parse(data);
                var request_source = request['request_source'];
                print('========filter========');
                print(data);
                if(request_source == 'DW_API'){
                    pattern = /^.*西湖.*$|^.*上城.*$|^.*下城.*$|^.*江干.*$|^.*滨江.*$|^.*拱墅.*$|^.*深圳.*$/;
                    print('dw');
                }else if(request_source == 'CX_API'){
                    pattern = /^.*上城.*$|^.*下城.*$|^.*深圳.*$/;
                    print('cx');
                }
                var conform = request['created_at'].substring(11,13) < 16
                            && request['packages'][0]['weight'] <= 50
                            && pattern.test(request['target']['address']['address'])
                            && pattern.test(request['source']['address']['address'])
                            && request['attributes']['distance'] <= 8000
                            && request['source']['coordinate']['detail']['precise'] == 1
                            && request['source']['coordinate']['detail']['confidence'] >= 80
                            && request['target']['coordinate']['detail']['precise'] == 1
                            && request['target']['coordinate']['detail']['confidence'] >= 80;
                if(conform) {
                    print('大网订单号：' + request['attributes']['appointmentno']);
                }
                return conform;
            } catch(err) {
                print('过滤脚本执行出现异常:' + err.message);
            }
            return false;
        }