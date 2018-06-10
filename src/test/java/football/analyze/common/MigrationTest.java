package football.analyze.common;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Hassan Mushtaq
 * @since 6/10/18
 */
@RunWith(MockitoJUnitRunner.class)
public class MigrationTest {

    @Test(expected = RuntimeException.class)
    public void shouldHandleException() {
        MongoTemplate mongoTemplate = mock(MongoTemplate.class);
        MongoClient mongoClient = mock(MongoClient.class);
        MongoDatabase mongoDatabase = mock(MongoDatabase.class);
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.getName()).thenReturn("datanase");
        Migration migration = new Migration(mongoClient, mongoTemplate);
        migration.migrate();
    }
}