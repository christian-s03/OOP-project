import java.util.Set;

public class TEST0 {
    public static void main(String[] args) {

        ResourceRepository resourceRepository = new InMemoryResourceRepository();
        UserRepository userRepo = new InMemoryUserRepository();

        resourceRepository.add(new Room("Sala Alfa", Money.of(80), 12, Set.of("Projektor")));
        resourceRepository.add(new Desk("Hot-1", Money.of(25), Desk.DeskType.HOT));
        resourceRepository.add(new Device("Projektor-1", Money.of(40), 2));

        userRepo.add(new IndividualUser("anna@ex.com", "Anna Nowak"));
        userRepo.add(new CompanyUser("biuro@acme.pl", "ACME Sp. z o.o.", "5211234567"));

        System.out.println("=== List_Resources ===");
        for (Resource r : resourceRepository.findAll()) {
            System.out.println(r);
        }
        System.out.println("=== List_Users ===");
        for (User r : userRepo.findAll()) {
            System.out.println(r);
        }
    }
}
