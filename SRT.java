import java.util.ArrayList;

public class SRT {

    // Declaring and Initializing Arraylist for the project.
    public ArrayList<Job> allJobs = new ArrayList<>(Main.currentJobs);
    public ArrayList<boolean[]> display = new ArrayList<>();
    ArrayList<Job> unfinishedJobs = new ArrayList<>(allJobs);


    // Runs the SRT algorithm and displays results.
    public void shortestRemainingTime() {

        // boolean array to keep track of which job is running at which time.
        boolean[] runningJobs = new boolean[allJobs.size()];
        int jobPosition = -1;

        // keep track of which job and time increment.
        Job jobInSystem = null;
        int counter = 0;

        // do while to runk the algorithm untill there are no jobs.
        do {
            int MAX_TIME = 1000000; // used to keep track of which job is running next.

            // Array that keeps track of which job is running at time instance.
            boolean[] jobsInSystem = new boolean[allJobs.size()];

            // Goes through the remining time for each job and finds the shortest one.
            for (int i = 0; i < runningJobs.length; i++) {
                Job currJob = allJobs.get(i);
                if (currJob.getArrivalTime() == counter) {
                    runningJobs[i] = true;
                }

                // Seting the shortest time job to the next runnable job.
                if (runningJobs[i] && currJob.getRemainingServiceTime() < MAX_TIME) {
                    jobPosition = i;
                    MAX_TIME = currJob.getRemainingServiceTime();
                }
            }

            // Checks if the current running job is done or not.
            if (runningJobs[jobPosition]) {
                jobInSystem = allJobs.get(jobPosition);
                for (int i = 0; i < allJobs.size(); i++) {
                    jobsInSystem[i] = allJobs.get(i).getJobName().equals(jobInSystem.getJobName());
                }
            }

            // adds which ever job ran into this.
            display.add(jobsInSystem);

            // decrements the jobs service time by 1.
            assert jobInSystem != null;
            jobInSystem.setRemainingServiceTime(jobInSystem.getRemainingServiceTime() - 1);

            // if a job finished its service time we remove that job from the queue.
            if (jobInSystem.getRemainingServiceTime() == 0) {
                unfinishedJobs.remove(jobInSystem);
                runningJobs[jobPosition] = false;
            }
            counter++;
        } while (!unfinishedJobs.isEmpty());

        // used to print the results of the algorithm.
        System.out.println();
        System.out.println("Scheduler Algorithm : Shortest Remaining Time");
        Main.print(display);

    }

}