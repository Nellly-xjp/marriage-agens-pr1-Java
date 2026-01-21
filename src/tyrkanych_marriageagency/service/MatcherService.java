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

    // Клієнт A лайкає клієнта B
    public void like(Client a, Client b) {

        a.getProfile().like(b); // зберегти лайк

        // Перевірка взаємного лайку
        if (b.getProfile().hasLiked(a)) {
            Match match = new Match(a, b);
            matches.add(match);
            System.out.println("Новий матч: " + a.getEmail() + " ❤️ " + b.getEmail());
        }
    }
}
