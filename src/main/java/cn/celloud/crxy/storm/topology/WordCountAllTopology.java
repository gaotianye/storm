package cn.celloud.crxy.storm.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import cn.celloud.crxy.storm.bolt.WordCountAllBolt;
import cn.celloud.crxy.storm.bolt.WordCountLineBolt;
import cn.celloud.crxy.storm.bolt.WordCountWordBoltByField;
import cn.celloud.crxy.storm.spout.WordCountSpout;
/**
 * 多线程处理 分组合并
 * @author Administrator
 *
 */
public class WordCountAllTopology {
	public static void main(String[] args) {
		TopologyBuilder topologyBuilder = new TopologyBuilder();
		//下面这个id可以随意起名字
		topologyBuilder.setSpout("a", new WordCountSpout());
		// 指定让wordcountlinebolt接收wordcountspout的输出，通过shuffergrouping
		topologyBuilder.setBolt("b", new WordCountLineBolt()).shuffleGrouping("a");
		// 指定让wordcountwordboltByField接收wordcountlinebolt的输出，通过fieldsGrouping
		topologyBuilder.setBolt("c", new WordCountWordBoltByField(),2).fieldsGrouping("b", new Fields("word"));
		topologyBuilder.setBolt("d", new WordCountAllBolt()).shuffleGrouping("c");

		// 创建一个本地集群来运行topology任务
		LocalCluster localCluster = new LocalCluster();
		// 把topology提交到集群中运行
		localCluster.submitTopology("localTopology", new Config(), topologyBuilder.createTopology());
//		try {
//			StormSubmitter.submitTopology("WordCountTopology", new Config(), topologyBuilder.createTopology());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
