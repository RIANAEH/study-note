# Circuit Breaker

> [토스뱅크의 완전히 새로운 대출 시스템 by 토스뱅크 민재슬](https://www.youtube.com/watch?v=SLamxuykpnw) 영상을 보는데 `서킷`이라는 용어를 알고있다는 전제하에 말씀을 하셨다. 나는 모른다. 그래서 알아본다. 

## 서킷 브레이커가 필요한 상황은?

만약 우리 서버에서 다른 외부 서버의 API를 사용해야하는 로직이 있는 상황에서 외부 서버가 요청에 응답하지 못하는 상태라면 어떻게 해야 할까요? 우리 서버는 타임아웃이 발생할 때까지 응답을 기다리거나 계속해서 무의미한 요청을 보내며 자원을 낭비하게 될 수 있습니다. 이러한 장애는 의존성이 있는 서비스까지 연쇄적으로 전파될 수 있습니다. MSA(Microservice Architecure)에서는 내부 서버 사이의 요청 상황으로 생각해 볼 수도 있습니다. 이러한 장애가 계속 전파되어 한 원격 서버에서 발생한 장애가 모든 시스템에 큰 영향을 주게 될 수도 있습니다. 서킷 브레이크는 이러한 장애 규모를 최소화할 수 있게 도와줍니다. 

![image](https://user-images.githubusercontent.com/45311765/203503217-2d013a54-655a-4716-9e16-7c7e05b9339d.png)

## 그래서 서킷 브레이커가 뭔데?

위의 영상에서 언급한 `서킷`은 `서킷 브레이커`를 의미합니다. 서킷 브레이커란 클라이언트에서 어떤 원격 서버로 전송한 요청의 실패율(failure rate)이 특정 임계치(threshold)를 넘어서면, 이 서버에 문제가 있다고 판단하여 더 이상 무의미한 요청을 전송하지 않고 빠르게 에러를 발생(fail fast)시키는 방법 입니다. 이 방법으로 시스템 장애 규모를 최소화할 수 있습니다. 

![image](https://user-images.githubusercontent.com/45311765/203503349-50d0ff81-0a69-4e43-85f1-264e1391c879.png)

## 상태 머신(State Machine)으로 나타낸 서킷 브레이커의 상태

![image](https://user-images.githubusercontent.com/45311765/203504298-2b5fa1f4-6757-406c-ae87-dc2b373949e7.png)

점속 성공과 실패 이벤트가 발생할 때마다 내부 상태를 업데이트하여 자동으로 장애를 검출하고 복구 여부를 판단합니다. 

- `closed`: 초기 상태, 모든 접속이 평소와 같이 실행된다.
- `open`: 에러율이 임계치를 넘기면 open 상태가 된다. 모든 접속은 차단(fail fast)된다. 
- `half_open`: open 후 일정 시간이 지나면 half_open 상태가 된다. 접속을 시도하여 성공하면 closed, 실패하면 open으로 되돌아간다. 

## 참고 자료
- [Armeria의 서킷 브레이커 사용해 보기 by LINE 주승환](https://engineering.linecorp.com/ko/blog/try-armeria-circuit-breaker/)
- [분산 서비스 환경에 대한 Circuit Breaker 적용 by LINE Ono Yuichi](https://engineering.linecorp.com/ko/blog/circuit-breakers-for-distributed-services/)
- [CircuitBreaker by Martin Fowler](https://martinfowler.com/bliki/CircuitBreaker.html)
