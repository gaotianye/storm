package cn.celloud.crxy.storm.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import cn.celloud.crxy.storm.bolt.MyBoltByIp;
import cn.celloud.crxy.storm.bolt.MyBoltCount;
import cn.celloud.crxy.storm.spout.MyDataSpout;

public class BBSTopology {
	public static void main(String[] args) {
		TopologyBuilder topologyBuilder = new TopologyBuilder();
		//可以提高并行度
		topologyBuilder.setSpout("spout", new MyDataSpout());
		//可以提高并行度
		topologyBuilder.setBolt("bolt1", new MyBoltByIp()).shuffleGrouping("spout");
		//只使用一个线程来向数据库中插入数据
		topologyBuilder.setBolt("bolt2", new MyBoltCount()).shuffleGrouping("bolt1");
		
		LocalCluster localCluster = new LocalCluster();
		String simpleName = BBSTopology.class.getSimpleName();
		Config config = new Config();
		//config.setMaxSpoutPending(100);//这个配置的生效条件是要开启消息确认机制
		localCluster.submitTopology(simpleName, config, topologyBuilder.createTopology());
	}
}
