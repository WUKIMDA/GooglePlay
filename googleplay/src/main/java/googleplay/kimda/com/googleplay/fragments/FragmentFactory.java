package googleplay.kimda.com.googleplay.fragments;

import java.util.HashMap;

import googleplay.kimda.com.googleplay.basic.BaseFragment;

/**
 * Created by BUTTON on 2017-05-25.
 */

public class FragmentFactory {
    // 保存Fragment集合,方便复用
    private static HashMap<Integer, BaseFragment> sFragmentMap = new HashMap<>();
    // 根据指针位置,生产相应的Fragment

    public static BaseFragment createOrGetFragmentint(int position) {

        //缓存判断
        if (sFragmentMap.containsKey(position)) {
            return sFragmentMap.get(position);
        }
        BaseFragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new AppFragment();
                break;
            case 2:
                fragment = new GameFragment();
                break;
            case 3:
                fragment = new SubjectFragment();
                break;
            case 4:
                fragment = new RecommendFragment();
                break;
            case 5:
                fragment = new CategoryFragment();
                break;
            case 6:
                fragment = new RankFragment();
                break;
        }
        sFragmentMap.put(position, fragment);
        return fragment;
    }


}
