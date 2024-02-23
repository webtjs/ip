package helpy.task;

import java.io.FileWriter;
import java.io.IOException;

public class Task {
    protected String taskName;
    protected boolean isDone;

    public Task() {
        setDone(false);
    }

    public Task(String taskName) {
        setTaskName(taskName.trim());
        setDone(false);
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + getTaskName();
    }

    public void saveToFile(String filePath) throws IOException {
        FileWriter helpyWriter = new FileWriter(filePath, true);
        String isDone = isDone() ? "1" : "0";
        helpyWriter.write("T | " + isDone + " | " + this.taskName + "\n");
        helpyWriter.close();
    }
}
