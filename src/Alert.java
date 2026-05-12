
public class Alert {
	private String severity;
	private String type;
	private String ipAddress;
	private String reason;
	private int failedLoginCount;

	public Alert(String severity, String type, String ipAddress, String reason, int failedLoginCount) {
		this.severity = severity;
		this.type = type;
		this.ipAddress = ipAddress;
		this.reason = reason;
		this.failedLoginCount = failedLoginCount;
	}

	public String getSeverity() {
		return this.severity;
	}

	public void setSeverity() {
		if (this.failedLoginCount == 5) {
			this.severity = "MEDIUM";
		} else if (this.failedLoginCount >= 10) {
			this.severity = "HIGH";
		}
	}

	public String getType() {
		return this.type;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public String getReason() {
		return this.reason;
	}

	public void increaseFailedLoginCount() {
		this.failedLoginCount++;
	}

	public int getFailedLoginCount() {
		return this.failedLoginCount;
	}

}
