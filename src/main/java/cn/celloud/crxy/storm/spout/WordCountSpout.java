package cn.celloud.crxy.storm.spout;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class WordCountSpout extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	private Map conf;
	private TopologyContext context;
	private SpoutOutputCollector collector;
	
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.conf = conf;
		this.context = context;
		this.collector = collector;
	}

	public void nextTuple() {
		// 需要监控指定目录下面文件的变化，发现有新文件的话就读取过来
		//第一个参数：监控的目录
		//第二个参数：监控的后缀名
		//第三个参数：是否递归读取文件目录下的数据
		Collection<File> listFiles = FileUtils.listFiles(new File("H:/test"), new String[]{"txt"}, true);
		
//		Collection<File> listFiles = FileUtils.listFiles(new File("/gaotianye/"), new String[]{"txt"}, true);
		for (File file : listFiles) {
			List<String> lines = null;
			try {
				lines = FileUtils.readLines(file);
				for (String line : lines) {
					this.collector.emit(new Values(line));
				}
				FileUtils.moveFile(file, new File(file.getAbsolutePath()+System.currentTimeMillis()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("line"));
	}

}
