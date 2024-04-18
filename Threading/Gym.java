import java.util.Map;
import java.util.List;
import java.lang.Thread;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Gym {

    private final int totalGymMembers;
    private Map<MachineType, Integer> availableMachines;

    public Gym(int totalGymMembers, Map<MachineType, Integer> availableMachines) {
        this.totalGymMembers = totalGymMembers;
        this.availableMachines = availableMachines;
    }

    public void openForTheDay() {

        List<Thread> gymMembersRoutines;

        gymMembersRoutines = IntStream.range(1,this.totalGymMembers)
                .mapToObj((id) -> {
                    Member member = new Member(id);
                    return new Thread(()-> {
                        try {
                            member.performRoutine();
                        } catch(Exception e) {
                            System.out.println(e.getMessage());
                        }
                    });
                })
                .collect(Collectors.toList());

        Thread supervisor = createSupervisor(gymMembersRoutines);
        supervisor.start();

        gymMembersRoutines.forEach((t) -> t.start()); // Standard lambda syntax

        // Method referencing syntax (uses double colons)
        // gymMembersRoutines.forEach(Thread::start)
    }

    public Thread createSupervisor(List<Thread> threads) {

        Thread supervisor = new Thread(() -> {

            while(true) {
                List<String> runningThreads = threads.stream().filter((t) -> t.isAlive())
                        .map((t) -> t.getName()).collect(Collectors.toList());

                // Method referencing technique
                /*List<String> runningThreads = threads.stream().filter(Thread::isAlive)
                        .map(Thread::getName).collect(Collectors.toList());*/

                System.out.println(Thread.currentThread().getName() + " - " + runningThreads.size()
                        + " members currently exercising: " + runningThreads + "\n");

                if(runningThreads.isEmpty())break;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println(Thread.currentThread().getName() + " has been completed.");

        });

        supervisor.setName("Gym Staff");

        return supervisor;

    }

}