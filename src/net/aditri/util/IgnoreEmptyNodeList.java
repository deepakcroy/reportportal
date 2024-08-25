package net.aditri.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IgnoreEmptyNodeList implements NodeList, Iterable<Node> {

    private List<Node> nodes;

    public IgnoreEmptyNodeList(NodeList list) {
        nodes = new ArrayList<Node>();
        for (int i = 0; i < list.getLength(); i++) {
            if (!isWhitespaceNode(list.item(i))) {
                nodes.add(list.item(i));
            }
        }
    }

    @Override
    public Node item(int index) {
        return nodes.get(index);
    }

    @Override
    public int getLength() {
        return nodes.size();
    }

    private static boolean isWhitespaceNode(Node n) {
        if (n.getNodeType() == Node.TEXT_NODE) {
            String val = n.getNodeValue();
            return val.trim().length() == 0;
        } else {
            return false;
        }
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }
}