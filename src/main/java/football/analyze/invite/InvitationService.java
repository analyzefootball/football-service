package football.analyze.invite;

import java.util.List;

/**
 * @author Hassan Mushtaq
 * @since 6/6/18
 */
public interface InvitationService {

    void sendInvite(Invitation invitation);

    List<Invitation> findAll();

    Invitation findById(String inviteId);
}
