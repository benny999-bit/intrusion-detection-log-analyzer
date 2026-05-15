## Intrusion Detection Log Analyzer 

## What the project does:
A system that takes logs from a file and detects suspicous activity using a parser to take important data from each log in the file and detect suspicious unwanted activity.


## What logs are:

Logs are normally outputs to a command line that gives you the history of activity status of users, normally used in operating systems. Logs contain normally a timestamp, user, action, and status.


## What the parser does:

The parser reads all of the lines in the text file and creates a LogEntry for each object storing all of the data in the log.


### What the LogEntry represents:
A LogEntry represents one log line in a log file.


### Why timestamps matter:
Timestamps are important for keeping logs in order and not mistaking a times order due to different timezones important for not being mislead.


### Future detection ideas:
- Adding  a more organinzed less manual way of parsing
- maybe with our list of entry logs turning that into a map instead with keys being the user or ip and the value being the actual log but a arraylist of logs so we can handle detections their if there is a suspicious amount of failed sign in entries
