import java.util.ArrayList;

public class ReportGenerator {
	public ReportGenerator() {
	}

	public void securityReport(DetectionEngine detect, int total) {
		StringBuilder result = new StringBuilder();
		result.append("==== SECURITY REPORT ====");
		result.append("\n");
		result.append("\n");
		result.append("Total Log Entries: " + total);
		result.append("\n");
		result.append("\n");
		result.append("Failed Login Summary:");
		for (String ip : detect.getCopyFailedLogins().keySet()) {
			result.append("\n");
			result.append(ip + " -> " + "Failed logins: " + detect.getCopyFailedLogins().get(ip));
		}
		result.append("\n");
		result.append("\n");
		result.append("\n");
		result.append("Alerts:");
		result.append("\n");
		for (String ip : detect.getAlertsCopy().keySet()) {
			result.append("IP -> " + ip);
			ArrayList<Alert> alerts = detect.getAlertsCopy().get(ip);
			for (Alert alert : alerts) {
				result.append("\n");
				result.append(
						alert.getTimeStamp() + " [" + alert.getSeverity() + "] " + alert.getType() + " from " + ip);
			}
			result.append("\n");
			result.append("\n");
		}
		System.out.println(result.toString());
	}
}
