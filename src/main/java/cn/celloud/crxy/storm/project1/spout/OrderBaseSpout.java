package cn.celloud.crxy.storm.project1.spout;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import cn.celloud.crxy.storm.project1.consumer.OrderConsumer;

public class OrderBaseSpout extends BaseRichSpout {
	private static final long serialVersionUID = 1L;
	
	Queue<String> queue = new ConcurrentLinkedDeque<String>();
	
	private SpoutOutputCollector collector;
	private TopologyContext context;
	private Map conf;
	private Integer TaskId;
	private String topic;
	
	public OrderBaseSpout(String topic){
		this.topic = topic;
	}
	
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		TaskId = context.getThisTaskId();
		OrderConsumer consumer = new OrderConsumer(topic);
		consumer.start();
		queue = consumer.getQueue();
	}

	public void nextTuple() {
		if(queue.size()>0){
			String message = queue.poll();
			collector.emit(new Values(message));
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("order"));
	}
}
