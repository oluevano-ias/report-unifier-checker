package com.unifier.dto.comparisonresult;

public class PerProductResult {
    private MultipleTeamsResult publisherReports;
    private MultipleTeamsResult firewallReports;

    public MultipleTeamsResult getPublisherReports() {
        return publisherReports;
    }

    public void setPublisherReports(MultipleTeamsResult publisherReports) {
        this.publisherReports = publisherReports;
    }

    public MultipleTeamsResult getFirewallReports() {
        return firewallReports;
    }

    public void setFirewallReports(MultipleTeamsResult firewallReports) {
        this.firewallReports = firewallReports;
    }
}
