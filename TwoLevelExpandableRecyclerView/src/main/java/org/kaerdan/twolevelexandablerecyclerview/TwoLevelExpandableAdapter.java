package org.kaerdan.twolevelexandablerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class TwoLevelExpandableAdapter<T extends TwoLevelExpandableAdapter.DataSet>
        extends RecyclerView.Adapter<ViewHolderWithSetter>  {

    public static final int HEADER_TYPE = 2;
    public static final int ITEM_TYPE = 1;

    private List<T> fullData;
    private List<DataHolder> dataList;
    private List<Boolean> isOpen;

    protected TwoLevelExpandableAdapter(List<T> data) {
        fullData = data;
        dataList = new ArrayList<>();
        isOpen = new ArrayList<>();
        for (T item: fullData) {
            dataList.add(new DataHolder(item.getData(), ItemType.HEADER));
            isOpen.add(false);
        }
    }

    protected TwoLevelExpandableAdapter(List<T> data, List<Boolean> state) {
        fullData = data;
        dataList = new ArrayList<>();
        isOpen = new ArrayList<>(state);
        for (int i = 0; i < fullData.size(); i++) {
            T headerGroup = fullData.get(i);
            dataList.add(new DataHolder(headerGroup.getData(), ItemType.HEADER));
            if (isOpen.get(i)) {
                for (Object item: headerGroup.getChildren()) {
                    dataList.add(new DataHolder(item, ItemType.ITEM));
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolderWithSetter holder, final int position) {
        final DataHolder dh = dataList.get(position);
        if (getItemViewType(position) == HEADER_TYPE) {
            holder.setExpandOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandOrCollapse(dataList.indexOf(dh));
                }
            });
        }
        holder.setItem(dh.getData());
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).getType() == ItemType.ITEM) {
            return ITEM_TYPE;
        }
        return HEADER_TYPE;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<Boolean> getOpenState() {
        return isOpen;
    }

    private int getHeaderCountUpToPosition(int position) {
        int counter = 0;
        for (int i = 0; i < position; i++) {
            counter += dataList.get(i).getType() == ItemType.HEADER ? 1 : 0;
        }
        return counter;
    }


    private void expandOrCollapse(int position) {
        int headerPosition = getHeaderCountUpToPosition(position);
        T headerGroup = fullData.get(headerPosition);
        int childLength = headerGroup.getChildren().size();
        if (!isOpen.get(headerPosition)) {
            for (int i = 0; i < childLength; i++) {
                dataList.add(position + i + 1, new DataHolder(headerGroup.getChildren()
                        .get(i), ItemType.ITEM));
            }
            notifyItemRangeInserted(position + 1, childLength);
        } else {
            for (int i = 0; i < childLength; i++) {
                dataList.remove(position + 1);
            }
            notifyItemRangeRemoved(position + 1, childLength);
        }
        isOpen.set(headerPosition, !isOpen.get(headerPosition));
    }

    protected enum ItemType {
        HEADER, ITEM;
    }

    public interface DataSet<K, L> {
        K getData();
        List<L> getChildren();
    }

    private class DataHolder<K> {
        private K data;
        private ItemType type;

        public DataHolder(K item, ItemType type) {
            this.data = item;
            this.type = type;
        }

        public K getData() {
            return data;
        }

        public ItemType getType() {
            return type;
        }
    }
}
