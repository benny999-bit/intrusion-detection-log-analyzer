import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DetectionEngine {
	private HashMap<String, Integer> failedLogins;
	private HashMap<String, Alert> alerts;
	private HashSet<String> suspicious;
	private HashMap<String, ArrayList<ZonedDateTime>> ipTimestamps;

	public DetectionEngine() {
		this.failedLogins = new HashMap<>();
		this.suspicious = new HashSet<>();
		this.alerts = new HashMap<>();
		this.ipTimestamps = new HashMap<>();

	}

	public boolean addFailedLogins(LogEntry entry) {
		if (entry.getStatus().equals("FAILED") && entry.getAction().equals("LOGIN")) {
			int count = this.failedLogins.getOrDefault(entry.getIp(), 0);
			this.failedLogins.put(entry.getIp(), count + 1);
			ArrayList<ZonedDateTime> list = this.ipTimestamps.getOrDefault(entry.getIp(),
					new ArrayList<ZonedDateTime>());
			list.add(entry.getTimestamp());
			this.ipTimestamps.put(entry.getIp(), list);
			if ((this.failedLogins.get(entry.getIp()) >= 3) && checkTimeStamps(this.ipTimestamps.get(entry.getIp()))) {
				if (this.alerts.get(entry.getIp()) != null) {
					Alert alert = this.alerts.get(entry.getIp());
					alert.setSeverity(this.failedLogins.get(entry.getIp()));
				} else {
					this.suspicious.add(entry.getIp());
					Alert alert = new Alert("LOW", "BRUTE_FORCE_ATTEMPT", entry.getIp(), "EXCESSIVE LOGIN FAILS");
					alert.setSeverity(this.failedLogins.get(entry.getIp()));
					this.alerts.put(entry.getIp(), alert);
				}
			}

			return true;
		}
		return false;
	}

	public boolean checkTimeStamps(ArrayList<ZonedDateTime> list) {
		// 1 2 3 4 5
		for (int i = 0; i < list.size() - 2; i++) {
			ZonedDateTime previous = list.get(i);
			ZonedDateTime current = list.get(i + 2);
			long duration = previous.until(current, ChronoUnit.SECONDS);
			if (duration <= 120) {
				return true;
			}
		}
		return false;

	}

	public String allFailedLogins() {
		StringBuilder result = new StringBuilder();
		for (String key : this.failedLogins.keySet()) {
			Integer value = this.failedLogins.get(key);
			result.append(key + " -> " + "Failed logins: " + value);
			result.append("\n");
		}
		return result.toString();
	}

	public String failedLogin(LogEntry entry) {
		String key = entry.getIp();
		int value = this.failedLogins.getOrDefault(key, 0);
		return key + " -> " + value;
	}

	public Set<String> getSusCopy() {
		return Collections.unmodifiableSet(this.suspicious);
	}

	public Map<String, Alert> getAlertsCopy() {
		return Collections.unmodifiableMap(this.alerts);
	}

}
