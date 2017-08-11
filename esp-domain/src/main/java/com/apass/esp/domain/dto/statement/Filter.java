package com.apass.esp.domain.dto.statement;

import java.util.ArrayList;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/11
 * @see
 * @since JDK 1.8
 */
public class Filter {
    private String start="2017-04-14";
    private String end = "2017-07-14";
    private ArrayList<Integer> platformids;
    private ArrayList<String> versions;
    private ArrayList<Integer> channelids;
    private ArrayList<String> eventids;
    private ArrayList<String> pagenames;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public ArrayList<Integer> getPlatformids() {
        return platformids;
    }

    public void setPlatformids(ArrayList<Integer> platformids) {
        this.platformids = platformids;
    }

    public ArrayList<String> getVersions() {
        return versions;
    }

    public void setVersions(ArrayList<String> versions) {
        this.versions = versions;
    }

    public ArrayList<Integer> getChannelids() {
        return channelids;
    }

    public void setChannelids(ArrayList<Integer> channelids) {
        this.channelids = channelids;
    }

    public ArrayList<String> getEventids() {
        return eventids;
    }

    public void setEventids(ArrayList<String> eventids) {
        this.eventids = eventids;
    }

    public ArrayList<String> getPagenames() {
        return pagenames;
    }

    public void setPagenames(ArrayList<String> pagenames) {
        this.pagenames = pagenames;
    }
}
