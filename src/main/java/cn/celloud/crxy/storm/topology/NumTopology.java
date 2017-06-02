package cn.celloud.crxy.storm.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import cn.celloud.crxy.storm.bolt.NumBolt;
import cn.celloud.crxy.storm.spout.NumSpout;

public class NumTopology {
	public static void main(String[] args) {
		TopologyBuilder topologyBuilder = new TopologyBuilder();
		//下面这个id可以随意起名字
		topologyBuilder.setSpout("a", new NumSpout());
		// 指定让numbolt接收numspout的输出，通过shuffergrouping
		topologyBuilder.setBolt("b", new NumBolt()).shuffleGrouping("a");

		// 创建一个本地集群来运行topology任务
		LocalCluster localCluster = new LocalCluster();
		// 把topology提交到集群中运行
		localCluster.submitTopology("localTopology", new Config(), topologyBuilder.createTopology());
	}
}
