package com.teko.hoangviet.androidtest.custom.view;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.teko.hoangviet.androidtest.R;
import com.teko.hoangviet.androidtest.base.ui.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewController<T extends BaseFragment> {

    public static String a = "314542";
    private int layoutId;
    private FragmentManager fragmentManager;
    private List<T> listAddFragment;
    //private List<Class<T>> listClass;
    private T currentFragment = null;

    private OnFragmentChangedListener onFragmentChangedListener;

    /**
     * Constructor
     *
     * @param fragmentManager FragmentManager
     * @param layoutId        resource layout id of fragment
     */
    public ViewController(FragmentManager fragmentManager, int layoutId) {
        this.fragmentManager = fragmentManager;
        this.layoutId = layoutId;
        listAddFragment = new ArrayList<>();
        //listClass = new ArrayList<>();
    }

    public static String wss_b() {
        return "0742e61692f77732f6c69766563686174";
    }

    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

//    public void replaceFragment(BaseFragment fragment, Map<String, Object> data) {
//        if (!listClass.isEmpty() && getCurrentFragment().getClass().getName().equalsIgnoreCase(fragment.getClass().getName())) {
//            return;
//        }
//        if (data != null) {
//            fragment.setData(data);
//        }
//        fragment.setViewController(this);
//        fragmentManager.beginTransaction().replace(layoutId, fragment).commit();
//        listClass.add(fragment);
//    }

    public void replaceFragment(Class<T> type, HashMap<String, Object> data) {
        /*
        // Comment: open two notification at the same time, will open two instances of a fragment
        if (currentFragment != null && currentFragment.getClass().getName().equalsIgnoreCase(type.getName())) {
            return;
        }*/
        try {
            currentFragment = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (data != null) {
            currentFragment.setData(data);
        }
        currentFragment.setViewController(this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out,
                R.anim.trans_right_in, R.anim.trans_right_out);
        fragmentTransaction.replace(layoutId, currentFragment).commitAllowingStateLoss();
        listAddFragment.remove(listAddFragment.size() - 1);
        listAddFragment.add(currentFragment);
        if (onFragmentChangedListener != null) {
            onFragmentChangedListener.onFragmentChanged();
        }
    }

    public void addFragment(Class<T> type, HashMap<String, Object> data, boolean hasAnimation, boolean isHideOldFragment) {
        /*
        // Comment: open two notification at the same time, will open two instances of a fragment
        if (currentFragment != null && currentFragment.getClass().getName().equalsIgnoreCase(type.getName())) {
            return;
        }*/
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (hasAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.trans_right_in, R.anim.trans_right_in);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.animation_none, R.anim.animation_none);
        }
        T newFragment = null;
        try {
            newFragment = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (newFragment != null && data != null) {
            newFragment.setData(data);
        }
        if (newFragment != null) {
            newFragment.setViewController(this);
            fragmentTransaction.add(layoutId, newFragment, type.getSimpleName());
            if (currentFragment != null) {
//                if (hasAnimation) {
//                    fragmentTransaction.setCustomAnimations(R.anim.animation_in_delay, R.anim.animation_in_delay);
//                } else {
//                    fragmentTransaction.setCustomAnimations(R.anim.animation_none, R.anim.animation_none);
//                }
                currentFragment.setUserVisibleHint(false);
//                if (isHideOldFragment) {
//                    fragmentTransaction.hide(currentFragment);
//                }
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
        listAddFragment.add(newFragment);
        currentFragment = newFragment;
        if (onFragmentChangedListener != null) {
            onFragmentChangedListener.onFragmentChanged();
        }
    }

    public void addFragmentUpAnimation(Class<T> type, HashMap<String, Object> data, boolean hasAnimation) {
        /*
        // Comment: open two notification at the same time, will open two instances of a fragment
        if (currentFragment != null && currentFragment.getClass().getName().equalsIgnoreCase(type.getName())) {
            return;
        }*/
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (hasAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_up);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.animation_none, R.anim.animation_none);
        }
        T newFragment = null;
        try {
            newFragment = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (newFragment != null && data != null) {
            newFragment.setData(data);
        }
        if (newFragment != null) {
            newFragment.setViewController(this);
            fragmentTransaction.add(layoutId, newFragment, type.getSimpleName());
            if (currentFragment != null) {
                if (hasAnimation) {
                    fragmentTransaction.setCustomAnimations(R.anim.animation_in_delay, R.anim.animation_in_delay);
                } else {
                    fragmentTransaction.setCustomAnimations(R.anim.animation_none, R.anim.animation_none);
                }
                fragmentTransaction.hide(currentFragment);
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
        listAddFragment.add(newFragment);
        currentFragment = newFragment;
        if (onFragmentChangedListener != null) {
            onFragmentChangedListener.onFragmentChanged();
        }
    }

    public void addFragmentUpAnimation(Class<T> type, HashMap<String, Object> data) {
        addFragmentUpAnimation(type, data, true);
    }

    public void addFragment(Class<T> type, HashMap<String, Object> data) {
        addFragment(type, data, true, true);
    }

    public boolean backFromAddFragment(HashMap<String, Object> data) {
        if (listAddFragment.size() >= 2) {
            listAddFragment.remove(listAddFragment.size() - 1);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.trans_right_out, R.anim.trans_right_out);
            fragmentTransaction.remove(currentFragment);
            currentFragment = listAddFragment.get(listAddFragment.size() - 1);
            if (data != null) {
                currentFragment.setData(data);
            }
            currentFragment.setViewController(this);
            currentFragment.setUserVisibleHint(true);
//            fragmentTransaction.setCustomAnimations(R.anim.animation_none, R.anim.animation_none);
            fragmentTransaction.show(currentFragment);
            fragmentTransaction.commitAllowingStateLoss();
            currentFragment.backFromAddFragment();
            if (onFragmentChangedListener != null) {
                onFragmentChangedListener.onFragmentChanged();
            }
            return true;
        } else {
            return false;
        }

    }

    public boolean removeAllFragmentExceptFirst(HashMap<String, Object> data) {
        if (listAddFragment.size() >= 2) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = listAddFragment.size() - 1; i > 0; i--) {
                fragmentTransaction.remove(listAddFragment.get(i));
            }
            currentFragment = listAddFragment.get(0);
            listAddFragment.clear();
            listAddFragment.add(currentFragment);

            if (data != null) {
                currentFragment.setData(data);
            }
            currentFragment.setViewController(this);
            currentFragment.setUserVisibleHint(true);
            fragmentTransaction.show(currentFragment);
            fragmentTransaction.commitAllowingStateLoss();
            currentFragment.backFromAddFragment();
            if (onFragmentChangedListener != null) {
                onFragmentChangedListener.onFragmentChanged();
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean backFromAddFragmentDownAnimation(HashMap<String, Object> data) {
        if (listAddFragment.size() >= 2) {
            listAddFragment.remove(listAddFragment.size() - 1);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(currentFragment);
            fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_down);
            currentFragment = listAddFragment.get(listAddFragment.size() - 1);
            if (data != null) {
                currentFragment.setData(data);
            }
            currentFragment.setViewController(this);
            currentFragment.setUserVisibleHint(true);
            fragmentTransaction.setCustomAnimations(R.anim.animation_none, R.anim.animation_none);
            fragmentTransaction.show(currentFragment);
            fragmentTransaction.commitAllowingStateLoss();
            currentFragment.backFromAddFragment();
            if (onFragmentChangedListener != null) {
                onFragmentChangedListener.onFragmentChanged();
            }
            return true;
        } else {
            return false;
        }
    }

    public Fragment findFragmentByTag(String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

    public void setOnFragmentChangedListener(OnFragmentChangedListener onFragmentChangedListener) {
        this.onFragmentChangedListener = onFragmentChangedListener;
    }

    public interface OnFragmentChangedListener {
        void onFragmentChanged();
    }
}
