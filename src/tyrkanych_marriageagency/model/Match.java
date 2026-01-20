package tyrkanych_marriageagency.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Match {

    private final String id;
    private final Client clientA;
    private final Client clientB;
    private final LocalDateTime createdAt;
    private final MatchStatus status;

    public Match(Client a, Client b) {
        this.id = UUID.randomUUID().toString();
        this.clientA = a;
        this.clientB = b;
        this.createdAt = LocalDateTime.now();
        this.status = MatchStatus.ACTIVE;
    }
}