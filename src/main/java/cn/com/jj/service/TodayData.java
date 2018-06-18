package cn.com.jj.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
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

public class TodayData {

	private static Logger logger = LoggerFactory.getLogger(LoadTotalIds.class);

	public static void load() {
		Jedis jedis = RedisClient.getInstance();
		Set<String> ids = new HashSet<String>();
		try {
			ids = jedis.smembers("jijin.ids");
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
						detail(id);
					}
				});
				break;
			}
		}
	}

	public static void detail(String id) {
		for (int i = 0; i < 3; i++) {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			final String url = "http://api.fund.eastmoney.com/f10/lsjz?fundCode=%s&pageIndex=0&pageSize=2&startDate=&endDate=&_=1528601899541";
			try {
				List<DetailModel> details = null;
				String localUrl = String.format(url, id);
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
				details = model.getData().getLSJZList();

				String today = DateTime.now().toString("yyyy-MM-dd");
				List<DetailModel> list = new ArrayList<DetailModel>();
				if (null != details && details.size() > 0) {
					for (DetailModel detailModel : details) {
						System.out.println(detailModel.toString());
						if (StringUtils.equals(today, detailModel.getFSRQ())) {
							detailModel.setCode(id);
							list.add(detailModel);
							break;
						}
					}
				}

				SaveDatas.save(list);
				logger.error("id-" + id + "----today-" + today + " success !");
				if (i > 0) {
					logger.error("id-" + id + "----today-" + today + " 重试success !");
				}
				break;
			} catch (Exception e) {
				logger.error("", e);
			}

			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
