package com.sin.android.sinlibs.exutils;

import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

public class ViewUtils {
	/**
	 * 绑定RadioGroup和ViewPager,使之成为联动的Tab选项卡
	 * 
	 * @param rg
	 *            RadioGroup选项卡
	 * @param vp
	 *            ViewPager页面
	 * @param rbids
	 *            RadioGroup的RadioButton成员ID
	 */
	public static void bindTabsViewPagers(final RadioGroup rg, final ViewPager vp, final int[] rbids) {
		vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int ix) {
				rg.check(rbids[ix]);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup rg, int rbid) {
				for (int i = 0; i < rbids.length; ++i) {
					if (rbids[i] == rbid) {
						//
						if (vp.getCurrentItem() != i) {
							vp.setCurrentItem(i);
						}
						break;
					}
				}
			}
		});
	}
}
