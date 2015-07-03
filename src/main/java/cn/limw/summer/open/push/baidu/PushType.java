package cn.limw.summer.open.push.baidu;

/**
 * @author li
 * @version 1 (2014年11月27日 下午2:53:36)
 * @since Java7
 */
public interface PushType {
    /**
     * 推送给 1：单个人，必须指定user_id 和 channel_id （指定用户的指定设备）或者user_id（指定用户的所有设备）
     */
    Integer ONE = 1;

    /**
     * 推送给 2：一群人，必须指定 tag
     */
    Integer SOME = 2;

    /**
     * 推送给 3：所有人，无需指定tag、user_id、channel_id
     */
    Integer ALL = 3;
}