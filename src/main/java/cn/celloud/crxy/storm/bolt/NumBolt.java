package cn.celloud.crxy.storm.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class NumBolt extends BaseRichBolt {
	private static final long serialVersionUID = 1L;
	
	private Map stormConf;
	private TopologyContext context;
	private OutputCollector collector;
	
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.stormConf = stormConf;
		this.context = context;
		this.collector = collector;
	}

	int sum = 0;
	public void execute(Tuple input) {
		Integer num = input.getIntegerByField("num");
//		sum +=num;
		System.out.println("线程："+Thread.currentThread().getId()+"num="+num);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}
