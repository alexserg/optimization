package ru.sberbank.optdemo4;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class LazySkipList<T> {
    static final int MAX_LEVEL = 10;

    final Node<T> head = new Node<T>(Integer.MIN_VALUE);
    final Node<T> tail = new Node<T>(Integer.MAX_VALUE);

    public LazySkipList() {
        for (int i = 0; i < head.next.length; i++) {
            head.next[i] = tail;
        }
    }

    private static final class Node<T> {
        static final int MAX_LEVEL = 10;
        final Lock lock = new ReentrantLock();
        final T item;
        final int key;
        final Node<T>[] next;
        volatile boolean marked = false;
        volatile boolean fullyLinked = false;
        private int topLevel;

        public Node(int key) { // sentinel node constructor
            this.item = null;
            this.key = key;
            next = new Node[MAX_LEVEL + 1];
            topLevel = MAX_LEVEL;
        }

        public Node(T x, int height) {
            item = x;
            key = x.hashCode();
            next = new Node[height + 1];
            topLevel = height;
        }

        public void lock() {
            lock.lock();
        }

        public void unlock() {
            lock.unlock();
        }
    }

    int find(T x, Node<T>[] preds, Node<T>[] succs) {
        int key = x.hashCode();
        int lFound = -1;
        Node<T> pred = head;
        for (int level = MAX_LEVEL; level >= 0; level--) {
            Node<T> curr = pred.next[level];
            while (key > curr.key) {
                pred = curr;
                curr = pred.next[level];
            }
            if (lFound == -1 && key == curr.key) {
                lFound = level;
            }
            preds[level] = pred;
            succs[level] = curr;
        }
        return lFound;
    }

    boolean contains(T x) {
        Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1];
        int lFound = find(x, preds, succs);
        return (lFound != -1
                && succs[lFound].fullyLinked
                && !succs[lFound].marked);
    }

    boolean add(T x) {
        int topLevel = randomLevel();
        Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1];
        while (true) {
            int lFound = find(x, preds, succs);
            if (lFound != -1) {
                Node<T> nodeFound = succs[lFound];
                if (!nodeFound.marked) {
                    while (!nodeFound.fullyLinked) {
                    }
                    return false;
                }
                continue;
            }
            int highestLocked = -1;
            try {
                Node<T> pred, succ;
                boolean valid = true;
                for (int level = 0; valid && (level <= topLevel); level++) {
                    pred = preds[level];
                    succ = succs[level];
                    pred.lock.lock();
                    highestLocked = level;
                    valid = !pred.marked && !succ.marked && pred.next[level] == succ;
                }
                if (!valid) continue;
                Node<T> newNode = new Node(x, topLevel);
                for (int level = 0; level <= topLevel; level++)
                    newNode.next[level] = succs[level];
                for (int level = 0; level <= topLevel; level++)
                    preds[level].next[level] = newNode;
                newNode.fullyLinked = true; // successful add linearization point return true;
            } finally {
                for (int level = 0; level <= highestLocked; level++)
                    preds[level].unlock();
            }
        }
    }

    private int randomLevel() {
        return ThreadLocalRandom.current().nextInt(MAX_LEVEL);
    }
}
