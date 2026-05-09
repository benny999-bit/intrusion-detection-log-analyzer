import java.nio.file.Path;
import java.util.ArrayList;

public class LogParser {
	private ArrayList<LogEntry> entries;

	public LogParser(Path path) {
		this.entries = new ArrayList<>();
	}

	public boolean addLog() {
		return false;
	}

	public int size() {
		return this.entries.size();
	}
}
