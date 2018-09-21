package utilpacket.viewhelp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

/**
 * 用于切换布局，传入内容页，方便后面替换
 */
public class VaryViewHelperX implements IVaryViewHelper {

    private IVaryViewHelper helper;
    private View view;

    public VaryViewHelperX(View view) {
        super();
        this.view = view;
        ViewGroup group = (ViewGroup) view.getParent();
        LayoutParams layoutParams = view.getLayoutParams();
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        group.removeView(view);
        //使用内容view的params，保持一致
        group.addView(frameLayout, layoutParams);
        //填充父布局，以便显示在父布局的中间位置(布局中设定)
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        frameLayout.addView(view, params);
        helper = new VaryViewHelper(view);
    }

    @Override
    public View getCurrentLayout() {
        return helper.getCurrentLayout();
    }

    @Override
    public void restoreView() {
        helper.restoreView();
    }

    @Override
    public void showLayout(View view) {
        helper.showLayout(view);
    }

    @Override
    public void showLayout(int layoutId) {
        showLayout(inflate(layoutId));
    }

    @Override
    public View inflate(int layoutId) {
        return helper.inflate(layoutId);
    }

    @Override
    public Context getContext() {
        return helper.getContext();
    }

    @Override
    public View getView() {
        return view;
    }
}
