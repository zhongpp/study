package com.zpp.java8.samples.nashorn;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;

/**
 * @author pingpingZhong
 *         Date: 2017/10/18
 *         Time: 15:08
 */
public class ControllerRequestNumber {

    public static void main(String[] args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        File file = FileUtil.findFile(Nashorn1.class, "filterRequest.js");

        engine.eval(new FileReader(file));

        Invocable invocable = (Invocable) engine;

        Object result = invocable.invokeFunction("run", param1);

        System.out.println(result);
    }


    static String param1 = "{\n" +
            "\t\t\"uuid\": \"2c9280835f9470f2015f952c5aaf6c4d\",\n" +
            "\t\t\"request_num\": \"DB0724421969\",\n" +
            "\t\t\"region\": \"2c9280835dbc20d4015dd74750991e85\",\n" +
            "\t\t\"eta\": \"90\",\n" +
            "\t\t\"location\": {\n" +
            "\t\t},\n" +
            "\t\t\"source\": {\n" +
            "\t\t\t\"address\": {\n" +
            "\t\t\t\t\"country\": \"中国\",\n" +
            "\t\t\t\t\"province\": \"浙江省\",\n" +
            "\t\t\t\t\"city\": \"杭州市\",\n" +
            "\t\t\t\t\"region\": \"江干区\",\n" +
            "\t\t\t\t\"street\": \"凯旋路445号物产国际广场20楼H座博爵学院\",\n" +
            "\t\t\t\t\"receiver\": \"叶叶\",\n" +
            "\t\t\t\t\"mobile\": \"15167156628\",\n" +
            "\t\t\t\t\"address\": \"中国浙江省杭州市上城区凯旋路445号物产国际广场20楼H座博爵学院\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"coordinate\": {\n" +
            "\t\t\t\t\"standard\": \"BD09LL\",\n" +
            "\t\t\t\t\"longitude\": 120.19649943709466,\n" +
            "\t\t\t\t\"latitude\": 30.28009077163951,\n" +
            "\t\t\t\t\"timestamp\": \"2017-11-07T14:30:12.379+0800\",\n" +
            "\t\t\t\t\"detail\": {\n" +
            "\t\t\t\t\t\"level\": \"商务大厦\",\n" +
            "\t\t\t\t\t\"precise\": 1,\n" +
            "\t\t\t\t\t\"confidence\": 80\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t},\n" +
            "\t\t\"target\": {\n" +
            "\t\t\t\"address\": {\n" +
            "\t\t\t\t\"country\": \"中国\",\n" +
            "\t\t\t\t\"province\": \"浙江省\",\n" +
            "\t\t\t\t\"city\": \"杭州市\",\n" +
            "\t\t\t\t\"region\": \"西湖区\",\n" +
            "\t\t\t\t\"street\": \"万塘路252号计量大厦705\",\n" +
            "\t\t\t\t\"receiver\": \"王老师\",\n" +
            "\t\t\t\t\"mobile\": \"13968159594\",\n" +
            "\t\t\t\t\"address\": \"中国浙江省杭州市上城区万塘路252号计量大厦705\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"coordinate\": {\n" +
            "\t\t\t\t\"standard\": \"BD09LL\",\n" +
            "\t\t\t\t\"longitude\": 120.1320610570829,\n" +
            "\t\t\t\t\"latitude\": 30.28370940166673,\n" +
            "\t\t\t\t\"timestamp\": \"2017-11-07T14:30:12.405+0800\",\n" +
            "\t\t\t\t\"detail\": {\n" +
            "\t\t\t\t\t\"level\": \"商务大厦\",\n" +
            "\t\t\t\t\t\"precise\": 1,\n" +
            "\t\t\t\t\t\"confidence\": 80\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t},\n" +
            "\t\t\"quote\": {\n" +
            "\t\t\t\"uuid\": \"2c9280835f9470f2015f952c4f416c3d\",\n" +
            "\t\t\t\"region\": \"2c9280835dbc20d4015dd74750991e85\",\n" +
            "\t\t\t\"pick_up\": {\n" +
            "\t\t\t\t\"address\": {\n" +
            "\t\t\t\t\t\"country\": \"中国\",\n" +
            "\t\t\t\t\t\"province\": \"浙江省\",\n" +
            "\t\t\t\t\t\"city\": \"杭州市\",\n" +
            "\t\t\t\t\t\"region\": \"江干区\",\n" +
            "\t\t\t\t\t\"street\": \"凯旋路445号物产国际广场20楼H座博爵学院\",\n" +
            "\t\t\t\t\t\"receiver\": \"叶叶\",\n" +
            "\t\t\t\t\t\"mobile\": \"15167156628\",\n" +
            "\t\t\t\t\t\"address\": \"中国浙江省杭州市江干区凯旋路445号物产国际广场20楼H座博爵学院\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"coordinate\": {\n" +
            "\t\t\t\t\t\"standard\": \"BD09LL\",\n" +
            "\t\t\t\t\t\"longitude\": 120.19649943709466,\n" +
            "\t\t\t\t\t\"latitude\": 30.28009077163951,\n" +
            "\t\t\t\t\t\"timestamp\": \"2017-11-07T14:30:12.379+0800\",\n" +
            "\t\t\t\t\t\"detail\": {\n" +
            "\t\t\t\t\t\t\"level\": \"商务大厦\",\n" +
            "\t\t\t\t\t\t\"precise\": 1,\n" +
            "\t\t\t\t\t\t\"confidence\": 80\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t\"drop_off\": {\n" +
            "\t\t\t\t\"address\": {\n" +
            "\t\t\t\t\t\"country\": \"中国\",\n" +
            "\t\t\t\t\t\"province\": \"浙江省\",\n" +
            "\t\t\t\t\t\"city\": \"杭州市\",\n" +
            "\t\t\t\t\t\"region\": \"西湖区\",\n" +
            "\t\t\t\t\t\"street\": \"万塘路252号计量大厦705\",\n" +
            "\t\t\t\t\t\"receiver\": \"王老师\",\n" +
            "\t\t\t\t\t\"mobile\": \"13968159594\",\n" +
            "\t\t\t\t\t\"address\": \"中国浙江省杭州市西湖区万塘路252号计量大厦705\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"coordinate\": {\n" +
            "\t\t\t\t\t\"standard\": \"BD09LL\",\n" +
            "\t\t\t\t\t\"longitude\": 120.1320610570829,\n" +
            "\t\t\t\t\t\"latitude\": 30.28370940166673,\n" +
            "\t\t\t\t\t\"timestamp\": \"2017-11-07T14:30:12.405+0800\",\n" +
            "\t\t\t\t\t\"detail\": {\n" +
            "\t\t\t\t\t\t\"level\": \"商务大厦\",\n" +
            "\t\t\t\t\t\t\"precise\": 1,\n" +
            "\t\t\t\t\t\t\"confidence\": 80\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t\"vehicle_type\": \"BIKE\",\n" +
            "\t\t\t\"package_type\": \"FILE\",\n" +
            "\t\t\t\"eta\": \"90\",\n" +
            "\t\t\t\"price\": 22.0,\n" +
            "\t\t\t\"real_price\": 12.0\n" +
            "\t\t},\n" +
            "\t\t\"merchant\": {\n" +
            "\t\t\t\"uuid\": \"2c9280835f9470f2015f952c0b3a6c1c\",\n" +
            "\t\t\t\"name\": \"Merchant_bf6f\",\n" +
            "\t\t\t\"account_type\": \"MOBILE\",\n" +
            "\t\t\t\"mobile\": \"15167156628\",\n" +
            "\t\t\t\"avatar\": \"http://od4jhik93.bkt.clouddn.com/default_avatar.jpg\",\n" +
            "\t\t\t\"status\": \"SIGNED_CONTRACT\",\n" +
            "\t\t\t\"summary\": {\n" +
            "\t\t\t\t\"grade\": 0.0,\n" +
            "\t\t\t\t\"total\": 0,\n" +
            "\t\t\t\t\"five_star\": 0,\n" +
            "\t\t\t\t\"rated\": 0,\n" +
            "\t\t\t\t\"commented\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t\"attributes\": {\n" +
            "\t\t\t\t\"wxopenid\": \"o8Uz80F-wO7e7ZcjXfhF_wtK6IsY\",\n" +
            "\t\t\t\t\"pay_monthly\": \"false\",\n" +
            "\t\t\t\t\"with_password\": \"false\",\n" +
            "\t\t\t\t\"last_device_uuid\": null,\n" +
            "\t\t\t\t\"working_department\": \"default\",\n" +
            "\t\t\t\t\"monthly_account_existed\": \"false\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"tags\": [\n" +
            "\t\t\t],\n" +
            "\t\t\t\"created_at\": \"2017-11-07T14:30:13.000+0800\"\n" +
            "\t\t},\n" +
            "\t\t\"status\": \"CANCELED\",\n" +
            "\t\t\"attributes\": {\n" +
            "\t\t\t\"eta\": \"3156\",\n" +
            "\t\t\t\"txid\": \"03499110714300605110924167\",\n" +
            "\t\t\t\"status\": \"CANCELED\",\n" +
            "\t\t\t\"ongoing\": \"201\",\n" +
            "\t\t\t\"payType\": \"FREIGHT_DELAYED\",\n" +
            "\t\t\t\"distance\": \"7310\",\n" +
            "\t\t\t\"arrival_time\": \"2017-11-07T16:00:32.000+0800\",\n" +
            "\t\t\t\"appointmentno\": \"CX2017110703133050\",\n" +
            "\t\t\t\"firehouse_uuid\": null,\n" +
            "\t\t\t\"oms_down_price\": \"12\",\n" +
            "\t\t\t\"abnormal_option\": \"DISPATCH_TIME_OUT\",\n" +
            "\t\t\t\"department_code\": \"专送开通下单区域\",\n" +
            "\t\t\t\"department_name\": \"杭州六大区\",\n" +
            "\t\t\t\"handover_ongoing\": \"00:04:42\",\n" +
            "\t\t\t\"pre_warning_status\": \"CANCELED\",\n" +
            "\t\t\t\"predict_hand_over_at\": \"2017-11-07T15:00:54.276+08:00\"\n" +
            "\t\t},\n" +
            "\t\t\"packages\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"uuid\": \"2c9280835f9470f2015f952c5aaf6c4c\",\n" +
            "\t\t\t\t\"region\": \"2c9280835dbc20d4015dd74750991e85\",\n" +
            "\t\t\t\t\"type\": \"FILE\",\n" +
            "\t\t\t\t\"source\": {\n" +
            "\t\t\t\t\t\"address\": {\n" +
            "\t\t\t\t\t\t\"country\": \"中国\",\n" +
            "\t\t\t\t\t\t\"province\": \"浙江省\",\n" +
            "\t\t\t\t\t\t\"city\": \"杭州市\",\n" +
            "\t\t\t\t\t\t\"region\": \"江干区\",\n" +
            "\t\t\t\t\t\t\"street\": \"凯旋路445号物产国际广场20楼H座博爵学院\",\n" +
            "\t\t\t\t\t\t\"receiver\": \"叶叶\",\n" +
            "\t\t\t\t\t\t\"mobile\": \"15167156628\",\n" +
            "\t\t\t\t\t\t\"address\": \"中国浙江省杭州市上城区凯旋路445号物产国际广场20楼H座博爵学院\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t\"coordinate\": {\n" +
            "\t\t\t\t\t\t\"standard\": \"BD09LL\",\n" +
            "\t\t\t\t\t\t\"longitude\": 120.19649943709466,\n" +
            "\t\t\t\t\t\t\"latitude\": 30.28009077163951,\n" +
            "\t\t\t\t\t\t\"timestamp\": \"2017-11-07T14:30:12.379+0800\",\n" +
            "\t\t\t\t\t\t\"detail\": {\n" +
            "\t\t\t\t\t\t\t\"level\": \"商务大厦\",\n" +
            "\t\t\t\t\t\t\t\"precise\": 1,\n" +
            "\t\t\t\t\t\t\t\"confidence\": 80\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"target\": {\n" +
            "\t\t\t\t\t\"address\": {\n" +
            "\t\t\t\t\t\t\"country\": \"中国\",\n" +
            "\t\t\t\t\t\t\"province\": \"浙江省\",\n" +
            "\t\t\t\t\t\t\"city\": \"杭州市\",\n" +
            "\t\t\t\t\t\t\"region\": \"西湖区\",\n" +
            "\t\t\t\t\t\t\"street\": \"万塘路252号计量大厦705\",\n" +
            "\t\t\t\t\t\t\"receiver\": \"王老师\",\n" +
            "\t\t\t\t\t\t\"mobile\": \"13968159594\",\n" +
            "\t\t\t\t\t\t\"address\": \"中国浙江省杭州市上城区万塘路252号计量大厦705\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t\"coordinate\": {\n" +
            "\t\t\t\t\t\t\"standard\": \"BD09LL\",\n" +
            "\t\t\t\t\t\t\"longitude\": 120.1320610570829,\n" +
            "\t\t\t\t\t\t\"latitude\": 30.28370940166673,\n" +
            "\t\t\t\t\t\t\"timestamp\": \"2017-11-07T14:30:12.405+0800\",\n" +
            "\t\t\t\t\t\t\"detail\": {\n" +
            "\t\t\t\t\t\t\t\"level\": \"商务大厦\",\n" +
            "\t\t\t\t\t\t\t\"precise\": 1,\n" +
            "\t\t\t\t\t\t\t\"confidence\": 80\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"eta\": \"90\",\n" +
            "\t\t\t\t\"weight\": 1.0,\n" +
            "\t\t\t\t\"status\": \"INIT\",\n" +
            "\t\t\t\t\"item_amount\": 1,\n" +
            "\t\t\t\t\"comments\": \"文件\",\n" +
            "\t\t\t\t\"capacity\": 1\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"created_at\": \"2017-11-07T14:30:32.000+0800\",\n" +
            "\t\t\"updated_at\": \"2017-11-07T15:00:09.000+0800\",\n" +
            "\t\t\"finished_at\": \"2017-11-07T15:00:09.000+0800\",\n" +
            "\t\t\"vehicle_specified\": false,\n" +
            "\t\t\"payed\": false,\n" +
            "\t\t\"type\": \"NORMAL\",\n" +
            "\t\t\"request_source\": \"CX_API\",\n" +
            "\t\t\"pay_type\": \"FREIGHT_DELAYED\",\n" +
            "\t\t\"product_type\": \"KUAISUDA\",\n" +
            "\t\t\"checksum\": \"d264dc13ad8202db70af9c3c40f6b249\",\n" +
            "\t\t\"pickUpTimeExceeded\": 0,\n" +
            "\t\t\"dropOffTimeExceeded\": 0,\n" +
            "\t\t\"firstDispatchTime\": \"1970-01-01T08:00:00.000+0800\"\n" +
            "\t}";

}
