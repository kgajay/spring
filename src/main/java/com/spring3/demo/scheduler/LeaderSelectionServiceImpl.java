package com.spring3.demo.scheduler;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.retry.ExponentialBackoffRetry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;

/**
 * @author ajay.kg created on 11/06/17.
 */
@Slf4j
public class LeaderSelectionServiceImpl implements LeaderSelectionService {


    private CuratorFramework client;
    private Map<String, Runnable> runnableRegistry;
    private Map<String, LeaderSelectorData> leaderSelectorRegistry;


    public LeaderSelectionServiceImpl(String zooKeeperConnectionString) {
        client = CuratorFrameworkFactory.newClient(zooKeeperConnectionString, new ExponentialBackoffRetry(1000, 3));
        runnableRegistry = Maps.newHashMap();
        leaderSelectorRegistry = Maps.newHashMap();
    }

    @Override
    public void registerRunnableOnLeaderSelection(final String leaderPath, Runnable runnableOnLeaderSelection) {
        runnableRegistry.put(leaderPath, runnableOnLeaderSelection);
    }


    @Override
    public void selectLeader(String leaderPath) {

        System.out.println("select leader " + leaderPath);
        try{
            if(leaderSelectorRegistry.containsKey(leaderPath)){
                leaderSelectorRegistry.get(leaderPath).setLocalJobCount(leaderSelectorRegistry.get(leaderPath).getJobStateCounter().getCount());
                leaderSelectorRegistry.get(leaderPath).getLeaderSelector().requeue();
            }else if(runnableRegistry.containsKey(leaderPath)){
                final SharedCount sharedCount = new SharedCount(client, leaderPath+"Counter", 0);
                final LeaderSelector selector = new LeaderSelector(client, leaderPath, new LeaderSelectorListenerAdapter(){
                    public void takeLeadership(CuratorFramework arg0) throws Exception {
                        final Integer expected = leaderSelectorRegistry.get(leaderPath).getLocalJobCount();
                        final Integer actual = leaderSelectorRegistry.get(leaderPath).getJobStateCounter().getCount();
                        if(Objects.equal(expected, actual)){
                            log.info(String.format("ExecutingAsLeader - %s", leaderPath));
                            System.out.println("ExecutingAsLeader - " + leaderPath);
                            runnableRegistry.get(leaderPath).run();
                            leaderSelectorRegistry.get(leaderPath).getJobStateCounter().setCount(actual + 1);
                        }else{
                            log.info(String.format("%s has already been executed successfully", leaderPath));
                        }
                    }
                });
                sharedCount.start();
                leaderSelectorRegistry.put(leaderPath, new LeaderSelectorData(selector, sharedCount,sharedCount.getCount()));
                selector.start();
            }else{
                throw new RuntimeException("No runnable registered for execution on leader selection");
            }
        } catch (Exception ex) {
            log.error("Error in Leader Selection for {}", leaderPath, ex);
        }
    }

    @PostConstruct
    public void init(){
        client.start();
    }

    @PreDestroy
    public void cleanUp() throws IOException {
        for (LeaderSelectorData leaderSelector : leaderSelectorRegistry.values()) {
            leaderSelector.getLeaderSelector().close();
            leaderSelector.getJobStateCounter().close();
        }
        leaderSelectorRegistry.clear();
        runnableRegistry.clear();
        client.close();
    }

    private class LeaderSelectorData{
        private LeaderSelector leaderSelector;
        private SharedCount jobStateCounter;
        private int localJobCount;

        LeaderSelectorData(LeaderSelector leaderSelector, SharedCount jobStateCounter,int localJobCount) {
            this.leaderSelector = leaderSelector;
            this.jobStateCounter = jobStateCounter;
            this.localJobCount = localJobCount;
        }
        public LeaderSelector getLeaderSelector() {
            return leaderSelector;
        }
        public SharedCount getJobStateCounter() {
            return jobStateCounter;
        }
        public int getLocalJobCount(){
            return localJobCount;
        }
        public void setLocalJobCount(int sharedCountValue){
            localJobCount = sharedCountValue;
        }
    }
}
