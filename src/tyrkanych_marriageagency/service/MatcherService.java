package tyrkanych_marriageagency.service;

import java.util.HashSet;
import java.util.Set;
import tyrkanych_marriageagency.model.Client;

public class MatcherService {

    private final Set<String> matches = new HashSet<>();

    public void like(Client from, Client to) {
        if (from.getProfile() == null || to.getProfile() == null) {
            return;
        }

        from.getProfile().like(to);

        if (to.getProfile().hasLiked(from)) {
            matches.add(key(from, to));
            matches.add(key(to, from));
            System.out.println("💖 MATCH!");
        }
    }

    public boolean isMatch(Client a, Client b) {
        return matches.contains(key(a, b));
    }

    private String key(Client a, Client b) {
        return a.getId() + ":" + b.getId();
    }
}
