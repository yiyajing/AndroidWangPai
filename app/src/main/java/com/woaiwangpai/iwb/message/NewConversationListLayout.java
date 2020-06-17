package com.woaiwangpai.iwb.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tim.uikit.component.CustomLinearLayoutManager;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.interfaces.IConversationAdapter;

public class NewConversationListLayout extends RecyclerView implements INewConversationListLayout {

    private ConversationListAdapter mAdapter;

    public NewConversationListLayout(Context context) {
        super(context);
        init();
    }

    public NewConversationListLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NewConversationListLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @SuppressLint("WrongConstant")
    public void init() {
        setLayoutFrozen(false);
        setItemViewCacheSize(0);
        setHasFixedSize(true);
        setFocusableInTouchMode(false);
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setAdapter(IConversationAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (ConversationListAdapter) adapter;
    }

    @Override
    public void setBackground(int resId) {
        setBackgroundColor(resId);
    }


    @Override
    public void disableItemUnreadDot(boolean flag) {
        mAdapter.disableItemUnreadDot(flag);
    }

    @Override
    public void enableItemRoundIcon(boolean flag) {
//        mAdapter.enableItemRoundIcon(flag);
    }

    @Override
    public void setItemTopTextSize(int size) {
        mAdapter.setItemTopTextSize(size);
    }

    @Override
    public void setItemBottomTextSize(int size) {
        mAdapter.setItemBottomTextSize(size);
    }

    @Override
    public void setItemDateTextSize(int size) {
        mAdapter.setItemDateTextSize(size);
    }

    @Override
    public NewConversationListLayout getListLayout() {
        return this;
    }

    @Override
    public ConversationListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        mAdapter.setOnItemClickListener((NewConversationListLayout.OnItemClickListener) listener);
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mAdapter.setOnItemLongClickListener(listener);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ConversationInfo messageInfo);
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(View view, int position, ConversationInfo messageInfo);
    }
}
