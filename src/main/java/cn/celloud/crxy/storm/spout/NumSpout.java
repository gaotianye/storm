package cn.celloud.crxy.storm.spout;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
/**
 * 每秒发送一个递增的数字
 * @author Administrator
 *
 */
public class NumSpout extends BaseRichSpout {
	private static final long serialVersionUID = 1L;
	private Map conf;
	private TopologyContext context;
	private SpoutOutputCollector collector;
	
	/**
	 * 初始化操作，只会被执行一次
	 * Map conf：配置类，topology的配置信息
	 * TopologyContext context：topology上下文 SpoutOutputCollector
     * collector：发射器，负责向外发射数据
	 */
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.conf = conf;
		this.context = context;
		this.collector = collector;
	}
	
	/**
	 * 一个死循环，一直不停的调用这个方法，不断的发射数据
	 */
	int i = 1;
	public void nextTuple() {
		//emit传入的参数是 ArrayList，而Values继承了ArrayList
		this.collector.emit(new Values(i++));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 声明输出字段，也就是给发送出去的数据对应的起个名字。 
	 * 简单的说，如果上边没有发射出去，那么也就没有它存在的必要。
     * 上边发射几个元素，下面就要有几个元素和它一一对应。名字可以随意起。
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//Fields的参数是String...  对应前面的new Values()
		declarer.declare(new Fields("num"));
	}
}
