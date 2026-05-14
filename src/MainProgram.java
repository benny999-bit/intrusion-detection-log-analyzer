import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
 * change files
→ git add .
→ git commit -m "message"
→ git push
 */

public class MainProgram {
	private LogParser parser;
	private ReportGenerator genrator;
	private DetectionEngine detect;

	public MainProgram() {

	}

	public static void main(String[] args) throws IOException {
		MainProgram main = new MainProgram();
		main.start();
	}

	public void start() throws IOException {
		String demoPath = "C:/Users/smithbd/Downloads/exampleLogformat.txt";
		Path path = Paths.get(demoPath);
		this.parser = new LogParser(path);
		List<LogEntry> list = this.parser.listCopy();
		this.detect = new DetectionEngine();
		for (LogEntry entry : list) {
			this.detect.addFailedLogins(entry);
		}
		this.genrator = new ReportGenerator(list);
		this.genrator.generateSecurityReport(this.detect, list.size());

	}
}
