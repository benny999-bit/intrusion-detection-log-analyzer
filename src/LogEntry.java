import java.time.ZonedDateTime;

public class LogEntry {
	private ZonedDateTime timeStamp;
	private String ip;
	private String username;
	private String action;
	private String status;

	public LogEntry(ZonedDateTime timeStamp, String ip, String username, String action, String status) {
		if (ip == null || ip.isBlank()) {
			throw new IllegalArgumentException("Invalid/Empty ip.");
		}
		if (username == null || username.isBlank()) {
			throw new IllegalArgumentException("Invalid/Empty username.");
		}
		if (action != null && ((action.equals("LOGIN")) || (action.equals("LOGOUT")))) {
			this.action = action;
		} else {
			throw new IllegalArgumentException("Invalid/Empty action.");
		}
		if (status != null && ((status.equals("SUCCESS")) || (status.equals("FAILED")))) {
			this.status = status;
		} else {
			throw new IllegalArgumentException("Invalid/Empty status.");
		}
		if (timeStamp == null) {
			throw new IllegalArgumentException("Empty timestamp.");
		}

		this.timeStamp = timeStamp;
		this.ip = ip;
		this.username = username;
	}

	public ZonedDateTime getTimestamp() {
		return this.timeStamp;
	}

	public String getIp() {
		return this.ip;
	}

	public String getUsername() {
		return this.username;
	}

	public String getAction() {
		return this.action;
	}

	public String getStatus() {
		return this.status;
	}

}
