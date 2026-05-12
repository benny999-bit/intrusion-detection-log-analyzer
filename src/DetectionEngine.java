import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DetectionEngine {
	private HashMap<String, Integer> failedLogins;
	private HashSet<String> suspicious;

	public DetectionEngine() {
		this.failedLogins = new HashMap<>();
		this.suspicious = new HashSet<>();

	}

	public boolean addFailedLogins(LogEntry entry) {
		if (entry.getStatus().equals("FAILED") && entry.getAction().equals("LOGIN")) {
			int count = this.failedLogins.getOrDefault(entry.getIp(), 0);
			this.failedLogins.put(entry.getIp(), count + 1);
			if (this.failedLogins.get(entry.getIp()) >= 3) {
				this.suspicious.add(entry.getIp());
			}
			return true;
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

}
