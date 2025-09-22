import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> userByEmail = new HashMap<>();

    @Override
    public void add(User user) {
        userByEmail.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userByEmail.get(email));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userByEmail.values());
    }
}
