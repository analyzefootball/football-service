package football.analyze.common.scripts;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Hassan Mushtaq
 * @since 6/9/18
 */
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
        File file = ResourceUtils.getFile("classpath:worldcup.json");
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    private String adminUserAsJSON() throws IOException {
        File file = ResourceUtils.getFile("classpath:adminuser.json");
        String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
        return content.replace("{password", System.getenv("admin_password"));
    }
}
