package com.stone.shop.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.ad.android.sdk.api.AdSdk;
import com.stone.shop.R;
import com.stone.shop.adapter.BXTListAdapter;
import com.stone.shop.model.BXTNews;

/**
 * 教学类-博学堂界面
 * 
 * @date 2014-5-10
 * @author Stone
 */
public class BXTActivity extends Activity implements OnItemClickListener {

	private static final String TAG = "BXTActivity";

	private ListView lvBXTNews;
	private BXTListAdapter mBxtListAdapter;
	private List<BXTNews> mBXTNewsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bxt);

		insertAds();
		initView();
		initData();

	}

	private void insertAds() {
		AdSdk ad = AdSdk.getInstace(getApplicationContext()); // 获取广告实例
		// 设置banner广告账号
		ad.setInsertAdPid("33e98a00123b5b9031df30bdf60a1445");
		ad.setAccount("596017443@qq.com"); // 设置用户账号可不设置
		// 为这个banner广告指派一种布局方式
		RelativeLayout layout = new RelativeLayout(getApplicationContext());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		addContentView(layout, params); // 将layout追加到当前activity的contentview
		if (ad.isInsertAdPrepared()) { // 在需要显示插屏广告时检测插屏广告是否已准备好
			ad.showInsertAd(getApplicationContext(), layout); // 显示banner广告
		}

	}

	private void initView() {
		lvBXTNews = (ListView) findViewById(R.id.lv_bxt_news);
		mBXTNewsList = new ArrayList<BXTNews>();
		mBxtListAdapter = new BXTListAdapter(this, mBXTNewsList);
		lvBXTNews.setAdapter(mBxtListAdapter);
		lvBXTNews.setOnItemClickListener(this);
	}

	private void initData() {
		BmobQuery<BXTNews> query = new BmobQuery<BXTNews>();
		query.findObjects(this, new FindListener<BXTNews>() {

			@Override
			public void onSuccess(List<BXTNews> newsList) {
				// toast("查询商品成功, 共" + newsList.size());
				if (newsList.size() == 0)
					toast("亲, 暂时还木有讲座哦");
				else {
					mBXTNewsList = newsList;
					mBxtListAdapter.refresh(newsList);
					mBxtListAdapter.notifyDataSetChanged();
				}

			}

			@Override
			public void onError(int arg0, String arg1) {
				toast("查询失败");
			}
		});
	}
	
	private void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent toBXTNewsActivity = new Intent(BXTActivity.this,
				BXTNewsActivity.class);
		toBXTNewsActivity.putExtra("title", mBXTNewsList.get(position)
				.getTitle());
		toBXTNewsActivity.putExtra("topic", mBXTNewsList.get(position)
				.getTopic());
		toBXTNewsActivity.putExtra("speaker", mBXTNewsList.get(position)
				.getSpeaker());
		toBXTNewsActivity
				.putExtra("time", mBXTNewsList.get(position).getTime());
		toBXTNewsActivity.putExtra("location", mBXTNewsList.get(position)
				.getLocation());
		toBXTNewsActivity.putExtra("holder1", mBXTNewsList.get(position)
				.getHolder1());
		toBXTNewsActivity.putExtra("holder2", mBXTNewsList.get(position)
				.getHolder2());
		toBXTNewsActivity.putExtra("points", mBXTNewsList.get(position)
				.getPoints());
		toBXTNewsActivity.putExtra("speakerinfo", mBXTNewsList.get(position)
				.getSpeakerinfo());
		startActivity(toBXTNewsActivity);
	}

}
