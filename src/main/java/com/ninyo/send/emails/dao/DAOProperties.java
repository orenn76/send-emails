package com.ninyo.send.emails.dao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix="jdbc")
public class DAOProperties {

    private Mysql mysql = new Mysql();
    private Mongo mongo = new Mongo();
    private Map<String, JDBCProperties> map = Map.of("mysql", mysql, "mongo", mongo);

    public Mysql getMysql() {
        return mysql;
    }

    public void setMysql(Mysql mysql) {
        this.mysql = mysql;
    }

    public Mongo getMongo() {
        return mongo;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public JDBCProperties getJdbcProperties(String name) {
        return map.get(name);
    }

    public static class Mysql implements JDBCProperties {

        private String url;
        private String driver;
        private String username;
        private String password;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDriver() {
            return driver;
        }

        public void setDriver(String driver) {
            this.driver = driver;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Mongo implements JDBCProperties {

        private String url;
        private String driver;
        private String username;
        private String password;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDriver() {
            return driver;
        }

        public void setDriver(String driver) {
            this.driver = driver;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static interface JDBCProperties {

        String getUrl();

        void setUrl(String url);

        String getDriver();

        void setDriver(String driver);

        String getUsername();

        void setUsername(String username);

        String getPassword();

        void setPassword(String password);
    }

}
