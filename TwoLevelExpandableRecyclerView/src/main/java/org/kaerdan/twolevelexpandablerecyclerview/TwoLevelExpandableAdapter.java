package org.kaerdan.twolevelexpandablerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Extends this class and it's abstract methods to get and adapter for expandable recycler view.
 */
public abstract class TwoLevelExpandableAdapter<T extends TwoLevelExpandableAdapter.DataSet>
        extends RecyclerView.Adapter<ViewHolderWithSetter>  {

    public static final int HEADER_TYPE = 2;
    public static final int ITEM_TYPE = 1;

    private List<T> fullData;
    private List<DataHolder> dataList;
    private List<Boolean> isOpen;

    /**
     * Return adapter with collapsed content;
     * @param data List of items for Adapter to work with;
     */
    protected TwoLevelExpandableAdapter(List<T> data) {
        fullData = data;
        dataList = new ArrayList<>();
        isOpen = new ArrayList<>();
        for (T item: fullData) {
            dataList.add(new DataHolder(item.getData(), ItemType.HEADER));
            isOpen.add(false);
        }
    }

    /**
     * Return adapter whose items will be expanded according to state
     * @param data List of items for Adapter to work with;
     * @param state List of state of items, if true it will be expanded otherwise collapsed.
     *              MUST BE SAME LENGTH AS data!
     */
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
    public ViewHolderWithSetter onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE) {
            return getSecondLevelViewHolder(parent);
        }
        return getTopLevelViewHolder(parent);
    }

    /**
     * This method should return ViewHolder whose view will be on the bottom of two level hierarchy.
     * @param parent ViewGroup to inflate ViewHolder view with
     * @return Second Level ViewHolder
     */
    public abstract ViewHolderWithSetter getSecondLevelViewHolder(ViewGroup parent);

    /**
     * This method should return ViewHolder whose view will be on the top of two level hierarchy.
     * @param parent ViewGroup to inflate ViewHolder view with
     * @return Top Level ViewHolder
     */
    public abstract ViewHolderWithSetter getTopLevelViewHolder(ViewGroup parent);

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

    /**
     * Returns current state of adapter.
     * @return current state of adapter
     */
    public List<Boolean> getExpandState() {
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

    /**
     * Inteface of data passing to the adapter;
     * @param <K> type of data to be displayed in top level view
     * @param <L> type of date to be displayed in second level view
     */
    public interface DataSet<K, L> {
        /**
         * Returns data to be displayed in top level view
         * @return data to be displayed in top level view
         */
        K getData();

        /**
         * Returns data to be displayed in second level views
         * @return data to be displayed in second level views
         */
        List<L> getChildren();
    }

    protected enum ItemType {
        HEADER, ITEM;
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
