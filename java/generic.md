# Generic

제네릭은 타입 형 변환에서 발생할 수 있는 문제점을 컴파일 시점에 검사할 수 있게 하기 위해 만들어졌습니다. 제네릭은 클래스 작성 시 T와 같은 가상의 타입을 이용해 타입을 지정하지 않은 범용적인 클래스를 만들 수 있습니다. 하지만 **해당 클래스를 사용하는 시점에는 T를 String, StringBuffer와 같은 구체적인 타입으로 지정해 사용**하개 함으로써 형 변환을 불필요하게 만듭니다. 따라서 런타임 시 다른 타입으로 잘못 형 변환하여 예외가 발생하는 일이 없어집니다. 

- 클래스 작성 시점에는 T와 같은 가상의 타입을 이용한다. 
- 클래스 사용 시점에는 명시적으로 타입을 지정해야한다. 

## Wildcards

<> 안에 구체적인 타입이 아니라 '?'를 사용하면 어떤 타입이 제네릭 타입이 되더라도 상관 없어집니다. 이때 '?'를 wildcard라고 합니다. 

```java
public void getwildcard(WildcardGeneric<?> wg) {
    Object object = wg.getWildcard(); // 값을 가져올 수 있다. 
}

public void setWildcard() {
    WildcardGeneric<?> wg = new WildcardGeneric<String>();
    wg.setWildcard("A"); // 컴파일 에러 발생 -> ?에 특정 타입의 값을 지정할 수 없다. 
}
```

- 어떤 객체를 wildcard로 선언하고, 그 객체의 값은 가져올 수 있다. 
- 어떤 객체를 wlidcard로 선언하고, 그 객체의 값을 특정 타입으로 지정하는 것은 불가능하다.
    - **T 타입의 값을 지정해주는 것은 가능하다.**

### wildcard의 타입 범위를 지정할 수 있어요. 

`Bus extends Car`인 상황에서 `<? extends Car>`에는 제네릭 타입으로 Car를 상속받은 모든 클래스(Car, Bus)를 사용할 수 있습니다. 이를 Bounded Wildcards라고 부릅니다. 

## 공변과 불공변

## Type Erasure

## 참고 자료
- 자바의 신 2
- https://devlog-wjdrbs96.tistory.com/263
