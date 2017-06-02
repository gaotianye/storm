package cn.celloud.crxy.storm.bolt;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class WordCountWordBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;

	private Map stormConf;
	private TopologyContext context;
	private OutputCollector collector;
	
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.stormConf = stormConf;
		this.context = context;
		this.collector = collector;
	}
	
	HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
	public void execute(Tuple input) {
		String word = input.getStringByField("word");
		Integer value = hashMap.get(word);
		if(value==null){
			value = 0;
		}
		value++;
		hashMap.put(word, value);
		for (String key : hashMap.keySet()) {
			System.out.println("key:"+key+",count:"+hashMap.get(key));
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
