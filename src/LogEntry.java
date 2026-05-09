import java.time.ZonedDateTime;

public class LogEntry {
	private String username;
	private String ip;
	private String action;
	private String status;
	private ZonedDateTime timeStamp;

	public LogEntry(String username, String ip, String action, String status, ZonedDateTime timeStamp) {
		if (action != null && ((action.equals("LOGIN")) || (action.equals("LOGOUT")))) {
			this.action = action;
		} else {
			throw new IllegalArgumentException("Invalid action.");
		}

		if (status != null && ((status.equals("SUCCESS")) || (status.equals("FAILED")))) {
			this.status = status;
		} else {
			throw new IllegalArgumentException("Invalid status.");
		}

		if (ip == null || ip.isBlank()) {
			throw new IllegalArgumentException("Invalid/Empty IP address.");
		}

		if (username == null || username.isBlank()) {
			throw new IllegalArgumentException("Invalid/Empty username.");
		}

		if (timeStamp == null) {
			throw new IllegalArgumentException("Invalid/Empty timestamp.");
		}

		this.username = username;
		this.ip = ip;
		this.timeStamp = timeStamp;
	}

	public String getAction() {
		return "ACTION=" + action;
	}

	public String getStatus() {
		return "STATUS=" + status;
	}

	public ZonedDateTime getTimeStamp() {
		return timeStamp;
	}

	public String getUsername() {
		return "USER=" + username;
	}

	public String getIp() {
		return "IP=" + ip;
	}

}
