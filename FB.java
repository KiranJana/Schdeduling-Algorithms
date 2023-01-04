import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FB {
    // Arraylists for the project
    public ArrayList<Job> allJobs = new ArrayList<>(Main.currentJobs);
    public ArrayList<boolean[]> display = new ArrayList<>();
    ArrayList<Job> remainingJobs = new ArrayList<>(allJobs);

    // Queues initialized for the project.
    Queue<Integer> readyQueueHigh = new LinkedList<>();
    Queue<Integer> readyQueueMid = new LinkedList<>();
    Queue<Integer> readyQueueLow = new LinkedList<>();

    // RUns the feedback algorithm and displays result.
    public void feedback() {

        // Keeps track of the current jobs in the system.
        int runningJobPosition = -1;
        Job jobInSystem;
        boolean[] readyJobs = new boolean[allJobs.size()];

        // positions being remembered to see which job is where.
        int timeoutJobPosition = -1;
        int readyQueue = -1;
        boolean newJobAdded = false;


        // Counter to keep track of each increment.
        int counter = 0;

        // Loop that runs until there is no job let.
        do {

            // gets the currently added jobs and adds them to the high queue.
            for (int i = 0; i < readyJobs.length; i++) {
                Job currJob = allJobs.get(i);
                if (currJob.getArrivalTime() == counter) {
                    readyJobs[i] = true;
                    readyQueueHigh.add(i);
                    newJobAdded = true;
                }
            }

            // Boolean to see whihc job was running at each time increement.
            boolean[] jobResult = new boolean[allJobs.size()];

            // If a new job is added to the back to the queue. Set it in the positon it needs to be.
            if (newJobAdded) {
                if (readyQueue == 1) {
                    readyQueueMid.add(timeoutJobPosition);
                } else if (readyQueue == 2 || readyQueue == 3) {
                    readyQueueLow.add(timeoutJobPosition);
                }
            } else if (readyQueueHigh.isEmpty() && readyQueueMid.isEmpty() && readyQueueLow.isEmpty()) {
                runningJobPosition = timeoutJobPosition;
            }

            // If there is a job in any of the queue's get it to run next.
            if (!readyQueueHigh.isEmpty()) {
                runningJobPosition = readyQueueHigh.remove();
                readyQueue = 1;
            } else if (!readyQueueMid.isEmpty()) {
                runningJobPosition = readyQueueMid.remove();
                readyQueue = 2;
            } else if (!readyQueueLow.isEmpty()) {
                runningJobPosition = readyQueueLow.remove();
                readyQueue = 3;
            }
            jobInSystem = allJobs.get(runningJobPosition);


            // add jobs that just ran to the array for display.
            if (readyJobs[runningJobPosition]) {
                for (int i = 0; i < allJobs.size(); i++) {
                    jobResult[i] = allJobs.get(i).getJobName().equals(jobInSystem.getJobName());
                }
                display.add(jobResult);
            } else if (!readyJobs[runningJobPosition] && readyQueueHigh.isEmpty() && readyQueueMid.isEmpty() && readyQueueLow.isEmpty()) {
                display.add(jobResult);
            }

            // Decrement once a job has ran once.
            jobInSystem.setRemainingServiceTime(jobInSystem.getRemainingServiceTime() - 1);

            // remove job from list if its service time is over.
            if (jobInSystem.getRemainingServiceTime() == 0) {
                remainingJobs.remove(jobInSystem);
                readyJobs[runningJobPosition] = false;
            }


            // Moving jobs to a new queue once they are done running.
            if (readyJobs[runningJobPosition]) {
                if (readyQueueHigh.isEmpty() && readyQueueMid.isEmpty() && readyQueueLow.isEmpty()) {
                    timeoutJobPosition = runningJobPosition;
                } else {
                    if (readyQueue == 1) {
                        readyQueueMid.add(runningJobPosition);
                    } else {
                        readyQueueLow.add(runningJobPosition);
                    }
                }
            }
            //Incrementing counter.
            newJobAdded = false;
            counter++;
        } while (!remainingJobs.isEmpty()); // Runs until no jobs left.

        // Printing and displaying results of the algorithm.
        System.out.println();
        System.out.println("Scheduler Algorithm : Feedback");
        Main.print(display);


    }

}