package kz.kcell.rest.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import kz.kcell.rest.dto.LastValue;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BoundParameterQuery;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daniyar.artykov
 */
@RestController
public class SampleController {

    private static final String DATABASE_NAME = "aTimeSeries";

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveValue(@RequestBody Map<String, Object> params) {
        String value = null;

        if (params != null && params.containsKey("value")) {
            value = (String) params.get("value");
        }

        InfluxDB influxDB = connectToInfluxDB();
        Point point = Point
                .measurement("cpu")
                .tag("flag", "savedValue")
                .addField("savedVal", value)
                .addField("system", new Date().getTime()).
                build();
        influxDB.setDatabase(DATABASE_NAME);
        influxDB.write(point);
//        influxDB.deleteDatabase(DATABASE_NAME);

        return "ok";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public LastValue getLast() {
        InfluxDB influxDB = connectToInfluxDB();
        Query query = BoundParameterQuery.QueryBuilder.newQuery("SELECT * FROM cpu WHERE flag = $flag")
                .forDatabase(DATABASE_NAME)
                .bind("flag", "savedValue")
                .create();
        QueryResult queryResult = influxDB.query(query);
        String value = null;
        Date date = new Date();
        // iterate the results and print details
        for (QueryResult.Result result : queryResult.getResults()) {
            if (result.getSeries() != null) {
                List<List<Object>> values = result.getSeries().get(0).getValues();
                if (values != null && !values.isEmpty()) {
                    List<Object> objs = values.get(values.size() - 1); // last one
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
                    try {
                        date = df.parse((String) objs.get(0));
                    } catch (ParseException ex) {
                        // ignore
                    }
                    value = (String) objs.get(2);
                    break;
                }
            }
        }

//        influxDB.deleteDatabase(DATABASE_NAME);

        return new LastValue(Long.toString(date.getTime()), value);
    }

    private InfluxDB connectToInfluxDB() {
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086",
                "root", "root");
        influxDB.createDatabase(DATABASE_NAME);

        return influxDB;
    }

}
