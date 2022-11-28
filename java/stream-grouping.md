# Java 스트림으로 grouping, countring, sorting 뿌수기!

`productId`로 그루핑을 하는 경우 다음과 같이 `Map` 형태로 반환되며 `key`로는 `productId`를, `value`로는 `List<Order>`를 가진다. 

```java
Map<Long, List<Order>> ordersByProductId = orders.stream()
        .collect(groupingBy(order -> order.getProduct().getId()));
```

![image](https://user-images.githubusercontent.com/45311765/204207502-44dcadd9-06fe-44c4-8e63-c02da0c31cde.png)

이때 각 key 별로 value의 개수(count)를 가진 Map이 나오면 우리가 원하는 결과에 가까워질 것 같다. 여기에는 `Collectors.counting()`을 이용해 볼 수 있다. 

```java
Map<Long, Long> countByProductId = orders.stream()
        .collect(groupingBy(order -> order.getProduct().getId(), counting()));
```

![image](https://user-images.githubusercontent.com/45311765/204209172-d495412a-04f3-418f-88ed-42c47826039f.png)


```java
List<Long> productIdsByCount = orders.stream()
        .collect(groupingBy(order -> order.getProduct().getId(), counting()))
        .entrySet()
        .stream()
        .sorted(Collections.reverseOrder(Entry.comparingByValue()))
        .map(Entry::getKey)
        .collect(Collectors.toUnmodifiableList());
```

![image](https://user-images.githubusercontent.com/45311765/204211826-a6f0e593-8cad-4363-bbe6-55d86f5928e5.png)
