package cn.celloud.crxy.storm.bolt;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
/**
 * 应付field的综合处理
 * 求：1、单词出现的总次数
 *    2、总共出现多少个去重的单词
 * @author Administrator
 *
 */
public class WordCountAllBolt extends BaseRichBolt {

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
		Integer count = input.getIntegerByField("count");
		hashMap.put(word, count);
		int sum = 0;
		for (String key : hashMap.keySet()) {
			Integer i = hashMap.get(key);
			sum +=i;
		}
		System.out.println("单词出现的总次数；"+sum);
		System.out.println("去重之后单词的个数："+hashMap.size());
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
