package cn.celloud.crxy.storm.project1.consumer;
/**
 * kafka参数参考：http://debugo.com/kafka-params/
 * @author Administrator
 *
 */
public class Constant {
	//topic
	static final String TOPIC = "storm_topic_20170606";
	//group_id
	static final String GROUP_ID = "group_20170606";
	//zk_connect
	static final String ZK_CONNECT = "master:2181,slave1:2181,slave2:2181";
	//连接zk的超时时间
	//zk_conn_timeout_ms 
	static final String ZK_CONN_TIMEOUT_MS = "400";
	//zookeeper的心跳超时时间，查过这个时间就认为是无效的消费者
	//zookeeper.session.timeout.ms
	static final String ZK_SESSION_TIMEOUT_MS = "400";
	//ZooKeeper集群中leader和follower之间的同步实际
	//zookeeper.sync.time.ms
	static final String ZK_SYNC_TIME_MS = "200";
	//是否自动提交,true时，Consumer会在消费消息后将offset同步到zookeeper，这样当Consumer失败后，新的consumer就能从zookeeper获取最新的offset
	//auto.commit.enable 
	static final String AUTO_COMMIT = "true";
	//自动提交的时间间隔
	//auto.commit.interval.ms 
	static final String AUTO_COMMIT_INTERVAL = "60000";
	
	
	

}
