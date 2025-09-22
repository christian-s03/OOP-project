import java.util.*;
import java.util.stream.Collectors;

public class InMemoryResourceRepository implements ResourceRepository {
    private final Map<String, Resource> resourcesByName = new HashMap<>();

    @Override
    public void add(Resource r) {
        resourcesByName.put(r.getName(), r);
    }

    @Override
    public Optional<Resource> findByName(String name) {
        return Optional.ofNullable(resourcesByName.get(name));
    }

    @Override
    public List<Resource> findAll() {
        return new ArrayList<>(resourcesByName.values());
    }

    @Override
    public List<Resource> findByType(Class<? extends Resource> t) {
        return resourcesByName.values().stream()
                .filter(r -> t.isInstance(r))
                .collect(Collectors.toList());
    }
}
