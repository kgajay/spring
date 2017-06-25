package com.spring3.demo.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.Queue;

/**
 * @author ajay.kg created on 25/06/17.
 */
public class GettingStartedClient {

    public static void main(String[] args) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.addAddress("127.0.0.1:5701");
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap map = client.getMap("customers");
        System.out.println("Map Size:" + map.size());
        System.out.println("1: " + map.get(1));

        Queue<String> queueCustomers = client.getQueue("customers");
        System.out.println("queueCustomers " + queueCustomers.peek());
    }
}
