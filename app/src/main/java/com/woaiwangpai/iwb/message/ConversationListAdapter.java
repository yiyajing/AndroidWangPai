package com.woaiwangpai.iwb.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationProvider;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.holder.ConversationBaseHolder;
import com.tencent.qcloud.tim.uikit.modules.conversation.interfaces.IConversationAdapter;
import com.tencent.qcloud.tim.uikit.modules.conversation.interfaces.IConversationProvider;
import com.woaiwangpai.iwb.R;

import java.util.ArrayList;
import java.util.List;

public class ConversationListAdapter extends IConversationAdapter {

    private List<ConversationInfo> mDataSource = new ArrayList<>();
    private NewConversationListLayout.OnItemClickListener mOnItemClickListener;
    private NewConversationListLayout.OnItemLongClickListener mOnItemLongClickListener;
    public boolean mIsShowUnreadDot = true;
    public boolean mIsShowItemRoundIcon = false;
    public int mTopTextSize;
    public int mBottomTextSize;
    public int mDateTextSize;
    private Context context;

    public void setOnItemClickListener(NewConversationListLayout.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(NewConversationListLayout.OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public ConversationListAdapter(Context context) {
        this.context = context;
    }

    public void setDataProvider(IConversationProvider provider) {
        mDataSource = provider.getDataSource();
        if (provider instanceof ConversationProvider) {
            provider.attachAdapter(this);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(TUIKit.getAppContext());
        RecyclerView.ViewHolder holder = null;
        // 创建不同的 ViewHolder
        View view;
        // 根据ViewType来创建条目
        if (viewType == ConversationInfo.TYPE_CUSTOM) {
            view = inflater.inflate(R.layout.new_conversation_custom_adapter, parent, false);
            holder = new NewConversationCustomHolder(view);
        } else {
            view = inflater.inflate(R.layout.new_conversation_adapter, parent, false);
            holder = new NewConversationCommonHolder(view,context);
        }
        if (holder != null) {
            ((ConversationBaseHolder) holder).setAdapter(this);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ConversationInfo conversationInfo = getItem(position);
        ConversationBaseHolder baseHolder = (ConversationBaseHolder) holder;
        switch (getItemViewType(position)) {
            case ConversationInfo.TYPE_CUSTOM:
                break;
            default:
                //设置点击和长按事件
                if (mOnItemClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickListener.onItemClick(view, position, conversationInfo);
                        }
                    });
                }
                if (mOnItemLongClickListener != null) {
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            mOnItemLongClickListener.OnItemLongClick(view, position, conversationInfo);
                            return true;
                        }
                    });
                }
                break;
        }
        baseHolder.layoutViews(conversationInfo, position);
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof NewConversationCommonHolder) {
            ((NewConversationCommonHolder)holder).conversationIconView.setBackground(null);
        }
    }

    public ConversationInfo getItem(int position) {
        if (mDataSource.size() == 0)
            return null;
        return mDataSource.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSource != null) {
            ConversationInfo conversation = mDataSource.get(position);
            return conversation.getType();
        }
        return 1;
    }

    public void addItem(int position, ConversationInfo info) {
        mDataSource.add(position, info);
        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mDataSource.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void setItemTopTextSize(int size) {
        mTopTextSize = size;
    }

    public void setItemBottomTextSize(int size) {
        mBottomTextSize = size;
    }

    public void setItemDateTextSize(int size) {
        mDateTextSize = size;
    }

    public void enableItemRoundIcon(boolean flag) {
        mIsShowItemRoundIcon = flag;
    }

    public void disableItemUnreadDot(boolean flag) {
        mIsShowUnreadDot = !flag;
    }


}
