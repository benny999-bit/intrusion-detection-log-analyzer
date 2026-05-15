## Intrusion Detection Log Analyzer 

## What the project does:
A system that takes logs from a file and detects suspicious activity using a parser to take important data from each log in the file and detect suspicious activity.


## What logs are:
Logs are records of user activities and system events related to authentication.

## What the parser does:

The parser reads all of the lines in the text file and prepares it for tracking the logs for suspicious activities.


### What the LogEntry represents:
A LogEntry represents one log including its username, IP address, timestamp, action, and status.


### Why timestamps matter:
Timestamps are important for keeping logs organized, also keeping reports clean and to see when suspicious activities occurred and how often they are occuring.

### How alerts work:
Alerts are warnings detected when a ip has failed multiple times logging in. They contain a severity, type, reason, ipAddress, and a timestamp.


### Brute force detection:

A brute force detection is triggered when a IP has failed a login at least 3 times on the same ip address in a 2 minute time-window.

### Username Spraying detection:
A username spraying detection is triggered when a user has failed a login with same IP attempts and multiple usernames in a 2 minute time-window.


#### Time-window analysis:
Time-window works in a two minute window, if 3 failed attempts have occurred in a two minute window a log is eligible for detection.


#### Severity escalation: 
For severity to bump up to medium failed logins must reach a number of at least 5, to bump up to high at least 10. 


### Tradeoffs of design:
- HashMaps are used a lot good for instant look up not so good for the security reports all of the alerts are out of order so timestamps help in term of knowing when the alert occurred but you'll have to look manually can't rely on order

- Parser logic for reading the file is a bit brittle I used a lot of manual splitting of the lines and if any log is off by a space the parser logic will break



