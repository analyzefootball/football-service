package football.analyze.common.scripts;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;

/**
 * @author Hassan Mushtaq
 * @since 6/11/18
 */
@ChangeLog(order = "003")
public class PredictionData {

    @ChangeSet(order = "001", id = "Update Predictions", author = "Hassan Mushtaq")
    public void updatePredictions(MongoDatabase db) throws IOException {
        MongoCollection<Document> users = db.getCollection("user");
        Bson filter = Filters.exists("username");

        Document document = Document.parse(predictionsAsJSON());
        Bson newValue = new Document("predictions", document.get("predictions"));
        Bson updateOperationDocument = new Document("$set", newValue);

        users.updateMany(filter, updateOperationDocument);
    }

    private String predictionsAsJSON() throws IOException {
        return IOUtils.toString(getClass().getResourceAsStream("/predictions.json"), "UTF-8");
    }
}
