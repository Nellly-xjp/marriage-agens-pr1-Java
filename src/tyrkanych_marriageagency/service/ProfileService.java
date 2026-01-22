package tyrkanych_marriageagency.service;

import java.util.List;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.model.Interest;
import tyrkanych_marriageagency.model.Profile;
import tyrkanych_marriageagency.repository.ClientRepository;

public class ProfileService {

    private final ClientRepository clientRepository;

    public ProfileService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void createOrUpdateProfile(Client client, int age, String city, String description,
          List<Interest> interests) {
        if (client.getProfile() == null) {
            client.setProfile(new Profile(age, city, null));
        }
        Profile profile = client.getProfile();
        profile.setAge(age);
        profile.setCity(city);
        profile.setDescription(description);
        profile.getInterests().clear();
        profile.getInterests().addAll(interests);

        clientRepository.update(client);
    }
}

