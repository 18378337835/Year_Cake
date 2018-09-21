/*
Copyright 2015 shizhefei（LuckyJayce）
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package utilpacket.viewhelp;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.hjn.year_cake.R;

/**
 * 自定义要切换的布局，通过IVaryViewHelper实现真正的切换<br>
 * 使用者可以根据自己的需求，使用自己定义的布局样式
 * 
 * @author LuckyJayce
 *
 */
public class LoadViewHelper {

	private IVaryViewHelper helper;

	public LoadViewHelper(View view) {
		this(new VaryViewHelper(view));
	}

	public LoadViewHelper(IVaryViewHelper helper) {
		super();
		this.helper = helper;
	}

	/**
	 * 数据错误时显示的布局
	 * @param onClickListener
	 */
	public void showError(OnClickListener onClickListener) {
		showError(onClickListener, R.string.data_error);
	}

	/**
	 * 数据错误时显示的布局
	 * @param onClickListener
	 */
	public void showError(OnClickListener onClickListener, int id) {
		View layout = helper.inflate(R.layout.load_error);
		TextView tv = (TextView) layout.findViewById(R.id.tv_error);
		tv.setText(id);
		layout.setOnClickListener(onClickListener);
		helper.showLayout(layout);
	}


	/**
	 * 空数据时显示默认的图片
	 * @param onClickListener：布局点击事件
     */
	public void showEmpty(OnClickListener onClickListener) {
//		showEmpty(onClickListener,R.mipmap.icon_no_data);
	}

	/**
	 * 空数据时要显示的布局
	 * @param onClickListener：布局点击事件
	 * @param imageId：要显示的图片Id
     */
	public void showEmpty(OnClickListener onClickListener, int imageId) {
		View layout = helper.inflate(R.layout.load_empty);
		ImageView imageView = (ImageView) layout.findViewById(R.id.iv_empty);
		imageView.setImageResource(imageId);
		layout.setOnClickListener(onClickListener);
		helper.showLayout(layout);
	}

	/**
	 * 空数据时要显示的布局
	 * @param onClickListener：布局点击事件
	 * @param resId：要显示的图片布局
	 */
	public void showEmpty(int resId,OnClickListener onClickListener) {
		View layout = helper.inflate(resId);
		layout.setOnClickListener(onClickListener);
		helper.showLayout(layout);
	}

	public void showLoading(String loadText) {
		View layout = helper.inflate(R.layout.load_ing);
		TextView textView = (TextView) layout.findViewById(R.id.tv_msg);
		textView.setText(loadText);
		helper.showLayout(layout);
	}

	/**
	 * loading显示的布局
	 */
	public void showLoading(){
		View layout = helper.inflate(R.layout.load_ing);
		TextView textView = (TextView) layout.findViewById(R.id.tv_msg);
//		textView.setText("正在加载中...");
		textView.setText(layout.getResources().getString(R.string.data_loading));
		helper.showLayout(layout);
	}

	/**
	 * 无网络时显示的布局
	 * @param onClickListener
	 */
	public void showNetwordNoConnect(OnClickListener onClickListener, int id) {
		View layout = helper.inflate(R.layout.load_no_network);
		TextView tv = (TextView) layout.findViewById(R.id.tv_no_network);
		tv.setText(id);
		layout.setOnClickListener(onClickListener);
		helper.showLayout(layout);
	}

	/**
	 * 无网络时显示的布局
	 * @param onClickListener
	 */
	public void showNetwordNoConnect(OnClickListener onClickListener) {
		showNetwordNoConnect(onClickListener, R.string.no_network);
	}


	public void restore() {
		helper.restoreView();
	}
}
