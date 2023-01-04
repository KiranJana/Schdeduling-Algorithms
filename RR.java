import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RR {
    public ArrayList<Job> allJobs = new ArrayList<>(Main.currentJobs);
    ;
    public ArrayList<boolean[]> display = new ArrayList<>();

    // runs the RR algorithm and displays the results
    public void roundRobin() {

        Queue<Job> readyJobsQueue = new LinkedList<>(); // Ready queue that will hold jobs that are going to run next.
        ArrayList<Job> unfinishedJobs = new ArrayList<>(allJobs); // Array list of the jobs that have time left.
        Job jobInSystem = null;

        int count = 0; // Counter to keep track of how many seconds have passed by.
        do {
            // Took keep track of which job is running in the system.
            boolean[] runningJobs = new boolean[allJobs.size()];

            for (int i = 0; i < allJobs.size(); i++) {
                if (allJobs.get(i).getArrivalTime() == count) readyJobsQueue.add(allJobs.get(i));
            }

            // THis is to preempt a job if there is another one in the queue.
            if (unfinishedJobs.contains(jobInSystem)) {
                readyJobsQueue.add(jobInSystem);
            }

            // If the queue is not empty than we run a job.
            if (!readyJobsQueue.isEmpty()) {
                jobInSystem = readyJobsQueue.remove();
                for (int i = 0; i < allJobs.size(); i++) {
                    runningJobs[i] = allJobs.get(i).getJobName().equals(jobInSystem.getJobName());
                }
            }

            // Add the jobs that have run into the display so we can print accurately.
            display.add(runningJobs);

            // reduce the duration of the currently running job by 1
            jobInSystem.setRemainingServiceTime(jobInSystem.getRemainingServiceTime() - 1);

            // check if the currently running job is done running, and if so remove it from the jobs list.
            if (jobInSystem.getRemainingServiceTime() == 0) {
                unfinishedJobs.remove(jobInSystem);
            }

            // increment the current time step
            count++;

        } while (!unfinishedJobs.isEmpty());

        // printing the result of the algorithm.
        System.out.println();
        System.out.println("Scheduler Algorithm : Round Robin");
        Main.print(display);

    }
}