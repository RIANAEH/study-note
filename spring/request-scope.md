# @RequestScope

```java
@Component
@Scope("requet")
```

빈을 request scope로 설정하는 경우 위와 같이 등록하면 스프링 실행 과정에서 `Scope 'request' is not active for the current thread.` 예외를 만날 수 있습니다. 스프링을 실행하고 빈을 등록하는 과정에서는 HTTP 요청이 없기 때문에 해당 빈이 생성되지 않기 때문이지요.  

```java
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
```

따라서 proxyMode 설정을 이용해 스프링 실행 시점에 빈을 등록할 때는 프록시가 등록되게 하고, 실제 HTTP 요청이 들어왔을 때는 프록시가 가리키는 실제 빈을 생성해 사용할 수 있게 해야합니다.
ObjectProvider를 이용하는 방법도 있습니다. ObjectProvider는 getObject() 메서드를 실행하는 시점으로 빈 등록 시점을 미뤄줍니다. 하지만 추가되는 코드가 있어서 저는 쓰고싶지 않습니다. 

## @RequestScope == @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)

<img width="660" alt="image" src="https://user-images.githubusercontent.com/45311765/203262678-8cd7cc72-136a-47b1-943a-cd96817a24bb.png">

- @RequestScope을 등록한 빈은 CGLIB Proxy로 생성된다. 

## 🤔 그렇다면 실제 빈은 언제 생성되는 걸까?

## ThreadLocal과는 뭐가 다른 걸까?

## 참고 자료
- https://chung-develop.tistory.com/64
