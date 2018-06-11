package football.analyze.common.scripts;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

/**
 * @author Hassan Mushtaq
 * @since 6/9/18
 */
@Slf4j
@ChangeLog(order = "001")
public class InitialData {

    @ChangeSet(order = "001", id = "Tournament", author = "Hassan Mushtaq")
    public void addTournament(MongoDatabase db) throws IOException {
        MongoCollection<Document> mycollection = db.getCollection("tournament");
        Document document = Document.parse(tournamentAsJSON());
        mycollection.insertOne(document);
    }

    @ChangeSet(order = "002", id = "Admin", author = "Hassan Mushtaq")
    public void addAdminUser(MongoDatabase db) throws IOException {
        MongoCollection<Document> mycollection = db.getCollection("user");
        Document document = Document.parse(adminUserAsJSON());
        mycollection.insertOne(document);
    }

    private String tournamentAsJSON() throws IOException {
        return IOUtils.toString(getClass().getResourceAsStream("/worldcup.json"), "UTF-8");
    }

    private String adminUserAsJSON() throws IOException {
        String content = IOUtils.toString(getClass().getResourceAsStream("/adminuser.json"), "UTF-8");
        String password = RandomStringUtils.random(8, true, true);
        log.info("admin password: {}", password);
        String encrypted = new BCryptPasswordEncoder().encode(password);
        return content.replace("{password_placeholder}", encrypted);
    }
}
