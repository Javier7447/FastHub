package com.fastaccess.ui.modules.main.issues.pager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.TextView;

import com.fastaccess.R;
import com.fastaccess.data.dao.FragmentPagerAdapterModel;
import com.fastaccess.helper.ViewHelper;
import com.fastaccess.ui.adapter.FragmentsPagerAdapter;
import com.fastaccess.ui.base.BaseFragment;
import com.fastaccess.ui.widgets.SpannableBuilder;
import com.fastaccess.ui.widgets.ViewPagerView;

import butterknife.BindView;

/**
 * Created by Kosh on 26 Mar 2017, 12:14 AM
 */

public class MyIssuesPagerView extends BaseFragment<MyIssuesPagerMvp.View, MyIssuesPagerPresenter> implements MyIssuesPagerMvp.View {

    public static final String TAG = MyIssuesPagerView.class.getSimpleName();

    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.pager) ViewPagerView pager;

    public static MyIssuesPagerView newInstance() {
        return new MyIssuesPagerView();
    }

    @Override protected int fragmentLayout() {
        return R.layout.centered_tabbed_viewpager;
    }

    @Override protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pager.setAdapter(new FragmentsPagerAdapter(getChildFragmentManager(), FragmentPagerAdapterModel.buildForMyIssues(getContext())));
        tabs.setupWithViewPager(pager);
    }

    @NonNull @Override public MyIssuesPagerPresenter providePresenter() {
        return new MyIssuesPagerPresenter();
    }

    @Override public void onSetBadge(int tabIndex, int count) {
        if (tabs != null) {
            TextView tv = ViewHelper.getTabTextView(tabs, tabIndex);
            tv.setText(SpannableBuilder.builder()
                    .append(tabIndex == 0 ? getString(R.string.opened) : getString(R.string.closed))
                    .append("   ")
                    .append("(")
                    .bold(String.valueOf(count))
                    .append(")"));
        }
    }
}
