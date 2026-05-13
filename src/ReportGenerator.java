import java.util.List;

public class ReportGenerator {

	public ReportGenerator(List<LogEntry> list) {

	}

	public void generateSecurityReport(DetectionEngine detect, int total) {
		StringBuilder result = new StringBuilder();
		result.append("==== SECURITY REPORT ====");
		result.append("\n");
		result.append("\n");
		result.append("Total Log Entries: " + total);
		result.append("\n");
		result.append("\n");
		result.append("Failed Login Summary: ");
		result.append("\n");
		result.append(detect.allFailedLogins());
		result.append("\n");
		result.append("\n");
		result.append("Alerts:");
		result.append("\n");
		result.append("\n");
		for (String ip : detect.getAlertsCopy().keySet()) {
			Alert alert = detect.getAlertsCopy().get(ip);
			result.append("[" + alert.getSeverity() + "] " + alert.getType() + " from " + alert.getIpAddress());
			result.append("\n");
		}
		System.out.println(result.toString());
	}
}
