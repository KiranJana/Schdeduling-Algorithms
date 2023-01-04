import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Job {
    // Variables to the inputs from the jobs file
    private String jobName;
    private int arrivalTime;
    private int remainingServiceTime;
    private int initialServiceTime;

    // Constructor Jobs to create Job objects.
    public Job(String jobName, int arrivalTime, int serviceTime) {
        this.jobName = jobName;
        this.arrivalTime = arrivalTime;
        initialServiceTime = serviceTime;
        remainingServiceTime = serviceTime;
    }

    // Getter and setter methods for the info from the jobs file.
    public String getJobName() {
        return jobName;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getRemainingServiceTime() {
        return remainingServiceTime;
    }

    public void setRemainingServiceTime(int dur) {
        if (remainingServiceTime > 0) {
            remainingServiceTime = dur;
        } else remainingServiceTime = 0;
    }

    public void resetInitialServiceTime() {
        remainingServiceTime = initialServiceTime;
    }

}

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // First argument contains the file, and second argument is the algorithm.
        String firstArgument = args[0];
        String secondArgument = args[1];

        // Methods that read from the jobs.txt file and runs which ever algorithm that was entered into the second argument.
        readFile(firstArgument);
        runAlgorithm(secondArgument);
    }

    public static ArrayList<Job> currentJobs = new ArrayList<>();

    // Method that reads in data from the file and stores it as a job object in the Jobs arraylist.
    static void readFile(String file) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(file));
        while (scan.hasNext()) {
            String line = scan.nextLine(); // taking in the next line and checking if it has values.
            if (!line.equals("")) {
                String[] token = line.split("\t"); // Splitting based on spaces in the file.

                // Creating a new job object that stores information about each of the jobs into an arraylist.
                Job newJob = new Job(token[0], Integer.parseInt(token[1]), Integer.parseInt(token[2]));
                currentJobs.add(newJob); // adding each job into the array list.
            }
        }
    }

    // Switch to read user input and run the correct algorithm.
    static void runAlgorithm(String alName) {
        switch (alName) {
            case "RR": { // Round Robin
                RR job = new RR();
                job.roundRobin();
                break;
            }
            case "SRT":{ // Shortest Remaining Time.
                SRT job = new SRT();
                job.shortestRemainingTime();
                break;
            }
            case "FB": { // Feedback
                FB job = new FB();
                job.feedback();
                break;
            }
            case "ALL":{ // Runs all the algorithm.
                RR rrJob = new RR();
                rrJob.roundRobin();
                SRT srtJob = new SRT();
                srtJob.shortestRemainingTime();
                FB fbJob = new FB();
                fbJob.feedback();
                break;
            }
            default: System.out.println("ERROR: Wrong Algorithm was selected");
        }

    }


    // Print function to out the result.
    static void print(ArrayList<boolean[]> display) {

        // Prints the Jobs in order.
        for (int i = 0; i < currentJobs.size(); i++) {

            System.out.print(" " + currentJobs.get(i).getJobName() + " ");
        }

        System.out.println();

        // Prints X if job ran . if the job didn't run.
        for (int i = 0; i < display.size(); i++) {
            for (int j = 0; j < display.get(i).length; j++) {
                if (display.get(i)[j]) System.out.print(" X ");
                else System.out.print(" . ");
            }
            System.out.println();
        }
        // Resets the service times.
        for (int i = 0; i < currentJobs.size(); i++) {
            currentJobs.get(i).resetInitialServiceTime();
        }

    }
}

