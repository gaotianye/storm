package cn.celloud.crxy.storm.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordCountLineBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;

	private Map stormConf;
	private TopologyContext context;
	private OutputCollector collector;
	
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.stormConf = stormConf;
		this.context = context;
		this.collector = collector;
	}

	public void execute(Tuple input) {
		String line = input.getStringByField("line");
		String[] words = line.split("\t");
		for (String word : words) {
			if(!word.trim().equals("")){
				this.collector.emit(new Values(word));
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
