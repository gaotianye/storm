package cn.celloud.crxy.storm.bolt;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
/**
 * 处理部分数据
 * @author Administrator
 *
 */
public class WordCountWordBoltByField extends BaseRichBolt {

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
		System.out.println("线程："+Thread.currentThread().getId()+",word:"+word+",value:"+value);
		this.collector.emit(new Values(word,value));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word","count"));
	}

}
