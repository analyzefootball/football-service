package football.analyze.common;

import com.github.mongobee.Mongobee;
import com.github.mongobee.exception.MongobeeException;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Hassan Mushtaq
 * @since 6/9/18
 */
@Component
public class Migration {

    private final Mongobee mongobee;

    public Migration(MongoClient mongoClient, MongoTemplate mongoTemplate) {
        mongobee = new Mongobee(mongoClient);
        mongobee.setDbName(mongoTemplate.getDb().getName());
        mongobee.setChangeLogsScanPackage("football.analyze.common.scripts");
        mongobee.setMongoTemplate(mongoTemplate);
        mongobee.setChangelogCollectionName("migrationLog");
        mongobee.setLockCollectionName("migrationLock");
    }

    public void migrate() {
        try {
            mongobee.execute();
        } catch (MongobeeException e) {
            throw new RuntimeException();
        }
    }
}
