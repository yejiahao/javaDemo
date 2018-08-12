package org.yejh.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InfluxDBClient {
    private static final Logger LOG = LoggerFactory.getLogger(InfluxDBClient.class);

    public static void main(String[] args) {
        InfluxDB influxDB = InfluxDBFactory.connect("http://192.168.2.196:8086", "admin", "admin");
        Pong pong = influxDB.ping();
        if (pong != null) {
            LOG.info(pong.toString());
        }
        List<String> list = influxDB.describeDatabases();
        LOG.info(list.toString());

        String dbName = "collectd";
        // influxDB.createDatabase(dbName);

        Query query = new Query("SELECT * FROM interface_tx WHERE type = 'if_packets' LIMIT 3", dbName);
        QueryResult result = influxDB.query(query);
        LOG.info(result.toString());
        List<List<Object>> values = result.getResults().get(0).getSeries().get(0).getValues();
        for (List<Object> value : values) {
            for (Object obj : value) {
                LOG.info(obj.getClass() + "\t" + obj);
            }
            LOG.info(value + "\n-------------------------------------------------------------------");

        }
    }
}
