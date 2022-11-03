import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.link.*
import com.atlassian.jira.issue.link.RemoteIssueLink
import com.atlassian.jira.issue.link.RemoteIssueLinkManager
import com.atlassian.jira.issue.link.RemoteIssueLinkBuilder

import org.apache.log4j.Logger
import org.apache.log4j.Level
//import groovy.util.logging.Log4j
//log.setLevel(org.apache.log4j.Level.DEBUG)

def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()

// Get the components
def issueManager = ComponentAccessor.issueManager
def issueLinkManager = ComponentAccessor.issueLinkManager
def remoteIssueLinkManager = ComponentAccessor.getComponent(RemoteIssueLinkManager)
def customFieldManager = ComponentAccessor.getCustomFieldManager()

// Define the params to get an issue and filter the issue links by type
//final issueKey = 'DFAPPSUP-21269'
Issue issue = issue

// Get the issue
//def issue = issueManager.getIssueByCurrentKey(issueKey)
def cField = customFieldManager.getCustomFieldObjectByName("CUSTOM FIELD NAME") //Custom Field Name
def cFieldValue = issue.getCustomFieldValue(cField).toString()

if (cFieldValue.isEmpty()) {
	log.warn("Custom Field is Empty")
	return
	}


def remoteLinks = remoteIssueLinkManager.getRemoteIssueLinksForIssue(issue)
def remoteLinksHtml = remoteLinks.collect { remoteLink ->
    "<a href='${remoteLink.url}'>${remoteLink.title}</a>"
}

//log.warn("${remoteLinksHtml}")

if (remoteLinksHtml.isEmpty()) {
    log.warn("No links Found")
	if (cFieldValue == null) {
	return
	} else {
	RemoteIssueLink link = new RemoteIssueLinkBuilder().issueId(issue.getId()).url("https://yoururl.com/ticket/"+cFieldValue).title("LINK NAME").build();
	ComponentAccessor.getComponent(RemoteIssueLinkManager.class).createRemoteIssueLink(link, user)
        return
}}
//def test = remoteLinksHtml.find{it.contains("LINK NAME")} //Link Name
//log.warn(test)
//for(i in remoteLinksHtml){
if( remoteLinksHtml.find{it.contains("LINK NAME")} )  { //Link Name
    //i.contains("Factory Marketplace Link") ){
    log.warn("Found")
    return
} else {
log.warn("Not Found")
	if (cFieldValue == null) {
	return
	} else {
	RemoteIssueLink link = new RemoteIssueLinkBuilder().issueId(issue.getId()).url("https://yoururl.com/ticket/"+cFieldValue).title("LINK NAME").build();
	ComponentAccessor.getComponent(RemoteIssueLinkManager.class).createRemoteIssueLink(link, user)
        log.warn("Posted URL")
        return
    }}




