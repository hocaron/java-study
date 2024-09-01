package com.basestudy.map;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class MyHashMap<K extends Comparable<K>, V> {

    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() { return key; }
        public final V getValue() { return value; }
        public final int hashCode() { return Objects.hashCode(key) ^ Objects.hashCode(value); }
        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
    }

    // 기본 해시 테이블 크기와 트리화 임계값 설정
    private static final int INITIAL_CAPACITY = 16;
    private static final int TREEIFY_THRESHOLD = 8;
    private static final float LOAD_FACTOR = 0.75f;

    // 해시 테이블
    private Node<K, V>[] table;
    private int size;
    private int threshold;

    public MyHashMap() {
        table = (Node<K, V>[]) new Node[INITIAL_CAPACITY];
        threshold = (int) (INITIAL_CAPACITY * LOAD_FACTOR);
    }

    private int hash(K key) {
        return key == null ? 0 : (key.hashCode()) ^ (key.hashCode() >>> 16);
    }

    public V put(K key, V value) {
        return putVal(hash(key), key, value);
    }

    private V putVal(int hash, K key, V value) {
        Node<K, V>[] tab = table;
        int n = tab.length;
        int i = (n - 1) & hash;

        Node<K, V> p = tab[i];
        if (p == null) {
            tab[i] = newNode(hash, key, value, null);
        } else {
            Node<K, V> e;
            K k;
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;
            } else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) {
                            treeifyBin(tab, i);
                        }
                        break;
                    }
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                        break;
                    }
                    p = e;
                }
            }
            if (e != null) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        if (++size > threshold) {
            resize();
        }
        return null;
    }

    private Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
        return new Node<>(hash, key, value, next);
    }

    private void resize() {
        Node<K, V>[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity << 1;
        threshold = (int) (newCapacity * LOAD_FACTOR);
        table = (Node<K, V>[]) new Node[newCapacity];

        for (int j = 0; j < oldCapacity; ++j) {
            Node<K, V> e;
            if ((e = oldTable[j]) != null) {
                oldTable[j] = null;
                if (e.next == null) {
                    table[e.hash & (newCapacity - 1)] = e;
                } else {
                    Node<K, V> loHead = null, loTail = null;
                    Node<K, V> hiHead = null, hiTail = null;
                    Node<K, V> next;
                    do {
                        next = e.next;
                        if ((e.hash & oldCapacity) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        } else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        table[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        table[j + oldCapacity] = hiHead;
                    }
                }
            }
        }
    }

    private void treeifyBin(Node<K, V>[] tab, int index) {
        Node<K, V> e;
        if (tab.length < INITIAL_CAPACITY) {
            resize();
        } else if ((e = tab[index]) != null) {
            TreeNode<K, V> hd = null, tl = null;
            do {
                TreeNode<K, V> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;
                else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            tab[index] = hd;
            if (hd != null)
                hd.treeify(tab);
        }
    }

    private TreeNode<K, V> replacementTreeNode(Node<K, V> p, Node<K, V> next) {
        return new TreeNode<>(p.hash, p.key, p.value, next);
    }

    public V get(K key) {
        Node<K, V> e = getNode(hash(key), key);
        return e == null ? null : e.value;
    }

    private Node<K, V> getNode(int hash, K key) {
        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;
        if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                if (first instanceof TreeNode)
                    return ((TreeNode<K, V>) first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    public V remove(K key) {
        Node<K, V> e = removeNode(hash(key), key);
        return e == null ? null : e.value;
    }

    private Node<K, V> removeNode(int hash, K key) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, index;
        if ((tab = table) != null && (n = tab.length) > 0 && (p = tab[index = (n - 1) & hash]) != null) {
            Node<K, V> node = null, e;
            K k;
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
                node = p;
            else if ((e = p.next) != null) {
                if (p instanceof TreeNode)
                    node = ((TreeNode<K, V>) p).getTreeNode(hash, key);
                else {
                    do {
                        if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                            node = e;
                            break;
                        }
                        p = e;
                    } while ((e = e.next) != null);
                }
            }
            if (node != null) {
                if (node instanceof TreeNode)
                    ((TreeNode<K, V>) node).removeTreeNode(this, tab, false);
                else if (node == p)
                    tab[index] = node.next;
                else
                    p.next = node.next;
                --size;
                return node;
            }
        }
        return null;
    }

    static final class TreeNode<K extends Comparable<K>, V> extends Node<K, V> {
        TreeNode<K, V> parent;
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> prev;
        boolean red;

        TreeNode(int hash, K key, V val, Node<K, V> next) {
            super(hash, key, val, next);
        }

        TreeNode<K, V> getTreeNode(int h, Object k) {
            return ((parent != null) ? root() : this).findTreeNode(h, k, null);
        }

        TreeNode<K, V> findTreeNode(int h, Object k, Class<?> kc) {
            TreeNode<K, V> p = this;
            do {
                int ph, dir;
                K pk;
                TreeNode<K, V> pl = p.left, pr = p.right, q;
                if ((ph = p.hash) > h)
                    p = pl;
                else if (ph < h)
                    p = pr;
                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    return p;
                else if (pl == null)
                    p = pr;
                else if (pr == null)
                    p = pl;
                else if ((kc != null || (kc = comparableClassFor(k)) != null) &&
                        (dir = compareComparables(kc, k, pk)) != 0)
                    p = (dir < 0) ? pl : pr;
                else if ((q = pr.findTreeNode(h, k, kc)) != null)
                    return q;
                else
                    p = pl;
            } while (p != null);
            return null;
        }

        void removeTreeNode(MyHashMap<K, V> map, Node<K, V>[] tab, boolean movable) {
            // 삭제 연산을 수행하고 트리의 균형을 맞추는 역할을 합니다.
        }

        TreeNode<K, V> root() {
            for (TreeNode<K, V> r = this, p;;) {
                if ((p = r.parent) == null)
                    return r;
                r = p;
            }
        }

        void treeify(Node<K, V>[] tab) {
            TreeNode<K, V> root = null;
            for (TreeNode<K, V> x = this, next; x != null; x = next) {
                next = (TreeNode<K, V>) x.next;
                x.left = x.right = null;
                if (root == null) {
                    x.parent = null;
                    x.red = false;
                    root = x;
                } else {
                    K k = x.key;
                    int h = x.hash;
                    Class<?> kc = null;
                    for (TreeNode<K, V> p = root;;) {
                        int dir, ph;
                        K pk = p.key;
                        if ((ph = p.hash) > h)
                            dir = -1;
                        else if (ph < h)
                            dir = 1;
                        else if ((kc == null && (kc = comparableClassFor(k)) == null) ||
                                (dir = compareComparables(kc, k, pk)) == 0)
                            dir = -1;
                        else
                            dir = 1;

                        TreeNode<K, V> xp = p;
                        if ((p = (dir <= 0) ? p.left : p.right) == null) {
                            x.parent = xp;
                            if (dir <= 0)
                                xp.left = x;
                            else
                                xp.right = x;
                            root = balanceInsertion(root, x);
                            break;
                        }
                    }
                }
            }
        }

        // 레드-블랙 트리 삽입 후 균형을 맞추는 메서드
        private TreeNode<K, V> balanceInsertion(TreeNode<K, V> root, TreeNode<K, V> x) {
            x.red = true;
            for (TreeNode<K, V> xp, xpp;;) {
                if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                } else if (!xp.red || (xpp = xp.parent) == null)
                    return root;

                TreeNode<K, V> xppl = xpp.left;
                TreeNode<K, V> xppr = xpp.right;
                if (xp == xppl) {
                    if (xppr != null && xppr.red) {
                        xppr.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    } else {
                        if (x == xp.right) {
                            x = xp;
                            root = rotateLeft(root, x);
                        }
                        xp.red = false;
                        xpp.red = true;
                        root = rotateRight(root, xpp);
                    }
                } else {
                    if (xppl != null && xppl.red) {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    } else {
                        if (x == xp.left) {
                            x = xp;
                            root = rotateRight(root, x);
                        }
                        xp.red = false;
                        xpp.red = true;
                        root = rotateLeft(root, xpp);
                    }
                }
            }
        }

        private TreeNode<K, V> rotateLeft(TreeNode<K, V> root, TreeNode<K, V> p) {
            if (p != null) {
                TreeNode<K, V> r = p.right;
                TreeNode<K, V> rl;
                if ((rl = r.left) != null) {
                    r.left = rl;
                    rl.parent = r;
                }
                r.parent = p.parent;
                if (p.parent == null)
                    root = r;
                else if (p.parent.left == p)
                    p.parent.left = r;
                else
                    p.parent.right = r;
                r.left = p;
                p.parent = r;
            }
            return root;
        }

        private TreeNode<K, V> rotateRight(TreeNode<K, V> root, TreeNode<K, V> p) {
            if (p != null) {
                TreeNode<K, V> l = p.left;
                TreeNode<K, V> lr;
                if ((lr = l.right) != null) {
                    l.right = lr;
                    lr.parent = l;
                }
                l.parent = p.parent;
                if (p.parent == null)
                    root = l;
                else if (p.parent.right == p)
                    p.parent.right = l;
                else
                    p.parent.left = l;
                l.right = p;
                p.parent = l;
            }
            return root;
        }
    }

    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            Type[] ts, as;
            Type t;
            ParameterizedType p;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                            (p = (ParameterizedType) t).getRawType() ==
                                    Comparable.class &&
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 && as[0] == c)
                        return c;
                }
            }
        }
        return null;
    }

    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc) ? 0 :
                ((Comparable) k).compareTo(x);
    }

    public static void main(String[] args) {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");

        System.out.println("Get key 2: " + map.get(2)); // Output: Two
        System.out.println("Get key 3: " + map.get(3)); // Output: Two
        System.out.println("Remove key 3: " + map.remove(3)); // Output: Three
        System.out.println("Get key 3 after removal: " + map.get(3)); // Output: null
    }
}