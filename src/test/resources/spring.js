{
    dataSource : {
        type : "com.alibaba.druid.pool.DruidDataSource",
        fields : {
            driverClassName : 'com.mysql.jdbc.Driver',
            url : 'jdbc:mysql://127.0.0.1:3306/weixin?useUnicode=true&characterEncoding=UTF-8',
            username : 'root',
            password : 'root',
            initialSize : 10,
            maxActive : 100,
            minIdle : 10,
            maxIdle : 20,
            defaultAutoCommit : false,
        }
    },
    dao : {
        type : "cn.limw.summer.dao.hibernate.search.SearchQueryBuilder"
    },
    testMap : {
        aaa:1,
        bbb:"dddd"
    },
    testList : [1,2,3,4,5]
}