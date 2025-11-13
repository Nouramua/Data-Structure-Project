package DSProject;

public class HeapPQ {
    private Product[] heap;
    private int size;
    private int capacity;

    public HeapPQ(int capacity) {
        this.capacity = capacity;
        this.heap = new Product[capacity];
        this.size = 0;
    }

    public void insert(Product product) {
        if (size < capacity) {
            heap[size] = product;
            heapifyUp(size);
            size++;
        } 
        else if (product.getAverageRating() > heap[0].getAverageRating()) {
            heap[0] = product;
            heapifyDown(0);
        }
    }

    public Product[] getAll() {
        return heap;
    }

    public int getSize() {
        return size;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].getAverageRating() >= heap[parent].getAverageRating()) break;
            swap(index, parent);
            index = parent;
        }
    }

    private void heapifyDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap[left].getAverageRating() < heap[smallest].getAverageRating())
                smallest = left;
            if (right < size && heap[right].getAverageRating() < heap[smallest].getAverageRating())
                smallest = right;

            if (smallest == index) break;
            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        Product temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
