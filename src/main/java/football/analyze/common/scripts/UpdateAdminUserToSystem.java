package football.analyze.common.scripts;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Hassan Mushtaq
 * @since 6/12/18
 */
@ChangeLog(order = "002")
public class UpdateAdminUserToSystem {

    @ChangeSet(order = "001", id = "Update User", author = "Hassan Mushtaq")
    public void updatePredictions(MongoDatabase db) {
        MongoCollection<Document> users = db.getCollection("user");
        users.updateOne(eq("username", "admin@analyze.football"), new Document("$set", new Document("role", "SYSTEM")));
    }
}
