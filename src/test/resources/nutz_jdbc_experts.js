var ioc = {
    experts : {
        "mongodb.*" : "org.nutz.dao.impl.jdbc.mysql.MysqlJdbcExpert",
    },
    config : {
        "pool-home" : "~/.nutz/tmp/dao/",
        "pool-max" : 200000
    }
};