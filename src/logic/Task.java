package logic;

/**
 * Created by dalugoga on 20-05-2017.
 */
public class Task {
    private final String task;
    private final boolean isdone;

    public Task(String task, boolean isdone) {
        this.task = task;
        this.isdone = isdone;
    }

    public String getTask() {
        return task;
    }

    public boolean isIsdone() {
        return isdone;
    }
}
