package cn.celloud.crxy.storm.bolt;

import java.util.Map;

import org.json.JSONObject;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import cn.celloud.crxy.storm.utils.PageUtil;

public class MyBoltByIp extends BaseRichBolt {
	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	
	/**
	 * 初始化操作
	 */
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void execute(Tuple input) {
		String ip = input.getStringByField("ip");
		//根据ip获取对应的省份信息，可以本地维护一个ip库，如果本地ip库中没找到，那么就去互联网上找
		String content = PageUtil.getContent("http://int.dpool.sina.com.cn/iplookup/iplookup.php?ip="+ip+"&format=json");
		JSONObject jsonObject = new JSONObject(content);
		String city = jsonObject.getString("city");
		this.collector.emit(new Values(city));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("city"));
	}

}
