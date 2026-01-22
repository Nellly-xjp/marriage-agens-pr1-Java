package tyrkanych_marriageagency.service;

import java.util.ArrayList;
import java.util.List;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.model.Match;

public class MatcherService {

    private final List<Match> matches = new ArrayList<>();

    public List<Match> getMatches() {
        return matches;
    }

    public void like(Client a, Client b) {
        if (a.getProfile() == null || b.getProfile() == null) {
            return;
        }

        a.getProfile().like(b);

        boolean alreadyMatched = matches.stream()
              .anyMatch(m -> (m.getClientA() == a && m.getClientB() == b)
                    || (m.getClientA() == b && m.getClientB() == a));

        if (b.getProfile().hasLiked(a) && !alreadyMatched) {
            matches.add(new Match(a, b));
        }
    }
}
