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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用于切换布局,用一个新的布局替换掉原先的布局
 */
public class VaryViewHelper implements IVaryViewHelper {
    /**
     * 内容页(与构造方法里的参数一致)
     */
    private View view;
    /**
     * 各种加载提示布局的父布局(便于控制它的子布局)
     */
    private ViewGroup parentView;
    private ViewGroup.LayoutParams params;
    /**
     * 当前正在显示的view
     */
    private View currentView;

    public VaryViewHelper(View view) {
        super();
        this.view = view;

    }

    private void init() {
        //这里获取的是内容view的params，如果内容view是warp_content的，界面显示可能不雅观(如view是一个ListView
        // 此时提示的界面会显示在上方)
        params = view.getLayoutParams();
        //获取内容布局的父布局
        parentView = (ViewGroup) view.getParent();
        currentView = view;
    }

    @Override
    public View getCurrentLayout() {
        return currentView;
    }

    @Override
    public void restoreView() {
        showLayout(view);
    }

    @Override
    public void showLayout(View view) {
        if (parentView == null) {
            init();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            //需要先移除才能添加,一个view不能同时拥有两个父容器
            parent.removeView(view);
        }
        parentView.removeAllViews();
        parentView.addView(view,params);
        currentView = view;
    }


    @Override
    public void showLayout(int layoutId) {
        showLayout(inflate(layoutId));
    }

    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(view.getContext()).inflate(layoutId, null);
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public View getView() {
        return view;
    }
}
