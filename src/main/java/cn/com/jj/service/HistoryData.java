package cn.com.jj.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.com.jj.common.ThreadPool;
import cn.com.jj.model.DetailModel;
import cn.com.jj.model.Model;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import redis.clients.jedis.Jedis;

public class HistoryData {
	private static Logger logger = LoggerFactory.getLogger(LoadTotalIds.class);

	private static AtomicInteger count = new AtomicInteger();

	public static void synHistoryDatas() {
		Jedis jedis = RedisClient.getInstance();
		Set<String> ids = new HashSet<String>();
		try {
			// String exitst = jedis.get("history");
			// if (StringUtils.isNumeric(exitst)) {
			// return;
			// }
			ids = jedis.smembers("jijin.ids");
			count.set(ids.size());
			jedis.set("total", ids.size() + "");
			// jedis.incr("history");
		} catch (Exception e) {
			logger.error("从缓存取所有id异常：", e);
		}
		if (null != jedis) {
			if (null != jedis) {
				jedis.close();
			}
		}

		if (null != ids && ids.size() > 0) {
			for (final String id : ids) {
				ThreadPool.execute(new Runnable() {
					@Override
					public void run() {
						logger.error("添加任務" + id);
						detail(id);
					}
				});
			}
		}
	}

	public static void detail(String id) {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		final String url = "http://api.fund.eastmoney.com/f10/lsjz?fundCode=%s&pageIndex=%s&pageSize=30&startDate=&endDate=&_=1528601899541";
		try {
			List<DetailModel> details = new ArrayList<DetailModel>();
			int index = 0;
			while (true) {
				String localUrl = String.format(url, id, index);
				Request req = new Request.Builder().url(localUrl).header("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
						.addHeader("Accept", "*/*").addHeader("DNT", "1").addHeader("Host", "api.fund.eastmoney.com")
						.addHeader("Referer", "http://fund.eastmoney.com/f10/jjjz_003407.html").build();
				Response res = client.newCall(req).execute();
				String resString = "";
				if (res.isSuccessful()) {
					resString = res.body().string();
				} else {
					throw new RuntimeException();
				}
				Model model = JSON.toJavaObject(JSON.parseObject(resString), Model.class);
				List<DetailModel> detail = model.getData().getLSJZList();

				if (detail.isEmpty()) {
					break;
				} else {
					details.addAll(detail);
				}
				index++;
			}
			for (DetailModel detailModel : details) {
				detailModel.setCode(id);
			}
			SaveDatas2.save(details);
			int decrementAndGet = count.decrementAndGet();
			logger.error("id-" + id + " success ! count = " + decrementAndGet);

		} catch (Exception e) {
			logger.error("", e);
			Jedis jedis = RedisClient.getInstance();
			try {
				jedis.lpush("err.ids", id);
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				if (null != jedis) {
					jedis.close();
				}
			}
		}
	}
}
