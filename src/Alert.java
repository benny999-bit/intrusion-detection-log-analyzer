
public class Alert {
	private String severity;
	private String type;
	private String ipAddress;
	private String reason;

	public Alert(String severity, String type, String ipAddress, String reason) {
		this.severity = severity;
		this.type = type;
		this.ipAddress = ipAddress;
		this.reason = reason;
	}

	public String getSeverity() {
		return this.severity;
	}

	public void setSeverity(int failedLoginAttempts) {
		if (failedLoginAttempts >= 5) {
			this.severity = "MEDIUM";
		} else if (failedLoginAttempts >= 10) {
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

	

	

}
