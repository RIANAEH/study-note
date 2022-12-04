# 동시성 제어하기

동시성을 제어할 수 있는 방법은 여러가지가 있으며, 이 방법들을 서로 조합해 사용할 수도 있습니다. 
따라서 **서비스의 인프라 구조와 비즈니스 로직에 맞는 방법**을 선택해야 합니다. 

> ✅ **동시성 제어 방법**  
> 1. synchronized
> 2. 격리 레벨 제어
> 3. 낙관적 락
> 4. 비관적 락
> 5. 분산락

## synchronized (자바 사용시!)

임계 영역에 접근하는 메서드가 여러 개인 경우, 또는 서버가 여러 대인 경우에는 synchronized 키워드로는 동시성을 제어할 수 없습니다. 

## 낙관적 락 (Optimistic Lock)

낙관적 락은 이름 그대로 트랜잭션 대부분은 충돌이 발생하지 않는다고 낙관적으로 가정하는 방법입니다. 
데이터베이스 락을 사용하지 않고 애플리케이션 락을 사용해 처리합니다. 
JPA가 제공하는 버전 관리 기능을 이용해 구현할 수 있습니다. 
낙관적 락은 트랜잭션을 커밋하기 전까지는 트랜잭션의 충돌을 알 수 없다는 특징을 가집니다. 

만약 한명의 사용자에 대한 3개의 결제 요청이 동시에 들어오면 낙관적 락을 사용하는 경우 2개의 결제 요청은 롤백이 됩니다. 
하지만 사용자 입장에서는 **순차적으로 3개의 요청이 모두 성공**하기를 바라고 있을 것입니다. 
낙관적 락을 이용하는 경우 롤백 말고 다른 엑션을 위해서는 충돌이 발생하는 상황에 대한 처리를 직접 해줘야하는데 생각보다 쉽지 않은 작업이라고 합니다. 

```
org.hibernate.StaleStateException: Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1; statement executed: update order_table set created_at=?, menu_id=?, menu_name=?, menu_price=?, status=?, user_id=?, version=? where id=? and version=?
```

## 비관적 락

비관적 락은 트랜잭션의 충돌이 발생한다고 가정하고 우선 락을 걸고 보는 방법입니다. 
데이터베이스 락을 사용합니다. (ex. SELECT FOR UPDATE)

```sql
select
    point0_.id as id1_4_,
    point0_.amount as amount2_4_,
    point0_.updated_at as updated_3_4_,
    point0_.user_id as user_id4_4_ 
from
    point point0_ 
where
    point0_.user_id=? for update
```

### 트랜잭션 문제

```
TransactionRequiredException: no transaction is in progress
```

- https://woonys.tistory.com/entry/Transactional%EC%9D%80-%EB%A7%8C%EB%8A%A5%EC%9D%B4-%EC%95%84%EB%8B%99%EB%8B%88%EB%8B%A4-2-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%EC%9D%98-%EA%B2%A9%EB%A6%AC%EC%84%B1%EA%B3%BC-lock
- https://junhyunny.github.io/spring-boot/jpa/junit/jpa-pessimitic-lock/

## 분산락

락을 위한 저장소를 따로 두고 특정 데이터에 대한 접근 전 락 저장소를 확인해 간접적으로 락을 구현하는 방식입니다. 분산 환경에서 락을 관리하는 공유 데이터베이스를 따로 두어 여러 서버 및 데이터베이스 사이의 동시성을 제어할 수 있습니다. 

- Redis, 
- MySQL 네임드 락: 문자열 이용, GET_LOCK, RELEASE_LOCK

락 데이터를 관리하는 데이터베이스에는 여러 서버에서 동시다발적으로 요청을 보내기 때문에 높은 처리량이 보장되어야 합니다. 이러한 면에서 Redis는 in-memory 기반 저장소기 때문에 RDBMS 보다 높은 처리량을 제공한다는 장점을 가집니다. 하지만 이미 RDBMS를 사용하고 있는 상황이라면 Redis를 위한 추가적인 인프라를 구축하고 관리해야한다는 단점을 가집니다. 

### 왜 많은 개발자들이 Redisson을 이용해 분산락을 구현할까요?

#### 레디스에서는 싱글 스레드를 이용합니다.

레디스는 싱글 스레드 기반으로 연산하기 때문에 락에 대한 여러 연산을 원자적이게 제공하기 수월합니다. 

#### lock에 타임아웃이 구현되어 있습니다. 

Redisson의 `tryLock(waitTime, leaseTime, unit)`에는 락 획득을 대기할 타임아웃인 `waitTime`과 락이 만료되는 시간인 `leaseTime`을 지정해야 합니다. 

`waitTime` 만큼의 시간이 지나면 `false`가 반환되며 락 획득에 실패했음을 알려줍니다. 그리고 `leaseTime` 만큼의 시간이 지나면 락이 만료되어 사라지기 때문에 어플리케이션에서 락을 해제해주지 않더라도(락 획득 후 해당 서버에 장애가 발생해 락을 해제하지 못하는 상황이 발생할 수 있음) 다른 스레드 혹은 어플리케이션에서 락을 획득할 수 있습니다. 

#### 스핀 락을 사용하지 않습니다.

Redisson은 스핀 락을 사용하지 않기 때문에 레디스에 부담을 주지 않습니다. 대신 pub/sub 방식을 사용합니다. 락이 해제될 때마다 subscribe 하는 클라이언트들에게 "락 획득을 시도해도 됩니다~"라는 메시지를 보냅니다. 따라서 각 클라이언트가 레디스에 락 획득 가능 여부 

### 분산락에는 갱신 유실 문제가 있어요.

분산락 구현 과정에서 `타임아웃`을 설정하게 되면 갱신 유실 문제가 발생할 수 있습니다. 분산락과 낙관적 락을 함께 이용해 이러한 문제를 해결할 수 있습니다.

> 이에 대한 자세한 설명은 [다음 영상](https://www.youtube.com/watch?v=UOWy6zdsD-c)에 나와있다. 

### 참고 자료

- https://way-be-developer.tistory.com/m/274
- https://techblog.woowahan.com/2631/
- https://hyperconnect.github.io/2019/11/15/redis-distributed-lock-1.html
- https://hyos-dev-log.tistory.com/34
