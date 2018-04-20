package com.yacob.weekend;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_menu extends Fragment {
    ResideMenu resideMenu;
    private Context mContext;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_menu, container, false);
        mContext = getActivity();
        resideMenu = new ResideMenu(mContext);
        resideMenu.attachToActivity(getActivity());
        resideMenu.setBackground(R.drawable.wallpaper);
        resideMenu.setShadowVisible(false);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);


        // create menu items;
        String titles[] = { "Home", "Profile", "Calendar", "Settings" };
        int icon[] = { R.drawable.menu2, R.drawable.menu2, R.drawable.menu3, R.drawable.menu5 };

        for (int i = 0; i < titles.length; i++){
            ResideMenuItem item = new ResideMenuItem(getContext(), icon[i], titles[i]);
            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_RIGHT); // or  ResideMenu.DIRECTION_RIGHT
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(container.getId())).commit();
                    //Fragment_home fragment_home= new Fragment_home();
                   // resideMenu.clearIgnoredViewList();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //fragmentTransaction.replace(container.getId(), fragment_home);
                    fragmentTransaction.remove(Fragment_menu.this);
                    fragmentTransaction.commit();
                    //resideMenu.clearIgnoredViewList();
                    resideMenu.closeMenu();
                }
            });

        }
        resideMenu.setMenuListener(menuListener);

        resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
        return view;

        // attach to current activity;
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

}
