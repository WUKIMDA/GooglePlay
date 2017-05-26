package googleplay.kimda.com.googleplay.fragments;

import java.util.HashMap;

/**
 * Created by BUTTON on 2017-05-25.
 */

public class FragmentFactory {
    // 保存Fragment集合,方便复用
    private static HashMap<Integer, BaseFragment> sFragmentMap = new HashMap<Integer, BaseFragment>();
    // 根据指针位置,生产相应的Fragment
    public static BaseFragment createFragment(int position) {
        //缓存判断
        BaseFragment fragment = sFragmentMap.get(position);
        if (fragment == null) {
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

                default:
                    break;
            }
            sFragmentMap.put(position, fragment);
        }
        return fragment;
    }
}
