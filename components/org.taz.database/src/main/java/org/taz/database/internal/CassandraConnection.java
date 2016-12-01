package org.taz.database.internal;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

/**
 * Created by vithulan on 11/30/16.
 */
/*
public class CassandraConnection {
    public void createKeySpace(String name) {
        String query = "CREATE KEYSPACE tp WITH replication "
                + "= {'class':'SimpleStrategy', 'replication_factor':1};";

        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.connect();
        session.execute(query);

    }
}
*/
public class CassandraConnection {

    /** Cassandra Cluster. */

    private Cluster cluster;

    /** Cassandra Session. */

    private Session session;

    /**

     * Connect to Cassandra Cluster specified by provided node IP

     * address and port number.

     *

     * @param node Cluster node IP address.

     * @param port Port of cluster host.

     */

    public void connect(final String node, final int port)

    {

        this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();

        final Metadata metadata = cluster.getMetadata();

        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());

        for (final Host host : metadata.getAllHosts())

        {

            System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",

                    host.getDatacenter(), host.getAddress(), host.getRack());

        }

        session = cluster.connect();

    }

    /**

     * Provide my Session.

     *

     * @return My session.

     */

    public Session getSession()

    {

        return this.session;

    }

    /** Close cluster. */

    public void close()

    {

        cluster.close();

    }

}
