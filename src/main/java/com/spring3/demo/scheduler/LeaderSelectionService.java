package com.spring3.demo.scheduler;

/**
 * @author ajay.kg created on 11/06/17.
 */
public interface LeaderSelectionService {

    public void registerRunnableOnLeaderSelection(String leaderPath,Runnable runnable);
    public void selectLeader(String leaderPath);

}
