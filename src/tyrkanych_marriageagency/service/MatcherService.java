package tyrkanych_marriageagency.service;

import java.util.Optional;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.model.Match;

public class MatcherService {

    public Optional<Match> tryMatch(Client a, Client b) {

        boolean sameCity =
              a.getProfile().getInterests().size() > 0 &&
                    b.getProfile().getInterests().size() > 0;

        if (sameCity) {
            return Optional.of(new Match(a, b));
        }
        return Optional.empty();
    }
}