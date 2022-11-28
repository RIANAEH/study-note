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

## Map의 value를 이용해 정렬하기

현재 Map의 value 값으로 count를 가지고 있습니다. 따라서 저희는 count가 높은 것 순으로 key 즉 productId를 정렬해야합니다. 스트림의 `sorted()`를 이용할 수 있으며, `reverseOrder()`를 적용해 내림차순으로 정렬할 수 있습니다. 

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

## Map의 value와 key를 이용해 정렬하기

만약 '주문이 1번 2개, 2번 1개, 3번 2개, 4번 1개' 인 상황을 가정해봅시다. 이렇게 주문의 개수가 같은 상품이 있는 경우에는 개수가 같은 상품끼리는 어떻게 정렬을 할지 생각해봐야 합니다. 

1. ID가 작은 것부터 정렬한다. 
2. 더 최근에 주문된 상품을 먼저 정렬한다.  
...

여러가지 정렬 규칙이 있을 것입니다. 저는 이 중 가장 쉬운 `ID가 작은 것부터 정렬한다`를 선택했습니다.  

이를 구현하는 데에는 `thenComparing()`을 이용할 수 있습니다. 이때 `Entity`의 `Generic Type`을 명시해주어야함에 주의해야 합니다. 

```java
List<Long> productIdsByCount = orders.stream()
                .collect(groupingBy(order -> order.getProduct().getId(), counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Entry<Long, Long>::getValue, Comparator.reverseOrder())
                                .thenComparing(Entry::getKey))
                .map(Entry::getKey)
                .collect(Collectors.toUnmodifiableList());
```

<img width="476" alt="image" src="https://user-images.githubusercontent.com/45311765/204224663-a18a7436-3721-43b0-9fb9-341ddbdf4c97.png">
