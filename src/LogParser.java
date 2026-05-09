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
			String date = split[0];
			String time = split[1];

			int firstDate = date.indexOf("-");
			int lastDate = date.lastIndexOf("-");
			int firstTime = time.indexOf(":");
			int lastTime = time.lastIndexOf(":");

			String year = date.substring(0, firstDate);
			String month = date.substring(firstDate + 1, lastDate);
			String day = date.substring(lastDate + +1, date.length());

			String hour = time.substring(0, firstTime);
			String minute = time.substring(firstTime + 1, lastTime);
			String second = time.substring(lastTime + 1, time.length());

			int yeartoInt = Integer.parseInt(year);
			int monthtoInt = Integer.parseInt(month);
			int daytoInt = Integer.parseInt(day);

			int hourToInt = Integer.parseInt(hour);
			int minuteToInt = Integer.parseInt(minute);
			int secondToInt = Integer.parseInt(second);

			String ipStart = split[2];
			String username = split[3];

			int usernameStart = username.indexOf("=");
			String name = username.substring(usernameStart + 1, username.length());

			int index = ipStart.indexOf("=");
			String ip = ipStart.substring(index + 1, ipStart.length());

			String action = split[4];
			int equal = action.indexOf("=");
			String loginOrLogout = action.substring(equal + 1, action.length());

			String status = split[5];
			int temp = status.indexOf("=");
			String failedOrSuccess = status.substring(temp + 1, status.length());

			ZonedDateTime timeStamp = ZonedDateTime.of(yeartoInt, monthtoInt, daytoInt, hourToInt, minuteToInt,
					secondToInt, 0, ZoneId.systemDefault());

			LogEntry entry = new LogEntry(name, ip, loginOrLogout, failedOrSuccess, timeStamp);
			this.entries.add(entry);

//			System.out.println(entry.getTimeStamp() + " " + entry.getIp() + " " + entry.getUsername() + " "
//					+ entry.getAction() + " " + entry.getStatus());

		}
	}

	public List<LogEntry> getLogEntries() {
		return Collections.unmodifiableList(this.entries);
	}

	public int size() {
		return this.entries.size();
	}
}
