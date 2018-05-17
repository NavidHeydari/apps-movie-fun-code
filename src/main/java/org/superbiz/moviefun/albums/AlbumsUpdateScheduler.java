package org.superbiz.moviefun.albums;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.TimeZone;

@Configuration
@EnableAsync
@EnableScheduling
public class AlbumsUpdateScheduler {

    private static final long SECONDS = 1000;
    private static final long MINUTES = 60 * SECONDS;

    private final AlbumsUpdater albumsUpdater;
    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    public JdbcTemplate jdbcTemplate;
    @Autowired
    public DataSource dataSource;


    public AlbumsUpdateScheduler(AlbumsUpdater albumsUpdater) {
        this.albumsUpdater = albumsUpdater;
    }


    @Scheduled(initialDelay = 15 * SECONDS, fixedRate = 20 * SECONDS)
    public void run() {

        if (startAlbumSchedulerTask()) {
            try {
                logger.debug("Starting albums update");
                albumsUpdater.update();

                logger.debug("Finished albums update");

            } catch (Throwable e) {
                logger.error("Error while updating albums", e);
            }
        }else{
            logger.debug("Nothing to start.");
        }
    }


    /**
     * checking database and return the state whether to start the scheduled task or not.
     *
     * @return
     */
    public boolean startAlbumSchedulerTask_ourLogic(){

        // querying database in here
        String query = "SELECT TOP 1 started_at FROM album_scheduler_task ORDER BY started_at DESC";
        Timestamp ts = getJdbcTemplate().queryForObject(query, Timestamp.class);

        logger.debug("last timestamp is: {}", ts );
        Instant last_db_time;

        if(Objects.isNull(ts)) {
//            last_db_time =  ZonedDateTime.now().toInstant().minusMillis(4000*60*60);
            return true;
        }

        last_db_time = ts.toInstant();
        Instant now = ZonedDateTime.now().toInstant();

        Instant two_min_ago= now.minusMillis(2000*60*60);

        return (!two_min_ago.isAfter(last_db_time));

    }


    private boolean startAlbumSchedulerTask() {
        int updatedRows = getJdbcTemplate().update(
                "UPDATE album_scheduler_task" +
                        " SET started_at = now()" +
                        " WHERE started_at IS NULL" +
                        " OR started_at < date_sub(now(), INTERVAL 20 SECOND)"
        );

        return updatedRows > 0;
    }


    /**
     * just preparing the jdbctemplate out of datastore object.
     * @return jdbcTempalte instance
     */
    public JdbcTemplate getJdbcTemplate(){

//        MysqlDataSource dataSource = new MysqlDataSource();
//        ds.set

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        return jdbcTemplate;
    }


}
