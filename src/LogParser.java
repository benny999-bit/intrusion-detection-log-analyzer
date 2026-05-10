import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogParser {
	private ArrayList<LogEntry> entries;

	public LogParser(Path path) throws IOException {
		this.entries = new ArrayList<>();

		List<String> lines = Files.readAllLines(path);
		for (String line : lines) {
			String[] split = line.split(" ");
			if (split.length != 6) {
				continue;
			}

			String initialDate = split[0];
			String initialTime = split[1];
			int firstindexDate = initialDate.indexOf("-");
			int lastindexDate = initialDate.lastIndexOf("-");

			String year = initialDate.substring(0, firstindexDate);
			String month = initialDate.substring(firstindexDate + 1, lastindexDate);
			String day = initialDate.substring(lastindexDate + 1, initialDate.length());

			int firstindexTime = initialTime.indexOf(":");
			int lastindexTime = initialTime.lastIndexOf(":");

			String hour = initialTime.substring(0, firstindexTime);
			String minute = initialTime.substring(firstindexTime + 1, lastindexTime);
			String second = initialTime.substring(lastindexTime + 1, initialTime.length());

			ZonedDateTime timeStamp = ZonedDateTime.of(Integer.parseInt(year), Integer.parseInt(month),
					Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second),
					0, ZoneId.systemDefault());

			String initialIp = split[2];
			int firstindexIp = initialIp.indexOf("=");
			String ip = initialIp.substring(firstindexIp + 1, initialIp.length());

			String initialUsername = split[3];
			int indexUser = initialUsername.indexOf("=");
			String username = initialUsername.substring(indexUser + 1, initialUsername.length());

			String initialAction = split[4];
			int indexAction = initialAction.indexOf("=");
			String action = initialAction.substring(indexAction + 1, initialAction.length());

			String initialStatus = split[5];
			int indexStatus = initialStatus.indexOf("=");
			String status = initialStatus.substring(indexStatus + 1, initialStatus.length());

			LogEntry entry = new LogEntry(timeStamp, ip, username, action, status);
			this.entries.add(entry);
		}
	}

	public List<LogEntry> listCopy() {
		return Collections.unmodifiableList(this.entries);
	}

	public int size() {
		return this.entries.size();
	}
}
