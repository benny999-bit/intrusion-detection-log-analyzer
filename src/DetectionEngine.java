import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DetectionEngine {
	private HashMap<String, Integer> failedLoginsDetect;
	private HashMap<String, Integer> failedLoginUsernameDetect;
	private HashMap<String, ArrayList<ZonedDateTime>> ipTimestampsFailedLogins;
	private HashMap<String, ArrayList<ZonedDateTime>> ipTimestampsUsernameSpraying;
	private HashMap<String, HashSet<String>> ipWithusernames;
	private HashMap<String, ArrayList<Alert>> alerts;
	private HashSet<String> suspicous;

	public DetectionEngine() {
		this.failedLoginsDetect = new HashMap<String, Integer>();
		this.failedLoginUsernameDetect = new HashMap<String, Integer>();
		this.ipTimestampsFailedLogins = new HashMap<String, ArrayList<ZonedDateTime>>();
		this.ipTimestampsUsernameSpraying = new HashMap<String, ArrayList<ZonedDateTime>>();
		this.ipWithusernames = new HashMap<String, HashSet<String>>();
		this.alerts = new HashMap<String, ArrayList<Alert>>();
		this.suspicous = new HashSet<String>();
	}

	public boolean addFailedLogin(LogEntry entry) {
		if (entry.getAction().equals("LOGIN") && entry.getStatus().equals("FAILED")) {
			int count = this.failedLoginsDetect.getOrDefault(entry.getIp(), 0);
			this.failedLoginsDetect.put(entry.getIp(), count + 1);
			int failedAttempts = this.failedLoginsDetect.get(entry.getIp());
			ArrayList<ZonedDateTime> list = this.ipTimestampsFailedLogins.getOrDefault(entry.getIp(),
					new ArrayList<ZonedDateTime>());
			list.add(entry.getTimestamp());
			this.ipTimestampsFailedLogins.put(entry.getIp(), list);
			if (failedAttempts >= 3 && checkTimestamps(list)) {
				this.suspicous.add(entry.getIp());
				ArrayList<Alert> alerts = this.alerts.getOrDefault(entry.getIp(), new ArrayList<Alert>());
				Alert alert = new Alert("LOW", "BRUTE_FORCE_ATTEMPT", entry.getIp(), "EXCESSIVE FAILED LOGIN ATTEMPTS",
						entry.getTimestamp().toString());
				alert.setSeverity(failedAttempts);
				if (alerts.contains(alert)) {
					return true;
				}
				alerts.add(alert);
				this.alerts.put(entry.getIp(), alerts);
			}
			return true;
		}
		return false;
	}

	public boolean checkTimestamps(ArrayList<ZonedDateTime> list) {
		for (int i = 0; i < list.size() - 2; i++) {
			ZonedDateTime current = list.get(i);
			ZonedDateTime next = list.get(i + 2);
			long duration = current.until(next, ChronoUnit.SECONDS);
			if (duration <= 120) {
				return true;
			}
		}
		return false;
	}

	public boolean usernameSprayingDetection(LogEntry entry) {
		if (entry.getAction().equals("LOGIN") && entry.getStatus().equals("FAILED")) {
			int count = this.failedLoginUsernameDetect.getOrDefault(entry.getIp(), 0);
			this.failedLoginUsernameDetect.put(entry.getIp(), count + 1);
			int failedAttempts = this.failedLoginUsernameDetect.get(entry.getIp());
			HashSet<String> set = this.ipWithusernames.getOrDefault(entry.getIp(), new HashSet<String>());
			set.add(entry.getUsername());
			this.ipWithusernames.put(entry.getIp(), set);
			HashSet<String> usernames = this.ipWithusernames.get(entry.getIp());
			ArrayList<ZonedDateTime> list = this.ipTimestampsUsernameSpraying.getOrDefault(entry.getIp(),
					new ArrayList<ZonedDateTime>());
			list.add(entry.getTimestamp());
			this.ipTimestampsUsernameSpraying.put(entry.getIp(), list);
			if (usernames.size() >= 3 && checkTimestamps(list)) {
				this.suspicous.add(entry.getIp());
				ArrayList<Alert> alerts = this.alerts.getOrDefault(entry.getIp(), new ArrayList<Alert>());

				Alert alert = new Alert("LOW", "USERNAME_SPRAYING", entry.getIp(),
						"FAILED LOGINS WITH DIFFERENT USERNAMES BUT SAME IP", entry.getTimestamp().toString());
				alert.setSeverity(failedAttempts);
				if (alerts.contains(alert)) {
					return true;
				}
				alerts.add(alert);
				this.alerts.put(entry.getIp(), alerts);

			}
			return true;

		}
		return false;
	}
	
	public String allFailedLogins() {
		StringBuilder result = new StringBuilder();
		for (String key : this.failedLoginsDetect.keySet()) {
			Integer value = this.failedLoginsDetect.get(key);
			result.append(key + " -> " + "Failed logins: " + value);
			result.append("\n");
		}
		return result.toString();
	}

	public String failedLogin(LogEntry entry) {
		String key = entry.getIp();
		int value = this.failedLoginsDetect.getOrDefault(key, 0);
		return key + " -> " + value;
	}

	public Set<String> getSusCopies() {
		return Collections.unmodifiableSet(this.suspicous);
	}

	public Map<String, Integer> getCopyFailedLogins() {
		return Collections.unmodifiableMap(this.failedLoginsDetect);
	}

	public Map<String, ArrayList<Alert>> getAlertsCopy() {
		return Collections.unmodifiableMap(this.alerts);
	}

}
