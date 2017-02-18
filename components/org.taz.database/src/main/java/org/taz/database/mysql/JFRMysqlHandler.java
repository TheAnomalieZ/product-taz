package org.taz.database.mysql;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IEventType;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.exceptions.AttributeNotFoundException;
import org.taz.commons.exceptions.EventNotFoundException;
import org.taz.commons.parser.JFRParserV18;
import org.taz.commons.parser.util.EventNode;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vithulan on 1/19/17.
 */
public class JFRMysqlHandler {

    JFRParserV18 jfrParser;
    MysqlConnector mysqlConnector;
    SQLQueryGenerator sqlQueryGenerator;

    public JFRMysqlHandler(String filePath, String host, String port, String databaseName) {
        jfrParser = new JFRParserV18(filePath);
        mysqlConnector = new MysqlConnector(host, port, databaseName);
        sqlQueryGenerator = new SQLQueryGenerator(databaseName);
    }

    private void createConnection() {
        mysqlConnector.createConnection();
    }

    public void closeConnection() {
        mysqlConnector.closeConnection();
    }

    public void generateDatabase() {
        createConnection();
        createTable();
        //insertParsedValues();
        insertIViewValues();
    }

    private void createTable() {
        ArrayList<EventNode> eventNodes = jfrParser.getAllJFRAttributes();
        String query = "";
        System.out.println("Starting create table");
        for (EventNode eventNode : eventNodes) {
            query = sqlQueryGenerator.getCreateTableQuery(eventNode.getEventName(),
                    new ArrayList<String>(eventNode.getAttributes()));
            //System.out.println(query);
            mysqlConnector.executeQuery(query);
        }
        //System.out.println(query);

       // System.out.println("finishing create table");
    }

    private void insertParsedValues() {
        ArrayList<EventNode> eventNodes = jfrParser.getAllJFRAttributes();
        for (EventNode eventNode : eventNodes) {
            try {
                Map<String, ArrayList<Object>> eventMap = jfrParser.getAttributeValues(eventNode.getEventName(),
                        new ArrayList<String>(eventNode.getAttributes()));
                if (eventMap.entrySet().size() != 0) {
                    ArrayList<String> attributes = new ArrayList<String>(eventMap.keySet());
                    ArrayList<String> valueList = new ArrayList<>();
                    //System.out.println(attributes.size());
                    int valueSize = eventMap.get(attributes.get(0)).size();
                    System.out.println(valueSize);
                    int i = 0;
                    for (i = 0; i < valueSize; i++) {
                        for (String attribute : attributes) {
                            if (eventMap.get(attribute).get(i) == null) {
                                valueList.add("NULL");
                            } else {
                                valueList.add(eventMap.get(attribute).get(i).toString());
                            }
                        }
                        String query = sqlQueryGenerator.getInsertionQuery(eventNode.getEventName(), attributes, valueList);
                        mysqlConnector.executeQuery(query);
                        valueList = new ArrayList<>();
                    }
                }

            } catch (EventNotFoundException e) {
                e.printStackTrace();
            } catch (AttributeNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertIViewValues() {
        System.out.println("Starting insertion process");
        IView iView = jfrParser.getIView();
        String query = "";
        for (IEvent event : iView) {
            IEventType iEventType = event.getEventType();
            ArrayList<String> attributes = new ArrayList<String>(iEventType.getFieldIdentifiers());
            ArrayList<String> values = new ArrayList<>();
            for (String attribute : attributes) {
                Object value = event.getValue(attribute);
                if (value == null) {
                    values.add("NULL");
                } else {
                    values.add(value.toString());
                }
            }
            query = sqlQueryGenerator.getInsertionQuery(iEventType.getName(),attributes,values);
            mysqlConnector.executeQuery(query);
            //System.out.println(query);
           // break;
        }
        /*System.out.println("Sending queries to DB");

        System.out.println("Finishing insertion");*/
    }

}
