package com.company;
import java.util.Map;
import java.util.TreeMap;

public class Broker {
    private final TreeMap<Integer, Integer> asks = new TreeMap<>();
    private final TreeMap<Integer, Integer> bids = new TreeMap<>();

    public void proceedOrder(String[] str) {
        if (str.length == 3) {
            String type = str[1];
            int size = Integer.parseInt(str[2]);

            if (type.equals("buy")) {
                while (size > 0) {
                    size -= asks.firstEntry().getValue();
                    if (size >= 0) {
                        asks.remove(asks.firstKey());
                    } else {
                        asks.replace(asks.firstKey(), size * -1);
                    }
                }
            } if (type.equals("sell")) {
                while (size > 0) {
                    size -= bids.lastEntry().getValue();
                    if (size >= 0) {
                        bids.remove(bids.lastKey());
                    } else {
                        bids.replace(bids.lastKey(), size * -1);
                    }
                }
            }
        }
    }

    public void proceedQuery(String[] str, StringBuilder strbuild) {
        if ("size".equals(str[1])&& str.length == 3) {
            int price = Integer.parseInt(str[2]);
            int size = asks.containsKey(price) ? asks.get(price) : bids.getOrDefault(price, 0);

            strbuild.append(size);
            strbuild.append(System.lineSeparator());

        } else if (str.length == 2) {
            String goal = str[1];
            int price = 0;
            int size = 0;

            if (goal.equals("best_ask")) {
                for (Map.Entry<Integer, Integer> entry : asks.entrySet()) {
                    if ((size = entry.getValue()) > 0) {
                        price = entry.getKey();
                        break;
                    }
                }
            } if (goal.equals("best_bid")) {
                if (!bids.isEmpty()) {
                    Integer key = bids.lastKey();
                    while (size == 0) {
                        size = bids.get(key);
                        price = size > 0 ? key : 0;
                        if ((key = bids.lowerKey(key)) == null) {
                            break;
                        }
                    }
                }
            }

            strbuild.append(price);
            strbuild.append(",");
            strbuild.append(size);
            strbuild.append(System.lineSeparator());
        }
    }

    public void proceedUpdate(String[] str) {
        if (str.length == 4) {
            int price = Integer.parseInt(str[1]);
            int size = Integer.parseInt(str[2]);
            String type = str[3];

            if (type.equals("ask")) {
                if (size > 0) {
                    asks.put(price, size);
                } else {
                    asks.remove(price);
                }
            } if (type.equals("bid")) {
                if (size > 0) {
                    bids.put(price, size);
                } else {
                    bids.remove(price);
                }
            }
        }

    }
}
