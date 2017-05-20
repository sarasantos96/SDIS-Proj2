package logic;

/**
 * Created by dalugoga on 20-05-2017.
 */
public class Task {
    private final String task;
    private final boolean isdone;
    private final int id;

    public Task(String task, boolean isdone, int id) {
        this.task = task;
        this.isdone = isdone;
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public boolean isIsdone() {
        return isdone;
    }

    public int getId() {
        return id;
    }
}
