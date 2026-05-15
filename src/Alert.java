import java.util.Objects;

public class Alert {
	private String severity;
	private String type;
	private String reason;
	private String ipAddress;
	private String timestamp;

	public Alert(String severity, String type, String reason, String ipAddress, String timestamp) {
		this.severity = severity;
		this.type = type;
		this.reason = reason;
		this.ipAddress = ipAddress;
		this.timestamp = timestamp;

	}

	public String getTimeStamp() {
		return this.timestamp;
	}

	public String getSeverity() {
		return this.severity;
	}

	public String getType() {
		return this.type;
	}

	public String getReason() {
		return this.reason;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setSeverity(int failedLoginAttempts) {
		if (failedLoginAttempts >= 5 && failedLoginAttempts < 10) {
			this.severity = "MEDIUM";
		} else if (failedLoginAttempts >= 10) {
			this.severity = "HIGH";
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Alert alert)) {
			return false;
		}

		return this.severity.equals(alert.severity) && this.type.equals(alert.type) && this.reason.equals(alert.reason)
				&& this.ipAddress.equals(alert.ipAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.severity, this.type, this.reason, this.ipAddress);
	}

}
